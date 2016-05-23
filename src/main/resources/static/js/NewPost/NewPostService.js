angular.module('ServiceFinder.NewPost')

	.service('NewPostService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.send = send;
		this.checkAddress = checkAddress;
		
		function send(post) {
			var deferred = $q.defer(),
				request = {
					title : post.title,
					description : post.description,
					postalCode: post.postalCode,
					priceMin: post.priceMin,
					priceMax: post.priceMax,
					name: post.name,
					phone: post.phone
				};
			
			$http.post("/sendPost", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
		function checkAddress(postalCode) {
			var deferred = $q.defer(),
				request = {
					postalCode: postalCode
				};
			
			$http.post("/checkAddress", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);