angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', 'UserService', '$route', function(HomeService, UserService, $route) {
		'use strict';
		
		var self = this;
		
		self.user = {};
		self.paging = {
			page: 1,
			pageSize: 5,
			totalPosts: 0
		};
		
		self.getPosts = getPosts;
		
		getPosts();
		UserService.getUser().then(function(data) {
			self.user = data;
		});
		
		function getPosts() {
			HomeService.getPosts(self.paging).then(function(data) {
				self.posts = data.posts;
				self.paging.totalPosts = data.totalPosts;
			});
		}
		
	}]);