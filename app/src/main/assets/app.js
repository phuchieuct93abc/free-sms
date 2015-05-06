var app = angular.module('plunker', []);

app.controller('MainCtrl', function($scope,$http,$q) {
 $http.get('http://vinaphone.com.vn/logout.do').success(function(){
// $http.get('https://vinaphone.com.vn/auth/logout?service=http://vinaphone.com.vn').success(function(){
 $scope.run();
// })
 })

  var path1 = 'https://vinaphone.com.vn/auth/login?service=http%3A%2F%2Fvinaphone.com.vn%3A80%2Flogin.jsp%3Flang%3Dvi';
  
  
   var getTokenLogin= function(){
      var deferred = $q.defer();
    var path = 'https://vinaphone.com.vn/auth/login'
    $http.get(path).success(function(value,value2,value3,value4){
      var startIndex = value.indexOf("<form");
      var endIndex = value.indexOf("</form>");

      var formContent = value.substring(startIndex, endIndex+8);
      var x = angular.element(angular.element(formContent)[0]);
      deferred.resolve(x.find('[name=lt]').val())

    })
      return deferred.promise;

    
  }
var getSendSMSForm = function(){
 var deferred = $q.defer();
    var path = 'http://vinaphone.com.vn/messaging/sms/sms.do'
    $http.get(path).success(function(value){
      var startIndex = value.lastIndexOf("<form");
      var endIndex = value.lastIndexOf("</form>");
  	var formContent = value.substring(startIndex, endIndex+8);
      var x = angular.element(angular.element(formContent)[0]);
x.attr('action','http://vinaphone.com.vn/messaging/sms/sendSms.do')
      deferred.resolve(x)

    })
      return deferred.promise;
}



  $scope.run = function(){
    getTokenLogin().then(function(value){

      var config =  {
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    }


    $http({
    method: 'POST',
    url: path1,
    data: $.param({'username':'841239911602',
      'password':'vstuqy',
        
        '_eventId':'submit',
        'lt':value
      }),
    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
}).success(function(value){
      $scope.name = value;
	getSendSMSForm().then(function(value){
		$("#form").append(value);
        onLoaded();
	})

    
    
  })
 
    
    
    
    
    });
      
    
    
  }


});