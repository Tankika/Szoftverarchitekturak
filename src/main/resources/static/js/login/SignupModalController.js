angular.module('ServiceFinder.Login')

	.controller('SignupModalController', ['UserService', '$location', '$route', '$scope', '$uibModal', function(UserService, $location, $route, $scope, $uibModal) {
		var self = this;
		
//		self.credentials = {};
//		self.user = {};
//		
//		self.openLoginModal = openLoginModal;
//		self.logout = logout;
//		
//		getUser();
//		
//		userChangedUnsubscriber = UserService.subscribe(userChangedListener);
//		$scope.$on('$destroy', onScopeDestroy);
//		
//		function openLoginModal() {
//			$uibModal.open({
//				templateUrl: 'js/login/login.html',
//				controller: 'LoginModalController',
//				controllerAs: 'loginModalCtrl',
//				size: 'sm'
//			});
//		}
//		
//		function logout() {
//			UserService.logout().then(logoutHandler, logoutHandler);
//			
//			function logoutHandler(result) {
//				if($location.path() === "/") {
//					$route.reload();
//				} else {
//					$location.path("/");
//				}
//			}
//		}
//		
//		function getUser() {
//			UserService.getUser().then(function(data) {
//				self.user = data;
//			});
//		}
//		
//		function userChangedListener(newUser) {
//			self.user = newUser;
//		}
//		
//		function onScopeDestroy() {
//			userChangedUnsubscriber();
//		}
		
	}]);