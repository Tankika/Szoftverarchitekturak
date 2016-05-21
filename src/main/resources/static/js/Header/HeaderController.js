angular.module('ServiceFinder.Header')

	.controller('HeaderController', ['UserService', '$location', '$route', '$scope', '$uibModal', function(UserService, $location, $route, $scope, $uibModal) {
		var self = this,
			userChangedUnsubscriber;
		
		self.credentials = {};
		self.user = {};
		
		self.openLoginModal = openLoginModal;
		self.openSignupModal = openSignupModal;
		self.onNewPostClick = onNewPostClick;
		self.logout = logout;
		
		getUser();
		
		userChangedUnsubscriber = UserService.subscribe(userChangedListener);
		$scope.$on('$destroy', onScopeDestroy);
		
		function openLoginModal() {
			var modal = $uibModal.open({
				templateUrl: 'js/Header/login.html',
				controller: 'LoginModalController',
				controllerAs: 'loginModalCtrl',
				size: 'sm'
			});
			
			// In case the modal was closed, register button was pressed, so we open the register panel.
			modal.closed.then(openSignupModal);
		}
		
		function openSignupModal() {
			$uibModal.open({
				templateUrl: 'js/Header/signup.html',
				controller: 'SignupModalController',
				controllerAs: 'signupModalCtrl',
				size: 'sm'
			});
		}
		
		function onNewPostClick() {
			if(self.user.authenticated === true) {
				$location.path("/newPost");
			} else {
				openLoginModal();
			}
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