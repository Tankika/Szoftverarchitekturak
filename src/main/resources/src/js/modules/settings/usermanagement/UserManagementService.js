angular.module('BugTracker.Settings')
	.service('UserManagementService', ['$http', function($http) {
		'use strict';
		
		this.signup = signup;
		this.getUserManagementPreload = getUserManagementPreload;
		
		function signup(credentials) {
			var request = {
				email: credentials.email,
				password: credentials.password,
				roleId: credentials.selectedRole
			};
			
			return $http.post("/user/signup", request).then(function(response) {
				return response.data;
			}, function(error) {
				return error;
			});
		}
		
		function getUserManagementPreload() {
			return $http.get('/user/usermanagementpreload').then(function(response) {
				return response.data;
			});
		}
		
	}]);