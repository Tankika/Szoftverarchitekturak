(function() {
	'use strict';
	
	angular.module('BugTracker', ['BugTracker.3rdparty',
								  'BugTracker.Common',
								  'BugTracker.Issue',
								  'BugTracker.Main',
								  'BugTracker.Project',
								  'BugTracker.Settings',
								  'BugTracker.WelcomeScreen'])

		.config(['$urlRouterProvider', '$httpProvider', function ($urlRouterProvider, $httpProvider) {
			console.log("ASD");
			$urlRouterProvider.otherwise(function ($injector, $location) {
				$injector.get('$state').go('main.welcomescreen');
			});
		}])
		.config(['$httpProvider', function($httpProvider) {
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		}])
		.config(['$locationProvider', function($locationProvider) {
			$locationProvider.html5Mode(true);
		}])
}());