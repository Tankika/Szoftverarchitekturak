angular.module('BugTracker.Issue')
	.service('IssueService', ['$http', function($http) {
		'use strict';
		
		this.getIssueById = getIssueById;
		this.getConstants = getConstants;
		this.listAssignableUsers = listAssignableUsers;
		this.assignUserToIssue = assignUserToIssue;
		
		this.createIssue = createIssue;
		this.modifyIssue = modifyIssue;
		
		this.sendComment = sendComment;
		
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
		
		function listAssignableUsers(projectId) {
			return $http.get('/issue/listAssignableUsers/' + projectId).then(function(response) {
				return response.data;
			});
		}
		
		function assignUserToIssue(issueId, userId) {
			return $http.post('/issue/assignUserToIssue', {
				issueId: issueId,
				userId: userId
			}).then(function(response) {
				return response.data;
			});
		}
		
		function createIssue(issue) {
			
		}
		
		function modifyIssue(issue) {
			
		}
		
		function sendComment(issueId, message) {
			return $http.post('/issue/sendComment', {
				issueId: issueId,
				message: message
			}).then(function(response) {
				return response.data;
			});
		}
		
	}]);