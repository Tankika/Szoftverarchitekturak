angular.module('BugTracker.Settings')
	.controller('UserManagementController', ['UserManagementService', 'UserHandlerService', 'userManagementPreload', 'projectList', function(UserManagementService, UserHandlerService, userManagementPreload, projectList) {
		'use strict';
		
		var vm = this;
		
		vm.onSignupButtonClick = onSignupButtonClick;
		vm.isEmailFree = isEmailFree;
		vm.roles = userManagementPreload.roles;
		vm.userProjectList = [];
		vm.selectedProject = {};
		vm.projectList = projectList;
		
		vm.removeUserFromProject = removeUserFromProject;
		vm.addUserToProject = addUserToProject;
		
		vm.credentials = {};
		
		function addUserToProject() {
			vm.userProjectList.push(vm.selectedProject);
		}
		
		function removeUserFromProject(index) {
			vm.userProjectList.splice(index, 1);
		}
		
		function onSignupButtonClick() {
			UserManagementService.signup(vm.credentials, vm.userProjectList).then(function(data) {
				vm.success = true;
				vm.error = false;
			}, function(error) {
				vm.error = true;
				vm.success = false;
			});
		}
		
		function isEmailFree(email) {
			return UserHandlerService.isEmailFree(email).then(function(data) {
				return data.emailFree;
			}, function(error) {
				return error;
			});
		}

	}]);