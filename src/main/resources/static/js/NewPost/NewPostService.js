angular.module('ServiceFinder.NewPost')

	.service('NewPostService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.checkAddress = checkAddress;
		
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
		
	}]);