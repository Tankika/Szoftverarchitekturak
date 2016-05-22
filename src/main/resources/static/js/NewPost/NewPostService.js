angular.module('ServiceFinder.NewPost')

	.service('NewPostService', ['$http', '$q', function($http, $q) {
		this.send = send;
		
		function send(post) {
			var deferred = $q.defer(),
				request = {
					title : post.title,
					description : post.description,
					zipCode: post.zipCode,
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
		
	}]);