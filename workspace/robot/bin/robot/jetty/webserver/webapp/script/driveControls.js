
var driveControlURL="drive";

function driveControlForward(){
	$.ajax({
		  url: driveControlURL,
		  data: { direction: "forward"},
		  type: 'GET',
		  success: function(response){
			  console.log("forward response: "+response);
		  },
		});
}

function driveControlLeft(){

	$.ajax({
		  url: driveControlURL,
		  data: { direction: "left"},
		  type: 'GET',
		  success: function(response){
			  console.log("forward response: "+response);
		  },
		});
}

function driveControlRight(){

	$.ajax({
		  url: driveControlURL,
		  data: { direction: "right"},
		  type: 'GET',
		  success: function(response){
			  console.log("forward response: "+response);
		  },
		});
}

function driveControlReverse(){

	$.ajax({
		  url: driveControlURL,
		  data: { direction: "reverse"},
		  type: 'GET',
		  success: function(response){
			  console.log("forward response: "+response);
		  },
		});
}

function driveControlStop(){

	$.ajax({
		  url: driveControlURL,
		  data: { direction: "stop"},
		  type: 'GET',
		  success: function(response){
			  console.log("forward response: "+response);
		  },
		});
}