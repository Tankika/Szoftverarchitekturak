angular.module('ServiceFinder.Home', [])
angular.module('ServiceFinder.Login', [])

angular.module('ServiceFinder', [ 'ngRoute', 'ServiceFinder.Home', 'ServiceFinder.Login' ])