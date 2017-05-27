function doCastImg(){
	$("#codeQR").attr('src',$("#base64token").html());
}

$(document).ready(function() {
    doCastImg();
});
