angular.module('BugTracker.Settings')
	.controller('UserManagementController', ['UserManagementService', 'UserHandlerService', function(UserManagementService, UserHandlerService) {
		'use strict';
		
		var vm = this;
		
		vm.onSignupButtonClick = onSignupButtonClick;
		vm.isEmailFree = isEmailFree;
		
		vm.credentials = {};
		
		function onSignupButtonClick() {
			UserManagementService.signup(vm.credentials).then(function(data) {
				// empty
			}, function(error) {
				vm.error = true;
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