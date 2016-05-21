angular.module('ServiceFinder.Login')

	.controller('LoginModalController', ['UserService', '$location', '$scope', function(UserService, $location, $scope) {
		var self = this;
		
		self.credentials = {};
		
		self.login = login;
		
		function login() {
			UserService.login(self.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					self.error = false;
					
					$location.path("/");
					$scope.$close();
				} else {
					self.error = true;
				}
			}
			
			function handleError(error) {
				self.error = true;
			}
		}
		
	}]);