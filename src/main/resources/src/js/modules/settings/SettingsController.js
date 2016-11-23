angular.module('BugTracker.Settings')
	.controller('SettingsController', ['UserHandlerService', function(UserHandlerService) {
		'use strict';
		
		var vm = this;
		vm.isAuthorised = UserHandlerService.isAuthorised;
		
	}]);