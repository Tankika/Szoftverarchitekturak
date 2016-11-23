angular.module('BugTracker.WelcomeScreen')
	.controller('WelcomeScreenController', ['UserHandlerService', 'WelcomeScreenService', '$state', function(UserHandlerService, WelcomeScreenService, $state) {
		'use strict';
		
		var vm = this;
		vm.login = login;
		
		vm.isLoggedIn = UserHandlerService.isLoggedIn;
		vm.getUsername = UserHandlerService.getUsername;
		vm.credentials = {};
		vm.projectList = [];
		vm.openProjectDetails = openProjectDetails;
		
		if (vm.isLoggedIn()) {
			getProjects();
		}		
		
		function getProjects() {
			WelcomeScreenService.listProjects().then(function(data) {
				vm.projectList = data.projects;
			});
		}
		
		function openProjectDetails(index) {
			$state.go('main.project', {
				id: vm.projectList[index].id
			});
		}
		
		function login() {
			UserHandlerService.login(vm.credentials).then(handleSuccess, handleError);
			
			function handleSuccess(data) {
				if (angular.isObject(data) && data.authenticated === true) {
					vm.error = false;
					vm.loginFailed = false;
					getProjects();
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