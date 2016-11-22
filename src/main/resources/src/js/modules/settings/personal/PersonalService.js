angular.module('BugTracker.Settings')
	.service('PersonalService', ['$http', function($http) {
		'use strict';
	
		this.changeUserPassword = changeUserPassword;
		
		function changeUserPassword(newPassword) {
			return $http.post('/user/changePassword', {newPassword: newPassword});
		}
	}]);