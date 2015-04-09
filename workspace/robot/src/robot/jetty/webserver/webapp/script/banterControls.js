var banterControlURL="banter";

function banter(){
	console.log(banterControlURL);
	console.log($("#banterText").val());
	$.ajax({
		  url: banterControlURL,
		  data: { text: $("#banterText").val()},
		  type: 'GET',
		  success: function(response){
			  console.log("banter response: "+response);
		  },
		});
}
