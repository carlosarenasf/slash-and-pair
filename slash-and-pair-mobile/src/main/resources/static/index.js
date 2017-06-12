$(document).ready(function() {
	
    $('#token').on('change',function(){
		var valToketn = $('#token').val();
		$(this).closest('form').submit();
    });
    
});