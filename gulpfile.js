var gulp = require('gulp');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var aggregate = require('gulp-aggregate');
var watch = require('gulp-watch');

gulp.task('sass', function() {
	return gulp.src(['src/main/resources/src/scss/**/*.css', 'src/main/resources/src/scss/**/*.scss'])
		.pipe(sass())
		.pipe(concat('bundled.css'))
		.pipe(gulp.dest('src/main/resources/static/'));
});

gulp.task('aggregateJs', function() {
	return gulp.src([
			'src/main/resources/src/js/3rdparty/angular/angular.min.js',
			'src/main/resources/src/js/3rdparty/angular/angular-ui-router.min.js',
			'src/main/resources/src/js/3rdparty/angular/ui-bootstrap-2.2.0.min.js',
			'src/main/resources/src/js/3rdparty/jquery/jquery-3.1.1.min.js',
			'src/main/resources/src/js/3rdparty/momentjs/moment-with-locales.min.js',
			'src/main/resources/src/js/3rdparty/modules.js',
			'src/main/resources/src/js/modules/common/module.js',
			'src/main/resources/src/js/modules/common/UserHandlerService.js',
			'src/main/resources/src/js/modules/issue/module.js',
			'src/main/resources/src/js/modules/issue/IssueService.js',
			'src/main/resources/src/js/modules/issue/IssueController.js',
			'src/main/resources/src/js/modules/main/module.js',
			'src/main/resources/src/js/modules/main/MainService.js',
			'src/main/resources/src/js/modules/main/MainController.js',
			'src/main/resources/src/js/modules/project/module.js',
			'src/main/resources/src/js/modules/project/ProjectService.js',
			'src/main/resources/src/js/modules/project/ProjectController.js',
			'src/main/resources/src/js/modules/settings/module.js',
			'src/main/resources/src/js/modules/settings/SettingsService.js',
			'src/main/resources/src/js/modules/settings/SettingsController.js',
			'src/main/resources/src/js/modules/welcomescreen/module.js',
			'src/main/resources/src/js/modules/welcomescreen/WelcomeScreenService.js',
			'src/main/resources/src/js/modules/welcomescreen/WelcomeScreenController.js',
			'src/main/resources/src/js/app.js'
		])
		.pipe(concat('bundled.js'))
		.pipe(gulp.dest('src/main/resources/static/'));
});

gulp.task('copy', function() {
	return gulp.src(['src/main/resources/src/**/*.html',
					 'src/main/resources/src/**/*.png',
					 'src/main/resources/src/**/*.eot',
					 'src/main/resources/src/**/*.woff2',
					 'src/main/resources/src/**/*.woff',
					 'src/main/resources/src/**/*.ttf'])
			   .pipe(gulp.dest('src/main/resources/static/'));
});

gulp.task('watchChanges', ['sass', 'aggregateJs', 'copy'], function() {
	gulp.watch('src/main/resources/src/scss/**', ['sass']);
	gulp.watch('src/main/resources/src/js/**', ['aggregateJs', 'copy']);
});