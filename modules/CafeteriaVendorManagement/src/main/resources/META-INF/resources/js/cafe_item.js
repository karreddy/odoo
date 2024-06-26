$(document).ready(function() {
    
$('#cafeItemDetails').validate({
    rules: {
    	itemDesc:{
		    required: true,
		    charOnly: true,
		    maxlength:45,
		},
		itemUnits:{
		    required: true,
		},
    },
    messages: {
    	itemDesc:{
		    	required:'Please Enter The Item Description',
		    	charOnly: 'Please Enter Only Alpha Characters',
		    	 maxlength:'Please Enter Characters Less Than 45'
    		},
    	itemUnits:'Please Select Any Option'
    },
    errorPlacement:function( error, element ){
    	error.appendTo(element.parent().after());
	  },
    submitHandler: function(form) {
        form.submit();
    }
});
    
$.ajax({
	url:fetchItemMasterDetailsURL,
	type:'get',
	dataType:'json',
	success: function(data){
		itemMasterDetailsDatatable(data, "#cafe-item-master-table");
	}
});

});

function itemMasterDetailsDatatable(data,id){
	
	var table = $(id).DataTable({
		"scrollX":true,
        "paging" : true,
		  "ordering" :  false,
		  "searching" : true,
		  "bLengthChange" : false,
		  "destroy" : true,
		  "bInfo": true,
		  language: {
			  "search": "Search",
			  "emptyTable": "No Data Available...",
			  "infoFiltered": "",
		    },
		    "aaData": data,
		  	"aoColumns" :
		      [
					{
					    "mData" : null,
					    "sTitle": '<span>Sl No.</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, row, meta){
					    		return '<span>'+(meta.row + 1) +'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Item Name</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemDesc=meta.itemDesc;
					    		return '<span>'+itemDesc+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Item Unit</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemUnits=meta.itemUnits;
					    		return '<span>'+itemUnits+'</span>';
					    }
					},
					
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 