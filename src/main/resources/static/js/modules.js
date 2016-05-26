angular.module('ServiceFinder.User', [ ]);
angular.module('ServiceFinder.Home', [ 'ServiceFinder.User', 'ServiceFinder.NewPost' ]);
angular.module('ServiceFinder.Header', [ 'ServiceFinder.User' ]);
angular.module('ServiceFinder.NewPost', [ 'ngFileUpload' ]);
angular.module('ServiceFinder.Post', [ 'uiGmapgoogle-maps' ]);

angular.module('ServiceFinder', [ 'ServiceFinder.Home', 'ServiceFinder.Header', 'ServiceFinder.User', 'ServiceFinder.NewPost', 'ServiceFinder.Post', 'ngRoute', 'ui.bootstrap', 'ngAnimate', 'ui.validate' ]);