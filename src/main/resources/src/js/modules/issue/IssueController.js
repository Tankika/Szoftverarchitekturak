angular.module('BugTracker.Issue')
	.controller('IssueController', ['preloadedIssue', 'preloadedChoices', 'isEdit', 'IssueService', '$stateParams', 'UserHandlerService', function(preloadedIssue, preloadedChoices, isEdit, IssueService, $stateParams, UserHandlerService) {
		'use strict';
		
		var vm = this;
		
		vm.issue = angular.isObject(preloadedIssue) ? preloadedIssue : {};
		vm.projectId = $stateParams.projectId;
		vm.constants = preloadedChoices;
		vm.isAuthorised = UserHandlerService.isAuthorised;
		
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onCreateButtonClick = onCreateButtonClick;
		vm.sendComment = sendComment;
		
		function onModifyButtonClick() {
			//IssueService.modifyIssue(vm.issue).then(navigateToIssueList);
		}
		
		function onCreateButtonClick() {
			//IssueService.createIssue(vm.issue).then(navigateToIssueList);
		}
		
		function sendComment() {
			IssueService.sendComment(vm.issue.id, vm.newComment).then(function(result) {
				vm.issue.comment = result;
			});
			vm.newComment = undefined;
		}
		
		function navigateToIssueList() {
			return $state.go('main.project', {
				id: vm.projectId
			});
		}
	}]);