export default ($scope, $http, $window, $state, $rootScope) => {
	'ngInject';

	// Check if the user is authenticated
	if (!$window.localStorage.getItem('token')) {
		$state.go('login');
		return;
	}

	function clientLogout() {
		$window.localStorage.removeItem('token');
		$state.go('login');
	}

	function success(response) {
		console.log(response);
		// TODO
	}

	function fail(response) {
		console.log(response);
		// TODO
	}

	Object.assign($scope, {
		logout() {
			$http.delete(`/api/users/logout`)
				.then(clientLogout, clientLogout);
		},
		
		activate(group) {
			$http.get(`/api/groups/activate/${group.id}`)
				.then(success, fail);
		},
		deactivate(group) {
			$http.delete(`/api/groups/deactivate/${group.id}`);
		}, 
		activateAll() {
			$http.get(`/api/groups/activate_all`);
		},
		deactivateAll() {
			$http.delete(`/api/groups/deactivate_all`);
		},
		
		groups : []
	});
	
	function loadGroups() {
		// TODO use healthcheck
		$http.get('/api/groups')
			.then(({data : groups}) => {
				$scope.groups = groups;
			}, err => {
				if (err.status === 401) {
					$state.go('home');
				}
				else {
					alert('Could not fetch groups');
				}
			}) 
	}
	
	loadGroups();
}