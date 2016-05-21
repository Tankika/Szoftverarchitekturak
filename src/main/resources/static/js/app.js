angular.module('ServiceFinder')

	.config(['$routeProvider', function($routeProvider) {
	
		$routeProvider
		.when('/', {
			templateUrl : 'js/Home/home.html',
			controller : 'HomeController',
			controllerAs: 'homeCtrl'
		})
		.when('/newPost', {
			templateUrl : 'js/NewPost/newPost.html',
			controller : 'NewPostController',
			controllerAs: 'newPostCtrl'
		})
		.otherwise('/');
	
	}])
	.config(['$httpProvider', function($httpProvider) {
	
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	}])
	.config(['$locationProvider', function($locationProvider) {
		
		$locationProvider.html5Mode(true);
		
	}]);