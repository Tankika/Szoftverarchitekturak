angular.module('BugTracker.Issue')
	.service('IssueService', ['$http', function($http) {
		'use strict';
		
		this.getIssueById = getIssueById;
		this.getConstants = getConstants;
		
		this.createIssue = createIssue;
		this.modifyIssue = modifyIssue;
		
		function getIssueById(issueId) {
			return $http.get('/issue/' + issueId).then(function(response) {
				return response.data;
			});
		}
		
		function getConstants() {
			return $http.get('/issue/getConstants').then(function(response) {
				return response.data;
			});
		}
		
		function createIssue(issue) {
			
		}
		
		function modifyIssue(issue) {
			
		}
		
	}]);