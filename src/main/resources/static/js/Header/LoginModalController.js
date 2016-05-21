angular.module('ServiceFinder.Header')

	.controller('LoginModalController', ['UserService', '$location', '$scope', function(UserService, $location, $scope) {
		var self = this;
		
		self.credentials = {};
		
		self.login = login;
		self.onSignupClick = onSignupClick;
		
		function login() {
			UserService.login(self.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					self.error = false;
					
					$location.path("/");
					$scope.$dismiss();
				} else {
					self.error = true;
				}
			}
			
			function handleError(error) {
				self.error = true;
			}
		}
		
		function onSignupClick() {
			$scope.$close();
		}
		
	}]);