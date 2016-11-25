angular.module('BugTracker.Settings')
	.service('UserManagementService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.createUser = createUser;
		this.modifyUser = modifyUser;
		this.getUserManagementPreload = getUserManagementPreload;
		this.getProjectList = getProjectList;
		this.getUserData = getUserData;
		
		function getUserData(email) {
			return $http.post("/user/getUserDataByEmail", {
				email: email
			}).then(function(response) {
				return response.data;
			});
		}
		
		function modifyUser(email, role, userProjectList) {
			var request = {
				email: email,
				roleId: role.id,
				projectIds: mapProjectIds(userProjectList)
			};
			
			return $http.post('/user/modifyUser', request);
		}
		
		function createUser(credentials, role, userProjectList) {
			var deferred = $q.defer();
			var request = {
				email: credentials.email,
				password: credentials.password,
				roleId: role.id,
				projectIds: mapProjectIds(userProjectList)
			};
			
			$http.post("/user/createUser", request).then(function(response) {
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