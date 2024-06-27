
function getSelectedItemQty() {
    var selectedItem = $("#item").val();
    var selectedStock = inObject.find(function(stock) {
        return stock.itemId == selectedItem;
    });
    if (selectedStock) {
        var itemQty = selectedStock.qty;
        $("#invDisplay").val(itemQty);
    }
}

$(document).ready(function() {
	 $("#item").change(function() {
    	getSelectedItemQty();
    });
    
    $('#consumptionDetails').validate({
	    rules: {
			item:{
			    required: true,
			},
			Qty:{
			    required: true,
			    number: true,
			    maxlength:6,
			    validateQuantity:true,
			    digit:true
			    
			},
			reason:{
			    required: true,
			    maxlength:75,
			}
	    },
	    messages: {
	    	item:{
	    		required: "Please Select A Option",
	    	  },
	    	  Qty:{
	    		required: "Please Enter The Quantity",
	    		number: "Accepts Only Numeric Values",
	    		 maxlength:"Characters Not More Than 6",
	    		 digits: "Accepts Only Whole Number",
	    		 validateQuantity:"Entered Quantity Exceeds The Available Quantity"
	    	  },
	    	  reason:{
	    		required: "Please Enter The Reason",
	    		 maxlength:"Characters Not More Than 75",
	    	  },
	    },
	    errorPlacement:function( error, element ){
	    	error.appendTo(element.parent().after());
		  },
	    submitHandler: function(form) {
	        form.submit();
	    }
	});
    
    $.validator.addMethod("validateQuantity", function(value, element) {
		var curStockQty = parseFloat($('#invDisplay').val());
		var enteredQty = parseFloat(value);
   		return enteredQty <= curStockQty;
		}); 
    
    $.ajax({
    	url: fetchInvConsDetailsURL,
    	type:'get',
    	dataType:'json',
    	success: function(data){
    		itemMasterDetailsDatatable(data, "#inv-consumtion-table");
    	}
    });
    
});  
  


function itemMasterDetailsDatatable(data,id){
	
	var table = $(id).DataTable({
		"scrollX":true,
		"paging" : true,
		  "ordering" :  false,
		   "searching" : true,
		  "bLengthChange" : true,
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
					    "sTitle":'<span>Date</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var date=meta.date;
					    	var dateObject = new Date(date);
  					        
  					    	var formattedDate = ('0' + dateObject.getDate()).slice(-2) + "-" +
  				            ('0' + (dateObject.getMonth() + 1)).slice(-2) + "-" +
  				            dateObject.getFullYear()
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
					    "sTitle":'<span>Reason</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var reason=meta.reason;
					    		return '<span>'+reason+'</span>';
					    }
					},
					
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 
