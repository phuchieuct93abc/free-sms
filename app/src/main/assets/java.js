function setToNumber(number) {

    $(document).ready(function () {
        var scope = $("body").scope();
        scope.to = number
        scope.$evalAsync()

    });

}
function onLoaded() {
    //JsHandler1.onLoaded();

}
function toast(value) {
    //JsHandler1.toast(value)
}
