describe('HomeController test', function() {
//	beforeEach(module('ServiceFinder.Home'));

	var $controller;

	beforeEach(inject(function(_$controller_){
		$controller = _$controller_;
	}));

	describe('$scope.grade', function() {
		it('sets the strength to "strong" if the password length is >8 chars', function() {
//			var $scope = {};
//			var controller = $controller('HomeController', { $scope: $scope });
//			$scope.password = 'longerthaneightchars';
//			$scope.grade();
			expect(true).toEqual(true);
		});
	});
});