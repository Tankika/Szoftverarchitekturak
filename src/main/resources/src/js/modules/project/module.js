angular.module('BugTracker.Project', [])

.config(['$stateProvider', function($stateProvider) {
	$stateProvider.state('main.project', {
		url: 'project/{id}',
		templateUrl: 'js/modules/project/Project.html',
		controller: 'ProjectController as vm',
		resolve: {
			issueList: ['ProjectService', '$stateParams', function(ProjectService, $stateParams) {
				return ProjectService.getIssues($stateParams.id);
			}]
		}
	})
}]);