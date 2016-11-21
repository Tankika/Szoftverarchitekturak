angular.module('BugTracker.WelcomeScreen', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider.state('main.welcomescreen', {
		url: 'welcome/',
		templateUrl: 'js/modules/welcomescreen/WelcomeScreen.html',
		controller: 'WelcomeScreenController as welcomeScreenController'
	})
}]);