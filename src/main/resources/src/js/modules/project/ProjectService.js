angular.module('BugTracker.Project')
	.service('ProjectService', ['$http', function($http) {
		'use strict';
		
		this.getIssues = getIssues;
		
		function getIssues(projectId) {
			return $http.get('/issue/listIssues/' + projectId).then(function(response) {
				return response.data;
			});
		}
	}]);