angular.module('ServiceFinder.Home')

	.controller('homeCtrl', ['$http', '$route', function($http, $route) {
		var self = this;
	//		$http.get('/resource/').success(function(data) {
	//			self.greeting = data;
	//		})
		getPosts();
		
		self.send = function() {
			$http.post("/sendPost", {
				entry : self.postInput
			}).then(function() {
				$route.reload();
			});
		};
		
		function getPosts() {
			$http.get("listPosts").then(function(data) {
				self.posts = data.data.posts;
			});
		}
	}])