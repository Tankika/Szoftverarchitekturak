angular.module('ServiceFinder.User', []);
angular.module('ServiceFinder.Home', [ 'ServiceFinder.User' ]);
angular.module('ServiceFinder.Header', [ 'ServiceFinder.User' ]);
angular.module('ServiceFinder.NewPost', []);


angular.module('ServiceFinder', [ 'ServiceFinder.Home', 'ServiceFinder.Header', 'ServiceFinder.User', 'ServiceFinder.NewPost', 'ngRoute', 'ui.bootstrap', 'ngAnimate', 'ui.validate' ]);