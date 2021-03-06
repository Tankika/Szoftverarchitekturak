angular.module('BugTracker.Issue', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider
	.state('main.createissue', {
		url: 'project/{projectId}/createissue',
		templateUrl: 'js/modules/issue/CreateIssue.html',
		controller: 'IssueController as vm',
		resolve: {
			preloadedIssue: [function() {
				return null;
			}],
			preloadedChoices: ['IssueService', function(IssueService) {
				return IssueService.getConstants();
 			}],
			assignableUsers: [function() {
				return null;
			}],
		}
	})
	.state('main.modifyissue', {
		url: 'project/{projectId}/modifyissue/{issueId}',
		templateUrl: 'js/modules/issue/ModifyIssue.html',
		controller: 'IssueController as vm',
		resolve: {
			preloadedIssue: ['IssueService', '$stateParams', function(IssueService, $stateParams) {
				return IssueService.getIssueById($stateParams.issueId);
			}],
			preloadedChoices: ['IssueService', function(IssueService) {
				return IssueService.getConstants();
 			}],
			assignableUsers: [function() {
				return null;
			}],
		}
	})
	.state('main.displayissue', {
		url: 'project/{projectId}/issue/{issueId}',
		templateUrl: 'js/modules/issue/DisplayIssue.html',
		controller: 'IssueController as vm',
		resolve: {
			preloadedIssue: ['IssueService', '$stateParams', function(IssueService, $stateParams) {
				return IssueService.getIssueById($stateParams.issueId);
			}],
			preloadedChoices: [function() {
				return null;
			}],
			assignableUsers: ['IssueService', '$stateParams', function(IssueService, $stateParams) {
				return IssueService.listAssignableUsers($stateParams.projectId);
			}]
		}
	});
}]);