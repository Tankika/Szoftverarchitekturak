angular.module('ServiceFinder.NewPost')

	.controller('NewPostController', ['NewPostService', 'UserService', '$location', function(HomeService, UserService, $location) {
		var self = this;
		
		self.postData = {};
		
		self.send = send;
		
		function send() {
			HomeService.send(self.postData).then(function() {
				$location.path("/");
			});
		}
		
	}]);