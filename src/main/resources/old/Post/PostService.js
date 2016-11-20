angular.module('ServiceFinder.Post')

	.service('PostService', ['$http', '$q', function($http, $q) {
		'use strict';
		
		this.getPost = getPost;
		
		function getPost(id) {
			var deferred = $q.defer();
			
			$http.get("/post/getPost/" + id).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);