angular.module('ServiceFinder.Header')

	.controller('LoginModalController', ['UserService', '$location', '$scope', function(UserService, $location, $scope) {
		var self = this;
		
		self.error = false;
		self.loginFailed = false;
		self.credentials = {};
		
		self.login = login;
		self.onSignupClick = onSignupClick;
		
		function login() {
			UserService.login(self.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					self.error = false;
					self.loginFailed = false;
					
					$scope.$close("login");
				} else {
					self.error = true;
				}
			}
			
			function handleError(error) {
				if(error.status === 401) {
					self.loginFailed = true;			
				} else {
					self.error = true;			
				}
			}
		}
		
		function onSignupClick() {
			$scope.$close("signup");
		}
		
	}]);