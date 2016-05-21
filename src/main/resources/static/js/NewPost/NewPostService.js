angular.module('ServiceFinder.NewPost')

	.service('NewPostService', ['$http', '$q', function($http, $q) {
		this.send = send;
		
		function send(post) {
			var deferred = $q.defer(),
				request = {
					entry : post
				};
			
			$http.post("/sendPost", request).then(function(response) {
				deferred.resolve(response.data);
			}, function(error) {
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
	}]);