angular.module('BugTracker.Common')
	.service('UserHandlerService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.isEmailFree = isEmailFree;
		this.login = login;
		this.logout = logout;
		this.isAuthorised = isAuthorised;
		this.isLoggedIn = isLoggedIn;
		this.getUsername = getUsername;
		
		var authorities = [];
		var principal = {};
		var loggedIn = false;
		
		function isLoggedIn() {
			return loggedIn; 
		}
		
		function isAuthorised(authority) {
			return authorities.indexOf(authority) !== -1;
		}
		
		function getUsername() {
			return principal.username;
		}
		
		function isEmailFree(email) {
			return $http.post("/user/checkEmail", {email: email}).then(function(response) {
				return response.data;
			}, function(error) {
				return error;
			});
		}
		
		function login(credentials) {
			var headers = {};
			
			if (angular.isObject(credentials)) {
				headers.authorization = "Basic " + btoa(credentials.email + ":" + credentials.password);
			}
			
			return $http.get("/user/user", { headers: headers }).then(function(response) {
				storeAuthorities(response.data.authorities);
				storeUserData(response.data.principal);
				loggedIn = true;
				return response.data;
			}, function(error) {
				// setUserLoggedOff()
				return error;
			});
		}
		
		function logout() {		
			return $http.get("/user/logout").then(function() {
				loggedIn = false;
			});
		}
		
		function storeAuthorities(authorityData) {
			authorities = [];
			if (angular.isArray(authorityData)) {
				for(var i = 0; i < authorityData.length; i++) {
					authorities.push(authorityData[i].authority);
				}
			}
		}
		
		function storeUserData(data) {
			principal = data;
		}
	}]);