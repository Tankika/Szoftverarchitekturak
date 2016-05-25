angular.module('ServiceFinder.NewPost')

	.service('NewPostService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.checkAddress = checkAddress;
		this.getCategories = getCategories;
		
		function checkAddress(postalCode) {
			var deferred = $q.defer(),
				request = {
					postalCode: postalCode
				};
			
			$http.post("/post/checkAddress", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
		function getCategories() {
			var deferred = $q.defer();
			
			$http.get("/post/getCategories").then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);