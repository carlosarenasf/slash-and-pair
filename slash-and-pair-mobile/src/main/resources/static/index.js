$(document).ready(function() {
	
    $('#token').on('change',function(){
		alert("entra en hidden" + $('#token').val());
		var valToketn = $('#token').val();
		$(this).closest('form').submit();
    });
    
});