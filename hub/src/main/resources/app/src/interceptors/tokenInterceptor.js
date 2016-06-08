export default angular.module('jarvis-hub.interceptors.tokenInterceptor', [])
	.factory('tokenInterceptor', ($window) => {
		'ngInject';
		return {
			request(config) {
				const token = $window.localStorage.getItem('token');
				if(token) {
					config.headers['Authorization'] = `Token ${token}`;
				}
				return config;
			},
			response(response) {
				//unauthorized
				if (response.status === 401) {
					$window.localStorage.removeItem('token');
				}
				return response;
			}
		}
})