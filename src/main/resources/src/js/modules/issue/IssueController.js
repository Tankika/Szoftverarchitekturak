angular.module('BugTracker.Issue')
	.controller('IssueController', ['preloadedIssue', 'preloadedChoices', 'isEdit', 'IssueService', '$stateParams', function(preloadedIssue, preloadedChoices, isEdit, IssueService, $stateParams) {
		'use strict';
		
		var vm = this;
		
		vm.issue = angular.isObject(preloadedIssue) ? preloadedIssue : {};
		vm.projectId = $stateParams.projectId;
		vm.constants = preloadedChoices;
		
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onCreateButtonClick = onCreateButtonClick;
		
		function onModifyButtonClick() {
			//IssueService.modifyIssue(vm.issue).then(navigateToIssueList);
		}
		
		function onCreateButtonClick() {
			//IssueService.createIssue(vm.issue).then(navigateToIssueList);
		}
		
		function navigateToIssueList() {
			return $state.go('main.project', {
				id: vm.projectId
			});
		}
	}]);