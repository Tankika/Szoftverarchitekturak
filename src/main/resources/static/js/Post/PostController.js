angular.module('ServiceFinder.Post')

	.controller('PostController', ['PostService', 'UserService', '$routeParams', '$location', function(PostService, UserService, $routeParams, $location) {
		'use strict';
		
		var self = this,
			postId = parseInt($routeParams.id, 10);
		
		self.loaded = false;
		self.map = {
			center: {
				latitude: 0,
				longitude: 0
			},
			zoom: 12,
			options: {
				streetViewControl: false
			},
			marker: {
				coords : {
					latitude: 0,
					longitude: 0
				}
			}
		};
		
		if(isFinite(postId)) {
			PostService.getPost(postId)
			.then(function(data) {
				self.post = data;
				
				self.map.center.latitude = data.latitude;
				self.map.center.longitude = data.longitude;
				angular.extend(self.map.marker.coords, self.map.center);
				self.loaded = true;
			}, function(error) {
				$location.path("/404");
			});
		} else {
			$location.path("/404");
		}
		
		UserService.getUser().then(function(data) {
			self.user = data;
		});
		
	}]);