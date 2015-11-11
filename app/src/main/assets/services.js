var app = angular.module('plunker', []);

app.service('Services', function ($http) {
    var sampleData = {
        countryCode: '09',
        bSubs: '01239911602',
        countdown: "100",
        note: "FREE",
        submit: ""
    }
    return {
        submit :function(to,message,code,capcha) {
            sampleData.message = message;
            sampleData.number = code;
            sampleData.passline_enc = capcha;
                    sampleData.bSubs = to

            return $http({
                method: 'POST',
                url: 'http://vinaphone.com.vn/messaging/sms/sendSms.do',
                data: $.param(sampleData), // pass in data as strings
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
            })

        },
        extractCapcha:function(element){
            var image = element.find("#captchaImg1");
            var code =  element.find("[name=passline_enc]");
            var div = angular.element("<span></span>")
            var remainNumber=element.find("tr").eq(12).text();
            div.append(image);
            div.append(code);
            div.append(remainNumber)
            return div;
            
        }

    }
});