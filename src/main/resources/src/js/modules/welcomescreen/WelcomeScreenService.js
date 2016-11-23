angular.module('BugTracker.WelcomeScreen')
	.service('WelcomeScreenService', ['$http', function($http) {
		'use strict';
		
		this.listProjects = listProjects;
		function listProjects() {
			return $http.get('/issue/listProjects').then(function(response) {
				return response.data;
			});
		}
	}]);