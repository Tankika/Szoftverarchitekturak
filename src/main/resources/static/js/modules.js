angular.module('ServiceFinder.User', []);
angular.module('ServiceFinder.Home', [ 'ServiceFinder.User' ]);
angular.module('ServiceFinder.Login', [ 'ServiceFinder.User' ]);

angular.module('ServiceFinder', [ 'ServiceFinder.Home', 'ServiceFinder.Login', 'ServiceFinder.User', 'ngRoute', 'ui.bootstrap', 'ngAnimate' ]);