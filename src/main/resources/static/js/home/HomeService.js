angular.module('ServiceFinder.Home')

	.service('HomeService', ['$http', '$q', function($http, $q) {
		this.send = send;
		this.getPosts = getPosts;
		
		function send(post) {
			var deferred = $q.defer(),
				request = {
						entry : post
				};
			
			$http.post("/sendPost", request).then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
		function getPosts() {
			var deferred = $q.defer();
			
			$http.get("listPosts").then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
	}]);