angular.module('BugTracker.Settings')
	.service('UserManagementService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.signup = signup;
		this.getUserManagementPreload = getUserManagementPreload;
		
		function signup(credentials) {
			var deferred = $q.deferred(),
				request = {
					email: credentials.email,
					password: credentials.password,
					roleId: credentials.selectedRole.id
				};
			
			$http.post("/user/signup", request).then(function(response) {
				if (response.status === 200) {
					deferred.resolve(response.data);
				} else {
					deferred.reject();
				}
				
			}, function(error) {
				deferred.reject();
			});
			
			return deferred.promise;
		}
		
		function getUserManagementPreload() {
			return $http.get('/user/usermanagementpreload').then(function(response) {
				return response.data;
			});
		}
		
	}]);