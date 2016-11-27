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
		
		function createIssue(projectId, issue) {
			return $http.post('/issue/saveIssue/' + projectId, createSaveIssueRequest(issue))
			.then(function(response) {
				return response.data;
			});
		}
		
		function modifyIssue(projectId, issue) {
			return $http.put('/issue/saveIssue/' + projectId + '/' + issue.id, createSaveIssueRequest(issue))
			.then(function(response) {
				return response.data;
			});
		}
		
		function sendComment(issueId, message) {
			return $http.post('/issue/sendComment', {
				issueId: issueId,
				message: message
			}).then(function(response) {
				return response.data;
			});
		}
		
		function createSaveIssueRequest(issue) {
			return {
			    name : issue.name,
			    description : issue.description,
			    reproductionSteps : issue.reproductionSteps,
			    version : issue.version,
			    type : issue.type,
			    status : issue.status,
			    priority : issue.priority,
			    severity : issue.severity,
			};
		}
		
	}]);