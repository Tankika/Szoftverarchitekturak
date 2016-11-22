angular.module('BugTracker.Settings')
	.controller('PersonalController', ['PersonalService', function(PersonalService) {
		'use strict';
		
		var vm = this;

		vm.onSavePersonalSettings = onSavePersonalSettings;
		vm.onChangePasswordButtonClick = onChangePasswordButtonClick;
		
		vm.credentials = {};
		
		function onSavePersonalSettings() {
			// TODO
		}
		
		function onChangePasswordButtonClick() {
			PersonalService.changeUserPassword(vm.newPassword).then(function() {
				console.log("000");
			});
		}
	}]);