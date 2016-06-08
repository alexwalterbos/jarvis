import angular from 'angular';
import ngSanitize from 'angular-sanitize';
import uiRouter from 'ui-router';
import groupsCtrl from './ctrls/groups'; // TODO CREATE THIS
import loginCtrl from './ctrls/login'; // TODO CREATE THIS
import tokenInterceptor from './interceptors/tokenInterceptor'

angular.module('jarvis-hub', [ngSanitize, uiRouter, tokenInterceptor.name])
	.config(($stateProvider, $urlRouterProvider, $httpProvider) => {
		'ngInject';
		$urlRouterProvider.otherwise('/');

		$stateProvider
			.state('login', {
				url : '/',
				templateUrl: '../pages/login.html',
				controller: loginCtrl
			})
			.state('groups', {
				url : '/groups',
				templateUrl : '../pages/groups.html',
				controller : groupsCtrl
			});

		$httpProvider.interceptors.push('tokenInterceptor');
	})
	.run(($http, $rootScope, $window, $state) => {
		'ngInject';
		if ($window.localStorage.getItem('token')) {
			$http.get('/api/users/me')
				.then(({data : user}) => {
					$rootScope.user = user;
					$state.go('groups');
				});
		}
	});

angular.element(document).ready(() => {
	angular.bootstrap(document, ['jarvis-hub']);
});
