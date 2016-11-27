angular.module('BugTracker.Settings')
	.controller('UserManagementController', ['UserManagementService', 'UserHandlerService', 'userManagementPreload', 'projectList', function(UserManagementService, UserHandlerService, userManagementPreload, projectList) {
		'use strict';
		
		var vm = this;
		
		vm.onSignupButtonClick = onSignupButtonClick;
		vm.isEmailFree = isEmailFree;
		vm.roles = userManagementPreload.roles;
		vm.userProjectList = [];
		vm.selectedProject = {};
		vm.selectedRole = {};
		vm.projectList = projectList;
		
		vm.removeUserFromProject = removeUserFromProject;
		vm.addUserToProject = addUserToProject;
		vm.onModifyUserButtonClick = onModifyUserButtonClick;
		vm.getUserData = getUserData;
		
		vm.credentials = {};
		
		function addUserToProject() {
			if(!vm.selectedProject.id) {
				return;
			}
			
			var found = false;
			for(var i = 0; i < vm.userProjectList.length; i++) {
				if (vm.selectedProject.id === vm.userProjectList[i].id) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				vm.userProjectList.push(vm.selectedProject);
			}
		}
		
		function removeUserFromProject(index) {
			vm.userProjectList.splice(index, 1);
		}
		
		function getUserData() {
			UserManagementService.getUserData(vm.userEmail).then(function(data) {
				vm.userProjectList = [];
				vm.selectedRole = {};
				for (var i = 0; i < vm.projectList.length; i++) {
					if (data.projectIds.indexOf(vm.projectList[i].id) !== -1) {
						vm.userProjectList.push(vm.projectList[i]);
					}
				}
				for (var i = 0; i < vm.roles.length; i++) {
					if (data.roleId === vm.roles[i].id) {
						vm.selectedRole = vm.roles[i];
					} 
				}
			});
		}
		
		function onSignupButtonClick() {
			UserManagementService.createUser(vm.credentials, vm.selectedRole, vm.userProjectList).then(function(data) {
				vm.success = true;
				vm.error = false;
			}, function(error) {
				vm.error = true;
				vm.success = false;
			});
		}
		
		function onModifyUserButtonClick() {
			UserManagementService.modifyUser(vm.userEmail, vm.selectedRole, vm.userProjectList).then(function(data) {
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