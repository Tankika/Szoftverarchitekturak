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
		controller: 'PersonalController as vm'
	})
	.state('main.settings.modifyuser', {
		url: 'modifyuser/',
		templateUrl: 'js/modules/settings/usermanagement/ModifyUser.html',
		controller: 'UserManagementController as vm',
		resolve: {
			userManagementPreload: ['UserManagementService', function(UserManagementService) {
				return UserManagementService.getUserManagementPreload();
			}],
			projectList: ['UserManagementService', function(UserManagementService) {
				return UserManagementService.getProjectList();
			}]
		}
	})
	.state('main.settings.createuser', {
		url: 'createuser/',
		templateUrl: 'js/modules/settings/usermanagement/CreateUser.html',
		controller: 'UserManagementController as vm',
		resolve: {
			userManagementPreload: ['UserManagementService', function(UserManagementService) {
				return UserManagementService.getUserManagementPreload();
			}],
			projectList: ['UserManagementService', function(UserManagementService) {
				return UserManagementService.getProjectList();
			}]
		}
	})
	.state('main.settings.projectmanagement', {
		url: 'projectmanagement/',
		templateUrl: 'js/modules/settings/projectmanagement/ProjectManagement.html',
		controller: 'ProjectManagementController as vm'
	});
}]);