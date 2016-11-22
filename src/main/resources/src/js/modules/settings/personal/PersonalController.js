angular.module('BugTracker.Settings')
	.controller('PersonalController', [function() {
		'use strict';
		
		var vm = this;

		vm.onSavePersonalSettings = onSavePersonalSettings;
		
		vm.credentials = {};
		
		function onSavePersonalSettings() {
			// TODO
		}
	}]);