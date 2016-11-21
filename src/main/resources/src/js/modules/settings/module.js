angular.module('BugTracker.Settings', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider
	.state('main.settings', {
		url: 'settings/',
		templateUrl: 'js/modules/settings/Settings.html',
		controller: 'SettingsController as vm'
	})
	.state('main.settings.personal', {
		url: 'personal/',
		templateUrl: 'js/modules/settings/personal/Personal.html',
		controller: 'PersonalController as PersonalSettingsController'
	})
	.state('main.settings.usermanagement', {
		url: 'usermanagement/',
		templateUrl: 'js/modules/settings/usermanagement/UserManagement.html',
		controller: 'UserManagementController as UserManagementSettingsController'
	})
}]);