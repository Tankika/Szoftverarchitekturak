angular.module('ServiceFinder.NewPost')

	.controller('NewPostController', ['NewPostService', 'UserService', '$location', '$q', 'Upload', function(NewPostService, UserService, $location, $q, Upload) {
		'use strict';
		
		var self = this;
		
		self.loaded = false;
		self.postData = {
			invalidFiles: []
		};
		self.cityValidation = {};
		self.notFoundMessage = "";
		self.progressBar = new ProgressBar();
		
		self.send = send;
		self.checkAddress = checkAddress;
		
		self.loaded = true;
		
		function send() {
			self.progressBar.reset();
			
			var upload = Upload.upload({
                url: '/post/sendPost',
                data: {
                    file: self.postData.files,
                    post: {
    					title : self.postData.title,
    					description : self.postData.description,
    					postalCode: self.postData.postalCode,
    					priceMin: self.postData.priceMin,
    					priceMax: self.postData.priceMax,
    					name: self.postData.name,
    					phone: self.postData.phone
    				}
                }
            });
			
			self.abort = upload.abort; 
            
			upload.then(function(response) {
				delete self.abort;
            	self.progressBar.type = "success";
            }, function(error) {
            	delete self.abort;
        		self.progressBar.type = "danger";
            }, function(evt) {
            	self.progressBar.value = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
		}
		
		function checkAddress(postalCode) {
			var deferred = $q.defer();
			
			NewPostService.checkAddress(postalCode)
			.then(function(data) {
				self.cityValidation.city = data.city;
				delete self.cityValidation.notFoundMessage;
				
				deferred.resolve();
			}, function(error) {
				if(error.status === 400 || error.status === 404) {
					self.cityValidation.notFoundMessage = error.data.message;	
				} else {
					self.cityValidation.notFoundMessage = "Ismeretlen hiba történt!";
				}
				delete self.cityValidation.city;
				
				deferred.reject();
			});
			
			return deferred.promise;
		}
		
		function ProgressBar() {
			
			this.reset = function() {
				this.value = 0;
				this.type = "info";
			};
			
			this.reset();
		}
		
	}]);