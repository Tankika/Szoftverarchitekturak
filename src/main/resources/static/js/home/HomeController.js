angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', 'UserService', '$route', '$scope', function(HomeService, UserService, $route, $scope) {
		'use strict';
		
		var self = this;
		
		self.loaded = false;
		self.paging = {
			page: 1,
			pageSize: 5,
			totalPosts: 0
		};
		
		self.getPosts = getPosts;
		
		getPosts();
		
		function getPosts() {
			self.loaded = false;
			
			HomeService.getPosts(self.paging).then(function(data) {
				self.posts = data.posts;
				self.paging.totalPosts = data.totalPosts;
				self.loaded = true;
			});
		}
		
	}]);