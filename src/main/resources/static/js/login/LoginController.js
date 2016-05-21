angular.module('ServiceFinder.Login')

	.controller('LoginController', ['UserService', '$location', '$route', '$scope', '$uibModal', function(UserService, $location, $route, $scope, $uibModal) {
		var self = this,
			userChangedUnsubscriber;
		
		self.credentials = {};
		self.user = {};
		
		self.openLoginModal = openLoginModal;
		self.logout = logout;
		
		getUser();
		
		userChangedUnsubscriber = UserService.subscribe(userChangedListener);
		$scope.$on('$destroy', onScopeDestroy);
		
		function openLoginModal() {
			$uibModal.open({
				templateUrl: 'js/login/login.html',
				controller: 'LoginModalController',
				controllerAs: 'loginModalCtrl',
				size: 'sm'
			});
		}
		
		function openSignupModal() {
			$uibModal.open({
				templateUrl: 'js/login/signup.html',
				controller: 'SignupModalController',
				controllerAs: 'signupModalCtrl',
				size: 'sm'
			});
		}
		
		function logout() {
			UserService.logout().then(logoutHandler, logoutHandler);
			
			function logoutHandler(result) {
				if($location.path() === "/") {
					$route.reload();
				} else {
					$location.path("/");
				}
			}
		}
		
		function getUser() {
			UserService.getUser().then(function(data) {
				self.user = data;
			});
		}
		
		function userChangedListener(newUser) {
			self.user = newUser;
		}
		
		function onScopeDestroy() {
			userChangedUnsubscriber();
		}
		
	}]);