$(document).ready(function () {
		globalScope = $("body").scope();


	});

function setToNumber(number) {

	$(document).ready(function () {
		var scope = $("body").scope();
		number= number.split(' ').join('').trim()
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
console.log(JSON.stringify(value))
	callJava(function () {

		JsHandler1.addHistory(JSON.stringify(value))

	})

}
function sendHistory(history){

	console.log(JSON.parse(history).messages)

	
}
