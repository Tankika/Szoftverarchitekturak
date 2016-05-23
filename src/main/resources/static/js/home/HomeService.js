angular.module('ServiceFinder.Home')

	.service('HomeService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.getPosts = getPosts;
		
		function getPosts(paging) {
			var deferred = $q.defer(),
				request = {
					page: paging.page,
					pageSize: paging.pageSize
				};
			
			$http.post("/listPosts", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);