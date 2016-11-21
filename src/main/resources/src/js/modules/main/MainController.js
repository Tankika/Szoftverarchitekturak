angular.module('BugTracker.Main')
	.controller('MainController', ['UserHandlerService', 'MainService', function(UserHandlerService, MainService) {
		'use strict';
		
		var mainController = this;
		mainController.isLoggedIn = true;
		mainController.logout = logout;
		mainController.addNewProject = addNewProject;
		mainController.onClickOnProject = onClickOnProject;
		
		function logout() {
			
		}
		
		function addNewProject() {
			
		}
		
		function onClickOnProject(project) {
			MainService.navigateToProject(project.id);
		}
	}]);