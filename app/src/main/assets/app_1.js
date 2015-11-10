
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

    $scope.run = function () {
        getTokenLogin().then(function (value) {
            toast('success2')
            $http({
                method: 'POST',
                url: path1,
                data: $.param({
                    'username': '841239911602',
                    'password': 'vstuqy',
                    '_eventId': 'submit',
                    'lt': value
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).success(function (value) {
                toast('success3')

                $scope.name = value;
                getSendSMSForm().then(function (value) {
                                        

                    $("#form").append(Services.extractCapcha(value));
                    onLoaded();
                })
            }).error(function (erorr) {
                toast(erorr)

            })
        }, function () {
            toast('erorr2')

        });

    }
    $scope.submit = function () {

        Services.submit($scope.to,$scope.message, $scope.code, $('[name=passline_enc]').val())

    }
});