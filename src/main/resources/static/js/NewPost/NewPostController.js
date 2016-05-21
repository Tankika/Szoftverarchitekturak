angular.module('ServiceFinder.NewPost')

	.controller('NewPostController', ['NewPostService', 'UserService', '$location', function(HomeService, UserService, $location) {
		var self = this;
		
		self.send = send;
		
		function send() {
			HomeService.send(self.postInput).then(function() {
				$location.path("/");
			});
		}
		
	}]);