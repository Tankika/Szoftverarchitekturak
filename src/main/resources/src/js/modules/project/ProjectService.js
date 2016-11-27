angular.module('BugTracker.Project')
	.service('ProjectService', ['$http', function($http) {
		'use strict';
		
		this.getIssues = getIssues;
		this.deleteIssue = deleteIssue;
		
		function getIssues(projectId) {
			return $http.get('/issue/listIssues/' + projectId).then(function(response) {
				return response.data;
			});
		}
		
		function deleteIssue(issueId) {
			return $http.delete('/issue/deleteIssue/' + issueId).then(function(response) {
				return response.data;
			});
		}
		
	}]);