export default ($scope, $http, $window, $state, $rootScope) => {
	'ngInject';
	Object.assign($scope, {
		user : {
			username : '',
			password : ''
		},
		newUser : {
			username : '',
			password : ''
		},
		login() {
			$http.post('/api/users/login/', $scope.user)
				.then(({data}) => {
					$window.localStorage.setItem('token', data.token);
					return $http.get('/api/users/me');
				})
				.then(({data : user}) => {
					$rootScope.user = user;
					$state.go('groups');
				}, (err) => {
					if (err.status === 404) {
						alert('User not found');
					} else if (err.status === 403) {
						alert('Invalid password');
					} else {
						alert('Could not log in');
					}
				});
		},
		register() {
			const username = $scope.newUser.username;
			$http.post('/api/users/', $scope.newUser)
				.then(() => {
					alert('User ${username} created.'); // TODO validation?
					$scope.newUser.username = '';
					$scope.newUser.password = '';
				}, (err) => {
					let message = 'Could not create user';
					if (err.status >= 400 && err.status < 500) {
						message += ' ' + err.data;
					}
					alert(message);
				})
		}
	})
}
