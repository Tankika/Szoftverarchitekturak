angular.module('ServiceFinder.NewPost')

	.controller('NewPostController', ['NewPostService', 'UserService', '$location', '$q', function(NewPostService, UserService, $location, $q) {
		'use strict';
		
		var self = this;
		
		self.postData = {};
		self.cityValidation = {};
		self.notFoundMessage = "";
		
		self.send = send;
		self.checkAddress = checkAddress;
		
		function send() {
			NewPostService.send(self.postData).then(function() {
				$location.path("/");
			});
		}
		
		function checkAddress(postalCode) {
			var deferred = $q.defer();
			
			NewPostService.checkAddress(postalCode)
			.then(function(data) {
				self.cityValidation.city = data.city;
				delete self.cityValidation.notFoundMessage;
				
				deferred.resolve();
			}, function(error) {
				if(error.status === 400 || error.status === 404) {
					self.cityValidation.notFoundMessage = error.data.message;	
				} else {
					self.cityValidation.notFoundMessage = "Ismeretlen hiba történt!";
				}
				delete self.cityValidation.city;
				
				deferred.reject();
			});
			
			return deferred.promise;
		}
		
	}]);