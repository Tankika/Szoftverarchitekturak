angular.module('BugTracker.Main')
	.service('MainService', ['$state', function($state) {
		'use strict';

		this.navigateToProject = navigateToProject;
		
		function navigateToProject(projectId) {
			$state.go('main.project', {
				projectId: projectId
			});
		}
	}]);