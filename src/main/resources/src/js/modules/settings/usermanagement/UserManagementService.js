angular.module('BugTracker.Settings')
	.service('UserManagementService', ['$http', function($http) {
		'use strict';
		
		this.signup = signup;
		
		function signup(credentials) {
			var request = {
				email: credentials.email,
				password: credentials.password
			};
			
			return $http.post("/user/signup", request).then(function(response) {
				return response.data;
			}, function(error) {
				return error;
			});
		}
	}]);