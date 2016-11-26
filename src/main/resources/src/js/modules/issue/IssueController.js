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
		
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onCreateButtonClick = onCreateButtonClick;
		vm.sendComment = sendComment;
		
		function onModifyButtonClick() {
			IssueService.modifyIssue(vm.issue).then(navigateToIssueList);
		}
		
		function onCreateButtonClick() {
			IssueService.createIssue(vm.issue).then(navigateToIssueList);
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