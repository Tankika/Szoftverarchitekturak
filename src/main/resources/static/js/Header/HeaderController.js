angular.module('ServiceFinder.Header')

	.controller('HeaderController', ['UserService', '$location', '$route', '$scope', '$uibModal', function(UserService, $location, $route, $scope, $uibModal) {
		'use strict';

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
		
		function openLoginModal(targetPath) {
			var modal = $uibModal.open({
				templateUrl: 'js/Header/login.html',
				controller: 'LoginModalController',
				controllerAs: 'loginModalCtrl',
				size: 'sm'
			});
			
			// Handle the close event of the modal.
			modal.result.then(function(result) {
				if(result === "login") {
					// User successfully logged in on modal.
					if(angular.isString(targetPath)) {
						$location.path(targetPath);
					} else {
						$route.reload();
					}
				} else if(result === "signup") {
					// User clicked the signup link on modal.
					modal.closed.then(openSignupModal);	
				}
			});
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
			var newPostPath = "/newPost";
			
			if(self.user.authenticated === true) {
				$location.path(newPostPath);
			} else {
				openLoginModal(newPostPath);
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