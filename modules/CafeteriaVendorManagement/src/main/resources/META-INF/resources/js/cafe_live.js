$(document).ready(function() {
	
var selInventory=$('#selInv').val();
function fetchLiveInventoryDetails(selInventory) {
	$.ajax({
		url: fetchLiveEntryDetailsURL,
		type:'get',
		dataType:'json',
		 data: {
	         selInventory: selInventory
	     },
		success: function(data){
			inventoryDetailsDatatable( data.Cafeteria, "#cafe-entry-table");
			 if(data.isSelected){
	         	if(data.inventory == 'Pantry' ){
	         		inventoryDetailsDatatable(data.Pantry, "#pantry-entry-table");
	         	}
	         	 else if(data.inventory == 'MD Office' ){
	         		inventoryDetailsDatatable(data.MD, "#pantry-entry-table");
	         	}  
	         }else{
	             if (data.MDorPantry.length > 0) {
	                 inventoryDetailsDatatable(data.MDorPantry, "#pantry-entry-table");
	             } else {
	                 $("#mdOrPantryDiv").hide(); 
	             }
	         }
		}
	});
}	
	fetchLiveInventoryDetails($('#selInv').val());
	
	$('#selInv').on('change', function() {
	    var selInventory = $(this).val();
	    fetchLiveInventoryDetails(selInventory);
	});

});

function inventoryDetailsDatatable(data,id){
	
	var table = $(id).DataTable({
        "paging" : true,
		  "ordering" : false,
		  "scrollX":true,
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
					    "sTitle":'<span>For</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var inventory=meta.inventory;
					    		return '<span>'+inventory+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Date</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var date=meta.date;
					    	var dateObject = new Date(date);
					        
					    	var formattedDate = ('0' + dateObject.getDate()).slice(-2) + "-" +
				            ('0' + (dateObject.getMonth() + 1)).slice(-2) + "-" +
				            dateObject.getFullYear()
				            /* + " " +
				            ('0' + dateObject.getHours()).slice(-2) + ":" +
				            ('0' + dateObject.getMinutes()).slice(-2) + ":" +
				            ('0' + dateObject.getSeconds()).slice(-2);
						       */
						    return '<span>' + formattedDate + '</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Item</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var item=meta.item;
					    		return '<span>'+item+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Quantity</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var quantity=meta.quantity;
					    		return '<span>'+quantity+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Cost Per Unit</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemUnitPrice=meta.itemUnitPrice;
					    		return '<span>'+itemUnitPrice+'</span>';
					    }
					},
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 