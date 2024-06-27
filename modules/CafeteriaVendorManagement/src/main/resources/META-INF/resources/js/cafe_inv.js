
$("#calendar-icon").click(function() {
    $("#date").focus(); 
});

function getSelInvItemQty() {
    var selectedItem = $("#item").val();
    var selectedStock = itemIdAndNameArray.find(function(stock) {
        return stock.itemId == selectedItem;
    });
    

    if (selectedStock) {
        var itemQty = selectedStock.qty;
        $("#currentQty1").val(itemQty);
        var unitPrice = selectedStock.unitPrice;
        $("#currentQty2").val(unitPrice);
    }
}

$(document).ready(function() {
    
	$("#item").change(function() {
    	getSelInvItemQty();
	});
	$.validator.addMethod("validateQuantity", function(value, element) {
		var curStockQty = parseFloat($('#currentQty1').val());
		var enteredQty = parseFloat(value);
			return enteredQty <= curStockQty;
	});
	$('#systemEntryDetails').validate({
	    rules: {
	    	inventory:{
			    required: true,
			},
			item:{
			    required: true,
			},
			Qty:{
			    required: true,
			    number: true,
			    digits:true,
			    maxlength:6,
			    validateQuantity:true
			},
			unitPrice:{
			    required: true,
			    number: true,
			    maxlength:10,
			},
	    },
	    messages: {
	    	inventory: {
	    		required: "Please Select A Option",
	    	
	    	  },
	    	item:{
	    		required: "Please Select A Option",
	    	  },
	    	Qty:{
	    		required: "Please Enter The Quantity",
	    		number: "Accepts Only Numeric Values",
	    		digits: "Accepts Only Whole Number", 
	    		 maxlength:"Characters Not More Than 6",
	    		 validateQuantity:"Entered Quantity Exceeds The Available Quantity"
	    	  },
	    	unitPrice:{
	    		required: "Please Enter The Unit Price",
	    		number: "Accepts Only Numeric Values",
	    		 maxlength:"Characters Not More Than 10",
	    	  },
	    },
	    errorPlacement:function( error, element ){
	    	error.appendTo(element.parent().after());
		  },
	    submitHandler: function(form) {
	        form.submit();
	    }
	});
	
	
	    $.ajax({
	        url: fetchCanteenURL,
	        type: 'get',
	        dataType: 'json',
	        success: function(data) {
	            inventoryDetailsDatatable(data, "#cafe-entry-table");
	        }
	    });

	
	var selInventory=$('#selInv').val();
	function fetchInventoryDetails(selInventory) {
	    $.ajax({
	        url: fetchPantryURL,
	        type: 'get',
	        dataType: 'json',
	        data: {
	            selInventory: selInventory
	        },
	        success: function(data) {
	        	mdpDatatable(data, "#pantry-entry-table");
	        }
	    });
	}
		fetchInventoryDetails($('#selInv').val());

	$('#selInv').on('change', function() {
	    var selInventory = $(this).val();
	    fetchInventoryDetails(selInventory);
	});
	
});  

function inventoryDetailsDatatable(data,id){

	var table = $(id).DataTable({
	"scrollX":true,
        "paging" : true,
		  "ordering" : false,
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
					    	var item=meta.itemDesc;
					    		return '<span>'+item+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Quantity</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var quantity=meta.qty;
					    		return '<span>'+quantity+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Cost Per Unit</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemUnitPrice=meta.unitPrice;
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

function mdpDatatable(data,id){

	var table = $(id).DataTable({
	"scrollX":true,
        "paging" : true,
		  "ordering" : false,
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
					    	var item=meta.itemDesc;
					    		return '<span>'+item+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Quantity</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var quantity=meta.qty;
					    		return '<span>'+quantity+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Cost Per Unit</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemUnitPrice=meta.unitPrice;
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