angular.module('BugTracker.Project')
	.controller('ProjectController', ['issueList', '$state', '$stateParams', 'uiGridConstants', 'UserHandlerService', function(issueList, $state, $stateParams, uiGridConstants, UserHandlerService) {
		'use strict';
		
		var vm = this;
		vm.onCreateNewIssueButtonClick = onCreateNewIssueButtonClick;
		vm.projectId = $stateParams.id;
		vm.onOpenButtonClick = onOpenButtonClick;
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onRemoveButtonClick = onRemoveButtonClick;

		vm.projectName = issueList.projectName;
		vm.issueList =  issueList.issues;
		vm.getTableHeight = getTableHeight;
		vm.isAuthorised = UserHandlerService.isAuthorised;
		
		vm.gridOptions = {
			enablePagination: true,
			enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER,
	        enableVerticalScrollbar : uiGridConstants.scrollbars.NEVER,
			data: vm.issueList,
			appScopeProvider: vm,
			enablePaginationControls: false,
			onRegisterApi: function(gridApi) {vm.gridApi = gridApi},
		    paginationPageSize: 25,
			columnDefs: [
				{ name: 'name', fieldName: 'name' },
				{ name: 'type', fieldName: 'type' },
				{ name: 'priority', fieldName: 'priority' },
				{ name: 'severity', fieldName: 'severity' },
				{ name: 'status', fieldName: 'status'},
				{
					name: 'Action',
					cellTemplate: '<span class=\"glyphicon glyphicon-eye-open\" ng-click=\"grid.appScope.onOpenButtonClick(row.entity)\"></span>' +
								  '<span class=\"glyphicon glyphicon-pencil\" ng-if=\"grid.appScope.isAuthorised(\'MODIFY_ISSUE\')\" ng-click=\"grid.appScope.onModifyButtonClick(row.entity)"\></span>' +
								  '<span class=\"glyphicon glyphicon-remove\" ng-if=\"grid.appScope.isAuthorised(\'DELETE_ISSUE\')\" ng-click=\"grid.appScope.onRemoveButtonClick(row.entity)"\></span>'
				}
			]
		};
		
		function onOpenButtonClick(rowEntity) {
			$state.go('main.displayissue', {
				projectId: vm.projectId,
				issueId: rowEntity.id
			});
		}
		
		function onModifyButtonClick(rowEntity) {
			$state.go('main.modifyissue', {
				projectId: vm.projectId,
				issueId: rowEntity.id
			});
		}
		
		function onRemoveButtonClick(rowEntity) {
			// TODO: show modal confirmation screen
		}
		
		function onCreateNewIssueButtonClick() {
			$state.go('main.createissue', {
				projectId: vm.projectId
			});
		}
		
	    function getTableHeight() {
	        var rowHeight = 30; // your row height
	        var headerHeight = 30; // your header height
	        var isLastPageNotComplete = (vm.gridApi.pagination.getPage() === vm.gridApi.pagination.getTotalPages()) && (vm.issueList.length % 25 !== 0);
	        var tableHeight = (isLastPageNotComplete ? vm.issueList.length % 25 : 25) * rowHeight + headerHeight; 
	        return tableHeight;
	     };
		
	}]);