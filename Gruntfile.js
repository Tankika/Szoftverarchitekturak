module.exports = function(grunt) {
	'use strict';
		
	var jshintFiles = ['Gruntfile.js',
	                   'src/main/resources/static/js/**/*.js',
	                   'src/test/resources/js/**/*.js',
	                   '!src/main/resources/static/js/3rdparty/*.js',
	                   '!src/test/resources/js/3rdparty/*.js'
	                   ];
	
    grunt.initConfig({
    	watch: {
			files: jshintFiles,
		    tasks: ['jshint']
		},
        jshint: {
			files: jshintFiles,
			options: {
				strict: true	
			}
        },
        karma: {
        	options: {
        		configFile: 'src/test/resources/karma.conf.js'
        	},
        	continuous: {
        		background: true
        	}
        }
    });
    
    grunt.registerTask('default', ['watch']);
    
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-watch');
};