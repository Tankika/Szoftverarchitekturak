angular.module('ServiceFinder.Home')

	.service('HomeService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.getPosts = getPosts;
		
		function getPosts(paging, filter) {
			var deferred = $q.defer(),
				request = {
					page: paging.page,
					pageSize: paging.pageSize,
					title: filter.title,
					priceMin: filter.priceMin,
					priceMax: filter.priceMax,
					city: filter.city,
					category: filter.category,
					startDate: filter.startDate,
					endDate: filter.endDate
				};
			
			$http.post("/post/listPosts", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);