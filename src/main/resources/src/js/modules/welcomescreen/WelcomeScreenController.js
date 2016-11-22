angular.module('BugTracker.WelcomeScreen')
	.controller('WelcomeScreenController', ['UserHandlerService', function(UserHandlerService) {
		'use strict';
		
		var vm = this;
		vm.login = login;
		
		vm.isLoggedIn = UserHandlerService.isLoggedIn;
		
		vm.credentials = {};
		
		function login() {
			UserHandlerService.login(vm.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					vm.error = false;
					vm.loginFailed = false;
				} else {
					vm.error = true;
				}
			}
			
			function handleError(error) {
				if(error.status === 401) {
					vm.loginFailed = true;			
				} else {
					vm.error = true;			
				}
			}
		}
		
	}]);