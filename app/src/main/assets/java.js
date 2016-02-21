function setToNumber(number) {

	$(document).ready(function () {
		var scope = $("body").scope();
		scope.to = number
			scope.$evalAsync()

	});

}
function callJava(callback) {
	try {
		callback();
	} catch (e) {
		console.log("Dont run on native")
	}

}
function onLoaded() {
	callJava(function () {
		JsHandler1.onLoaded();
	})

}
function toast(value) {
	callJava(function () {
		JsHandler1.toast(value)
	})

}
function addHistoryFn(value) {
	callJava(function () {
		JsHandler1.addHistory(JSON.stringify(value))

	})

}
