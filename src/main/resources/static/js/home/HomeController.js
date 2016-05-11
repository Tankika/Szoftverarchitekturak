angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', '$route', function(HomeService, $route) {
		var self = this;
		self.send = send;
		
	//		$http.get('/resource/').success(function(data) {
	//			self.greeting = data;
	//		})
		
		getPosts();
		
		function send() {
			HomeService.send(self.postInput).then(function() {
				$route.reload();
			});
		}
		
		function getPosts() {
			HomeService.getPosts().then(function(data) {
				self.posts = data.data.posts;
			});
		}
		
	}]);