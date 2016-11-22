angular.module('BugTracker.Main')
	.controller('MainController', ['UserHandlerService', 'MainService', '$state', function(UserHandlerService, MainService, $state) {
		'use strict';
		
		var mainController = this;
		mainController.isLoggedIn = UserHandlerService.isLoggedIn;
		mainController.isAuthorised = UserHandlerService.isAuthorised;
		mainController.logout = logout;
		mainController.addNewProject = addNewProject;
		mainController.onClickOnProject = onClickOnProject;
		
		function logout() {
			UserHandlerService.logout().then(function() {
				$state.go('main.welcomescreen');
			});
		}
		
		function addNewProject() {
			
		}
		
		function onClickOnProject(project) {
			MainService.navigateToProject(project.id);
		}
	}]);