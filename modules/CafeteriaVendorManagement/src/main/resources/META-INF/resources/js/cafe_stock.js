



function calculateTotalAmount() {
    var qty = parseFloat($("#Qty").val()) || 0;
    var unitPrice = parseFloat($("#unitPrice").val()) || 0;
    var totalAmount = (qty * unitPrice).toFixed(2);
    $("#totalAmt").val(totalAmount);
}

function getSelectedItemQty() {
    var selectedItem = $("#item").val();
    var selectedStock = stockQuantityList.find(function(stock) {
        return stock.item_id== selectedItem;
    });
    if (selectedStock) {
        var itemQty = selectedStock.qty;
        $("#currentQty").val(itemQty);
    } else {
    	$("#currentQty").val("0");
    }
}

 function generateInvoiceNumber() {
    const randomNumber = Math.floor(Math.random() * 9000) + 1000;
    const timestamp = Date.now();
    const invoiceNumber = timestamp.toString() + randomNumber.toString();
    $("#invcNo").val(invoiceNumber);
}
 
$(document).ready(function() {
   
	$("#item").change(function() {
    	getSelectedItemQty();
    });
	//generateInvoiceNumber();
	$("#Qty, #unitPrice").on("input", function() {
	    calculateTotalAmount();
	});
    $('#stockDetails').validate({
	    rules: {
	    	vendor:{
			    required: true, 
			},
			item:{
			    required: true,
			},
			invcNo:{
				required: true,
				maxlength:25
			},
			Qty:{
			    required: true,
			    number: true,
			    maxlength:6,
			    digits: true,
			//    validateQuantity:true
			},
			unitPrice:{
			    required: true,
			    number: true,
			    maxlength:10,
			},
			invcDate:{
			    required: true,
			},
			
	    },
	    messages: {
	    	vendor: {
	    		 required: "Please Select A Option",
	    	
	    	  },
	    	item:{
	    		required: "Please Select A Option",
	    		number: "Accepts Only Numeric Values"
	    	  },
	    	Qty:{
	    		required: "Please Enter The Quantity",
	    		number: "Accepts Only Numeric Values",
	    		 maxlength:"Characters Not More Than 6",
	    		 digits: "Accepts Only Whole Number",
	    		// validateQuantity:"Entered Quantity Exceeds The Available Quantity"
	    	  },
	    	unitPrice:{
	    		required: "Please Enter The Unit Price",
	    		number: "Accepts Only Numeric Values",
	    		 maxlength:"Characters Not More Than 10",
	    	  },
	    	  invcNo:{
	    		  	required: "Please Enter The Invoice No.",
				    maxlength:"Characters Not More Than 25"
				},
	    	  invcDate:{
				    required: "Please Select The Invoice Date",
				},
	    },
	    errorPlacement:function( error, element ){
	    	error.appendTo(element.parent().after());
		  },
	    submitHandler: function(form) {
	        form.submit();
	    }
	});
    
    $('#invcDate').on('change', function() {
        if ($(this).valid()) {
            $(this).next('label.error').remove();
        }
    });
    /*    $.validator.addMethod("alphaNumeric", function(value, element) {
    	 		return this.optional(element) || /^[A-Z0-9][A-Z0-9-]*$/.test(value);
    		});
    		
	     $.validator.addMethod("validateQuantity", function(value, element) {
				var curStockQty = parseFloat($('#currentQty').val());
				console.log("curStockQty >>>>"+curStockQty);
				var enteredQty = parseFloat(value);
		   		 console.log("enteredQty >>>>"+enteredQty);
		   		return enteredQty <= curStockQty;
			});  */
    
    $.ajax({
    	url: fetchstockDetailsURL,
    	type:'get',
    	dataType:'json',
    	success: function(data){
    		stockDetailsDatatable(data, "#cafe-stock-table");
    	}
    });
    
});  
 
function stockDetailsDatatable(data,id){
	var table = $(id).DataTable({
        "paging" : true,
        "ordering" :  false,
		  "searching" : true,
		  "scrollX":true,
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
				    "sTitle":'<span>Vendor Name</span>',
				    "defaultContent": "",					    
				    "render" : function(data, type, meta){
				    	var vendor=meta.vendor;
				    		return '<span>'+vendor+'</span>';
				    }
				},
				{
				    "mData" : null,
				    "sTitle": '<span>Item</span>',
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
				{
				    "mData" : null,
				    "sTitle":'<span>Amount</span>',
				    "defaultContent": "",					    
				    "render" : function(data, type, meta){
				    	var totalAmount=meta.amount;
				    		return '<span>'+totalAmount+'</span>';
				    }
				},
				{
				    "mData" : null,
				    "sTitle":'<span>Invoice Date</span>',
				    "defaultContent": "",					    
				    "render" : function(data, type, meta){
				    	var invoiceDate=meta.invDate;
				    	var dateObject = new Date(invoiceDate);
				        
				    	var formattedDate = ('0' + dateObject.getDate()).slice(-2) + "-" +
			            ('0' + (dateObject.getMonth() + 1)).slice(-2) + "-" +
			            dateObject.getFullYear()
					    return '<span>' + formattedDate + '</span>';
				    }
				},
				{
				    "mData" : null,
				    "sTitle":'<span>Invoice No.</span>',
				    "defaultContent": "",					    
				    "render" : function(data, type, meta){
				    	var invNo=meta.invNo;
				    		return '<span>'+invNo+'</span>';
				    }
				},
					
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 