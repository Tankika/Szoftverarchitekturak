describe('HomeController test', function() {
	beforeEach(module('ServiceFinder.Home'));

	var $controller,
		HomeService = {
			getPosts: function() {
				return {
					then : function(callback) {
						callback({
							posts : ["post1", "post2"]
						});
					}
				};
			}
		};


	beforeEach(function() {
		
		module({
			HomeService: HomeService
		});
		
		inject(function(_$controller_){
			$controller = _$controller_;
		});
	});
	
	
	describe('', function() {
		it('', function() {
			var controller = $controller('HomeController', { $route : {} } );
			expect(controller.posts.length).toEqual(2);
		});
	});
});