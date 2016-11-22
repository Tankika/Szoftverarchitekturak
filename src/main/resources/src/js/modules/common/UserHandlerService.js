angular.module('BugTracker.Common')
	.service('UserHandlerService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.getUserAuthorities = getUserAuthorities;
		this.isEmailFree = isEmailFree;
		this.login = login;
		
		function getUserAuthorities() {
			
		}
		
		function isEmailFree(email) {
			return $http.post("/user/checkEmail", {email: email}).then(function(response) {
				return response.data;
			}, function(error) {
				return error;
			});
		}
		
		function login(credentials) {
			var deferred = $q.defer(),
				headers = {};
			
			if(angular.isObject(credentials)) {
				headers.authorization = "Basic " + btoa(credentials.email + ":" + credentials.password);
			}
			
			$http.get("/user/user", { headers: headers })
			.then(function(response) {
				changeUser(response.data);
				deferred.resolve(response.data);
			}, function(error) {
				setUserLoggedOff();
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
	}]);