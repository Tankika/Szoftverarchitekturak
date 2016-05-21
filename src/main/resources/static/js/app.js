angular.module('ServiceFinder')

	.config(function($routeProvider) {
	
		$routeProvider
		.when('/', {
			templateUrl : 'js/home/home.html',
			controller : 'HomeController',
			controllerAs: 'homeCtrl'
		})
		.otherwise('/');
	
	})
	.config(function($httpProvider) {
	
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	});