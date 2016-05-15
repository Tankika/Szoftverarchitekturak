angular.module('ServiceFinder.Login')

	.controller('LoginController', ['UserService', '$location', '$route', '$scope', function(UserService, $location, $route, $scope) {
		var self = this,
			userChangedUnsubscriber;
		
		self.credentials = {};
		self.user = {};
		
		self.login = login;
		self.logout = logout;
		
		getUser();
		
		userChangedUnsubscriber = UserService.subscribe(userChangedListener);
		$scope.$on('$destroy', onScopeDestroy);
		
		function login() {
			UserService.login(self.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					self.user = data;
					self.error = false;
					
					$location.path("/");
				} else {
					self.error = true;
				}
			}
			
			function handleError(error) {
				self.error = true;
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