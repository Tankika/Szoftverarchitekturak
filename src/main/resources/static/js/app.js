angular.module('ServiceFinder')

	.config(function($routeProvider) {
	
		$routeProvider
		.when('/', {
			templateUrl : 'js/home/home.html',
			controller : 'HomeController',
			controllerAs: 'controller'
		})
		.when('/login', {
			templateUrl : 'js/login/login.html',
			controller : 'navigationCtrl',
			controllerAs: 'controller'
		}).otherwise('/');
	
	})
	.config(function($httpProvider) {
	
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	});