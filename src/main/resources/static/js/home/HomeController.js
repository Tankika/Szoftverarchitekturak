angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', 'UserService', '$route', function(HomeService, UserService, $route) {
		var self = this;
		
		self.user = {};
		
		getPosts();
		UserService.getUser().then(function(data) {
			self.user = data;
		});
		
		function getPosts() {
			HomeService.getPosts().then(function(data) {
				self.posts = data;
			});
		}
		
	}]);