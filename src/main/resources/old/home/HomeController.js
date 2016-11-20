angular.module('ServiceFinder.Home')

	.controller('HomeController', ['HomeService', 'NewPostService', '$route', '$scope', function(HomeService, NewPostService, $route, $scope) {
		'use strict';
		
		var self = this;
		
		self.loading = 0;
		self.paging = {
			page: 1,
			pageSize: 5,
			totalPosts: 0
		};
		self.filter = {};
		self.categories = [];
		
		self.getPosts = getPosts;
		self.doFilter = doFilter;
		self.resetFilter = resetFilter; 
		
		getPosts();

		self.loading++;
		NewPostService.getCategories().then(function(data) {
			self.categories = data.categories;
			self.loading--;
		});
		
		function getPosts() {
			self.loading++;
			
			HomeService.getPosts(self.paging, self.filter).then(function(data) {
				self.posts = data.posts;
				self.paging.totalPosts = data.totalPosts;
				
				self.loading--;
			});
		}
		
		function doFilter() {
			self.paging.page = 1;
			getPosts();
		}
		
		function resetFilter() {
			self.filter = {};
			doFilter();
		}
		
	}]);