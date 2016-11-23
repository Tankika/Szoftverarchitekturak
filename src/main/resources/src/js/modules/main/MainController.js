angular.module('BugTracker.Main')
	.controller('MainController', ['UserHandlerService', 'MainService', '$state', function(UserHandlerService, MainService, $state) {
		'use strict';
		
		var mainController = this;
		mainController.isLoggedIn = UserHandlerService.isLoggedIn;
		mainController.isAuthorised = UserHandlerService.isAuthorised;
		mainController.logout = logout;
		
		function logout() {
			UserHandlerService.logout().then(function() {
				$state.go('main.welcomescreen');
			});
		}
		
	}]);