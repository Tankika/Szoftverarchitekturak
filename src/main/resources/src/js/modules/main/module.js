angular.module('BugTracker.Main', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider.state('main', {
		url: '/',
		abstract: true,
		templateUrl: 'js/modules/main/Main.html',
		controller: 'MainController as mainController'
	})
}]);