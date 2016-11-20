angular.module('BugTracker.Settings', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider.state('main.settings', {
		url: '/settings',
		templateUrl: 'js/modules/settings/Settings.html',
		controller: 'SettingsController as vm'
	})
}]);