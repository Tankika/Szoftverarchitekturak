angular.module('BugTracker.Settings')
	.controller('UserManagementController', ['UserManagementService', 'UserHandlerService', 'userManagementPreload', function(UserManagementService, UserHandlerService, userManagementPreload) {
		'use strict';
		
		var vm = this;
		
		vm.onSignupButtonClick = onSignupButtonClick;
		vm.isEmailFree = isEmailFree;
		vm.roles = userManagementPreload.roles;
		
		vm.credentials = {};
		
		function onSignupButtonClick() {
			UserManagementService.signup(vm.credentials).then(function(data) {
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