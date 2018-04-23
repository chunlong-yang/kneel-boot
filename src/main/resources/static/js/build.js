function ready(){ 
	csvChange();
};



function projectChange(){ 
	var csv = document.getElementById("csv"); 
	removeOptions(csv); 
}

function resChange(){
	
	var project = document.getElementById("project");
	if(project = null){
		alert("please choose project first");
		return;
	}  
	var csv = document.getElementById("csv"); 
	removeOptions(csv); 
	
	csvChange();
}

function csvChange(){
	var project = document.getElementById("project");
	var res = document.getElementById("res"); 
	if(project == null || res == null){
		alert("please choose project/res first");
		return;
	}  
	
	var csv = document.getElementById("csv"); 
	
	var csvurl = csv.getAttribute("src");
	 
	//发异步消息去获取数据.
	$.ajax({
		  type: 'GET', 
		  url: csvurl,
		  data: {
			project: project.value,
			res: res.value
	      },
	      dataType: 'text',
	      success: function(datastr) {  
	    	  var start= 0; 
	    	  var data = $.parseJSON(datastr);
	    	  $.each(data, function(index, element) { 
	    		  csv.options[start] = new Option(element.txt, element.txt);
				  start++; 
	          });
	      }  
    });
}

function runGenerator(){
	var project = document.getElementById("project");
	var res = document.getElementById("res"); 
	var csv = document.getElementById("csv"); 
	if(project == null || res == null|| csv == null){
		alert("please choose project/res/csv first");
		return;
	}  
	
	var form = document.getElementById("formid"); 
	var runurl = form.getAttribute("action");
	 
	//发异步消息去获取数据.
	$.ajax({
		  type: 'GET', 
		  url: runurl,
		  data: {
			project: project.value,
			res: res.value,
			csv: csv.value
	      },
	      dataType: 'text',
	      success: function(datastr) {
	    	 $("#sqls").html("<pre>"+datastr+"</pre>"); 
	      }  
    });
	
}

function removeOptions(selectObj){
	if (typeof selectObj != 'object'){
		selectObj = document.getElementById(selectObj);
	} 
	var len = selectObj.options.length;
 	for(var i=0;i < len;i++){ 
		selectObj.options[0] = null;
	}
}