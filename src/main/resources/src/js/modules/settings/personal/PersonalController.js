angular.module('BugTracker.Settings')
	.controller('PersonalController', ['PersonalService', 'UserHandlerService', function(PersonalService, UserHandlerService) {
		'use strict';
		
		var vm = this;

		vm.onChangePasswordButtonClick = onChangePasswordButtonClick;
		vm.email = UserHandlerService.getUsername();
		vm.credentials = {};
		
		
		function onChangePasswordButtonClick() {
			PersonalService.changeUserPassword(vm.newPassword).then(function() {
				vm.success = true;
				vm.error = false;
			}, function() {
				vm.success = false;
				vm.error = true;
			});
		}
	}]);