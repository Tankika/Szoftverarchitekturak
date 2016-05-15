angular.module('ServiceFinder')

	.config(function($routeProvider) {
	
		$routeProvider
		.when('/', {
			templateUrl : 'js/home/home.html',
			controller : 'HomeController',
			controllerAs: 'homeCtrl'
		})
		.when('/login', {
			templateUrl : 'js/login/login.html',
			controller : 'LoginController',
			controllerAs: 'loginCtrl'
		}).otherwise('/');
	
	})
	.config(function($httpProvider) {
	
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	});