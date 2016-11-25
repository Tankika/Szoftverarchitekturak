angular.module('BugTracker.Settings')
	.service('ProjectManagementService', ['$http', function($http) {
		'use strict';
		
		this.createNewProject = createNewProject;
		
		function createNewProject(project) {
			return $http.post('/issue/createNewProject', {
				name: project.projectName,
				description: project.description
			});
		}
	}]);