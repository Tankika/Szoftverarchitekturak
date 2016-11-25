angular.module('BugTracker.Settings')
	.controller('ProjectManagementController', ['ProjectManagementService', function(ProjectManagementService) {
		'use strict';
		
		var vm = this;
		
		vm.onCreateNewProjectButtonClick = onCreateNewProjectButtonClick;
		vm.project = {};
		
		function onCreateNewProjectButtonClick() {
			ProjectManagementService.createNewProject(vm.project).then(function() {
				vm.success = true;
				vm.error = false;
			}, function(error) {
				vm.error = true;
				vm.success = false;
			});
		}

	}]);