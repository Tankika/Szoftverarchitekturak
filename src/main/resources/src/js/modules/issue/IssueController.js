angular.module('BugTracker.Issue')
	.controller('IssueController', ['preloadedIssue', 'preloadedChoices', 'IssueService', '$stateParams', 'UserHandlerService', 'assignableUsers', function(preloadedIssue, preloadedChoices, IssueService, $stateParams, UserHandlerService, assignableUsers) {
		'use strict';
		
		var vm = this;
		
		vm.issue = angular.isObject(preloadedIssue) ? preloadedIssue : {};
		vm.projectId = $stateParams.projectId;
		vm.constants = preloadedChoices;
		vm.assignableUsers = assignableUsers;
		vm.assignUserToIssue = assignUserToIssue;
		vm.isAuthorised = UserHandlerService.isAuthorised;
		
		vm.onCreateButtonClick = onCreateButtonClick;
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.sendComment = sendComment;
		
		function onCreateButtonClick() {
			IssueService.createIssue($stateParams.projectId, vm.issue).then(navigateToIssueList);
		}
		
		function onModifyButtonClick() {
			IssueService.modifyIssue($stateParams.projectId, vm.issue).then(navigateToIssueList);
		}
		
		function sendComment() {
			IssueService.sendComment(vm.issue.id, vm.newComment).then(function(result) {
				vm.issue.comment = result;
			});
			vm.newComment = undefined;
		}
		
		function assignUserToIssue(assignee) {
			IssueService.assignUserToIssue($stateParams.issueId, assignee.id);
		}
		
		function navigateToIssueList() {
			return $state.go('main.project', {
				id: vm.projectId
			});
		}
	}]);