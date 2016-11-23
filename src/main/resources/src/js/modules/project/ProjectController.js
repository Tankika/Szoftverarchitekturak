angular.module('BugTracker.Project')
	.controller('ProjectController', ['issueList', '$state', '$stateParams', 'uiGridConstants', function(issueList, $state, $stateParams, uiGridConstants) {
		'use strict';
		
		var vm = this;
		vm.onCreateNewIssueButtonClick = onCreateNewIssueButtonClick;
		vm.projectId = $stateParams.projectId;
		vm.onOpenButtonClick = onOpenButtonClick;
		vm.onModifyButtonClick = onModifyButtonClick;
		vm.onRemoveButtonClick = onRemoveButtonClick;
		var demoIssueList = [];
		for(var i = 0; i < 86; i++) {
			demoIssueList.push({name:'A' + i, type:'bug', priority:'low', severity:'low', status:'OPEN'});
		}
		
		vm.issueList =  issueList.issues.length > 0 ? issueList.issues : demoIssueList;
		vm.getTableHeight = getTableHeight;
		
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
					cellTemplate: '<span class=\"glyphicon glyphicon-eye-open\" ng-click=\"grid.appScope.onOpenButtonClick(row)\"></span>' +
								  '<span class=\"glyphicon glyphicon-pencil\" ng-click=\"grid.appScope.onModifyButtonClick(row)"\></span>' +
								  '<span class=\"glyphicon glyphicon-remove\" ng-click=\"grid.appScope.onRemoveButtonClick(row)"\></span>'
				}
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
		
	    function getTableHeight() {
	        var rowHeight = 30; // your row height
	        var headerHeight = 30; // your header height
	        var isLastPageNotComplete = (vm.gridApi.pagination.getPage() === vm.gridApi.pagination.getTotalPages()) && (vm.issueList.length % 25 !== 0);
	        var tableHeight = (isLastPageNotComplete ? vm.issueList.length % 25 : 25) * rowHeight + headerHeight; 
	        return tableHeight;
	     };
		
	}]);