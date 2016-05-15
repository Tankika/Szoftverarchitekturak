angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', 'UserService', '$route', function(HomeService, UserService, $route) {
		var self = this;
		
		self.send = send;
		self.user = {};
		
		getPosts();
		UserService.getUser().then(function(data) {
			self.user = data;
		});
		
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