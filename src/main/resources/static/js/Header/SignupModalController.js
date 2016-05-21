angular.module('ServiceFinder.Header')

	.controller('SignupModalController', ['UserService', '$scope', '$q', function(UserService, $scope, $q) {
		var self = this;

		self.error = false;
		self.credentials = {};
		
		self.signup = signup;
		self.isEmailFree = isEmailFree;
		
		function signup() {
			UserService.signup(self.credentials).then(function(data) {
				$scope.$close(data);
			}, function(error) {
				self.error = true;
			});
		}
		
		function isEmailFree(email) {
			var deferred = $q.defer();
			
			UserService.isEmailFree(email)
			.then(function(data) {
				if(data.emailFree) {
					deferred.resolve();
				} else {
					deferred.reject();
				}
			}, function(error) {
				deferred.reject();
			});
			
			return deferred.promise;
		}
		
	}]);