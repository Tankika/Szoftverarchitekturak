angular.module('BugTracker.Settings')
	.service('UserManagementService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.signup = signup;
		this.getUserManagementPreload = getUserManagementPreload;
		this.getProjectList = getProjectList;
		
		function signup(credentials, userProjectList) {
			var deferred = $q.defer();
			var request = {
				email: credentials.email,
				password: credentials.password,
				roleId: credentials.selectedRole.id,
				projectIds: mapProjectIds(userProjectList)
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
		
		function mapProjectIds(userProjectList) {
			var userProjectIds = [];
			for(var i = 0; i < userProjectList.length; i++) {
				userProjectIds.push(userProjectList[i].id);
			}
			return userProjectIds;
		}
		
		function getUserManagementPreload() {
			return $http.get('/user/usermanagementpreload').then(function(response) {
				return response.data;
			});
		}
		
		function getProjectList() {
			return $http.get('/issue/listProjects').then(function(response) {
				return response.data.projects;
			});
		}
		
	}]);