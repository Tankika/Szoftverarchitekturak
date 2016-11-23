angular.module('BugTracker.Settings')
	.controller('ProjectManagementController', ['ProjectManagementService', function(ProjectManagementService) {
		'use strict';
		
		var vm = this;
		
		vm.onCreateNewProjectButtonClick = onCreateNewProjectButtonClick;
		
		function onCreateNewProjectButtonClick() {
			ProjectManagementService.createNewProject(vm.projectName).then(function() {
				vm.success = true;
				vm.error = false;
			}, function(error) {
				vm.error = true;
				vm.success = false;
			});
		}

	}]);