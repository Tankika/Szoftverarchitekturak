(function() {
	'use strict';
	
	angular.module('ServiceFinder')

		.config(['$routeProvider', function($routeProvider) {
			$routeProvider
			.when('/', {
				templateUrl : 'js/home/home.html',
				controller : 'HomeController',
				controllerAs: 'homeCtrl'
			})
			.when('/newPost', {
				templateUrl : 'js/NewPost/newPost.html',
				controller : 'NewPostController',
				controllerAs: 'newPostCtrl'
			})
			.when('/post', {
				templateUrl : 'js/Post/post.html',
				controller : 'PostController',
				controllerAs: 'postCtrl'
			})
			.when('/404', {
				templateUrl : 'js/404/404.html'
			})
			.otherwise('/404');
		
		}])
		.config(['$httpProvider', function($httpProvider) {
		
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		
		}])
		.config(['$locationProvider', function($locationProvider) {
			
			$locationProvider.html5Mode(true);
			
		}])
		
		.config(function(uiGmapGoogleMapApiProvider) {
		    uiGmapGoogleMapApiProvider.configure({
		        key: '	AIzaSyCPneN_9KImqzqziQviiPcCir6X1F-atrY',
		        v: '3.20',
		        language: "hu",
		        libraries: 'weather,geometry,visualization'
		    });
		});
	
}());