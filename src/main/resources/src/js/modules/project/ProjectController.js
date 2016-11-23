angular.module('BugTracker.Project')
	.controller('ProjectController', ['issueList', '$state', '$stateParams', function(issueList, $state, $stateParams) {
		'use strict';
		
		var vm = this;
		vm.onCreateNewIssueButtonClick = onCreateNewIssueButtonClick;
		vm.projectId = $stateParams.projectId;
		vm.onOpenButtonClick = onOpenButtonClick;
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onRemoveButtonClick = onRemoveButtonClick;
		
		vm.gridOptions = {
			data: issueList.issues,
			columnDefs: [
				{ name: 'name', fieldName: 'name' },
				{ name: 'type', fieldName: 'type' },
				{ name: 'priority', fieldName: 'priority' },
				{ name: 'severity', fieldName: 'severity' },
				{ name: 'status', fieldName: 'status'}
				/*,
				{
					name: 'Action',
					cellTemplate: '<span class="glyphicon glyphicon-eye-open" ng-click="vm.onOpenButtonClick($row)"></span>' +
								  '<span class="glyphicon glyphicon-modify" ng-click="vm.onModifyButtonClick($row)"></span>' +
								  '<span class="glyphicon glyphicon-remove" ng-click="vm.onRemoveButtonClick($row)"></span>'
				}*/
			]
		};
		
		function onOpenButtonClick(row) {
			console.log(row);
		}
		
		function onModifyButtonClick(row) {
			console.log(row);
		}
		
		function onRemoveButtonClick(row) {
			console.log(row);
		}
		
		function onCreateNewIssueButtonClick() {
			//$state.go('main.issedet)
		}
		
	}]);