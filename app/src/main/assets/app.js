
app.controller('MainCtrl', function (Services, $scope, $http, $q) {
	$http.get('http://vinaphone.com.vn/logout.do').success(function () {
		$scope.run();

	}).error(function () {
		toast('success')

	})
	var path1 = 'https://vinaphone.com.vn/auth/login?service=http%3A%2F%2Fvinaphone.com.vn%3A80%2Flogin.jsp%3Flang%3Dvi';
	var getTokenLogin = function () {
		var deferred = $q.defer();
		var path = 'https://vinaphone.com.vn/auth/login'
			$http.get(path).success(function (value, value2, value3, value4) {
				var startIndex = value.indexOf("<form");
				var endIndex = value.indexOf("</form>");

				var formContent = value.substring(startIndex, endIndex + 8);
				var x = angular.element(angular.element(formContent)[0]);
				deferred.resolve(x.find('[name=lt]').val())

			})
			return deferred.promise;
	}
	var getSendSMSForm = function () {
		var deferred = $q.defer();
		var path = 'http://vinaphone.com.vn/messaging/sms/sms.do'
			$http.get(path).success(function (value) {
				var startIndex = value.lastIndexOf("<form");
				var endIndex = value.lastIndexOf("</form>");
				var formContent = value.substring(startIndex, endIndex + 8);
				var x = angular.element(angular.element(formContent)[0]);
				x.attr('action', 'http://vinaphone.com.vn/messaging/sms/sendSms.do')
				deferred.resolve(x)
			})
			return deferred.promise;
	}
	function login(token) {
		return $http({
			method : 'POST',
			url : path1,
			data : $.param({
				'username' : '841239911602',
				'password' : 'vstuqy',
				'_eventId' : 'submit',
				'lt' : token
			}),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		})
	}
	$scope.run = function () {

		getTokenLogin().then(function (token) {
			toast('Get token successful')
			login(token).success(function (value) {
				toast('Login successful')
				getSendSMSForm().then(function (value) {

					$("#form").append(Services.extractCapcha(value));
					$scope.showSubmit = true
						onLoaded();
				})
			}).error(function (erorr) {
				toast(erorr)

			})
		}, function () {
			toast('erorr2')

		});

	}
	function getHistory() {
		Services.getHistory()

	}
	$scope.submit = function () {
		Services.submit($scope.to, $scope.message, $scope.code, $('[name=passline_enc]').val()).success(function (success) {
			if (success.indexOf('http://vinaphone.com.vn/vnp_files/image/error.gif') > 0) {
				alert("Gui khong thanh cong");

				Services.addHistory($scope.message, $scope.to, false)
			} else {

				alert('gui thanh cong');

				Services.addHistory($scope.message, $scope.to, true)

			}

		})

	}
});
