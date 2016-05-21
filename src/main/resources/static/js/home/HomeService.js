angular.module('ServiceFinder.Home')

	.service('HomeService', ['$http', '$q', function($http, $q) {
		this.getPosts = getPosts;
		
		function getPosts() {
			var deferred = $q.defer();
			
			$http.get("/listPosts").then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);