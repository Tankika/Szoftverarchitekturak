angular.module('ServiceFinder.User')

	.service('UserService', ['$http', '$q', function($http, $q) {
		var user,
			userChangedEventListeners = [];
		
		this.login = login;
		this.getUser = getUser;
		this.logout = logout;
		this.subscribe = subscribe; 
		
		function login(credentials) {
			var deferred = $q.defer(),
				headers = {};
			
			if(angular.isObject(credentials)) {
				headers.authorization = "Basic " + btoa(credentials.username + ":" + credentials.password);
			}
			
			$http.get("/user", { headers: headers })
			.then(function(response) {
				changeUser(response.data);
				deferred.resolve(response.data);
			}, function(error) {
				setUserLoggedOff();
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
		function getUser() {
			var deferred = $q.defer();
			
			if(angular.isObject(user)) {
				// if the user is cached, return it 
				deferred.resolve(user);
			} else {
				// otherwise, fetch from server
				$http.get("/user")
				.then(function(response) {
					changeUser(response.data);
					deferred.resolve(user);
				}, function(error) {
					setUserLoggedOff();
					deferred.resolve(user);
				});	
			}
			
			return deferred.promise;
		}
		
		function logout() {
			var deferred = $q.defer();
			
			$http.post("/logout", {})
			.then(function(response) {
				setUserLoggedOff();
				deferred.resolve(response.data);
			}, function(error) {
				setUserLoggedOff();
				deferred.reject(error);
			});
			
			return deferred.promise;
		}
		
		function subscribe(listener) {
			if(angular.isFunction(listener)) {
				userChangedEventListeners.push(listener);
				
				return function() {
					angular.forEach(userChangedEventListeners, function(element, index) {
						if(element === listener) {
							delete userChangedEventListeners[index];
						}
					});
				};
			}
		}
		
		function setUserLoggedOff() {
			changeUser({
				authenticated : false
			});
		}
		
		function changeUser(newUser) {
			user = newUser;
			angular.forEach(userChangedEventListeners, function(listener) {
				listener(newUser);
			});
		}
		
	}]);