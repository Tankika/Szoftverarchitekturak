angular.module('BugTracker.Settings')
	.service('ProjectManagementService', ['$http', function($http) {
		'use strict';
		
		this.createNewProject = createNewProject;
		
		function createNewProject(projectName) {
			return $http.post('/issue/createNewProject', {
				name: projectName
			});
		}
	}]);