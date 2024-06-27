
$(document).ready(function() {
	$.validator.addMethod("checkDuplicateVendor", function(value, element) {
	    var newVendorName = $("#vendorName").val().trim().toLowerCase();
	    for (var i = 0; i < cafeVendorMasterList.length; i++) {
	        var vendor = cafeVendorMasterList[i];
	        if (vendor.vendorName.trim().toLowerCase() === newVendorName) {
	            return false;
	        }
	    }
	    return true;
	});

	$.validator.addMethod("checkDuplicateVendorId", function(value, element) {
	    var newVendorSapId = $("#vendorSapId").val().trim();
	    for (var i = 0; i < cafeVendorMasterList.length; i++) {
	        var vendor = cafeVendorMasterList[i];
	        if ( vendor.vendorSapId.trim() === newVendorSapId) {
	            return false;
	        }
	    }
	    return true;
	});

/*	$.validator.addMethod("charOnly", function (value, element) {
		return this.optional(element) || /^[a-zA-Z][a-zA-Z\s]*$/.test(value);
	});*/
	
$('#vendorDetails').validate({
    rules: {
    	vendorName:{
		    required: true,
		    charOnly: true,
		    maxlength:45,
		    checkDuplicateVendor:true
			},
		vendorAddr:{
		    required: true,
		    maxlength:250
		},
		vendorSapId:{
			required: true,
			 maxlength:25,
			 checkDuplicateVendorId:true
		},
		
    },
    messages: {
    	vendorName: 
    		{
    		required:'Please Enter A Name',
    		charOnly: 'Please Enter Alpha Characters Only',
    		maxlength:'Please Enter Characters Less Than 45',
    		checkDuplicateVendor:'Vendor Already Exists'
    		},
    	vendorAddr:{
    		required:'Please Enter The Address',
    		maxlength:'Please Enter Characters Less Than 250'
    	},
    	vendorSapId:{
    		required:'Please Enter The Vendor Sap Id',
    		maxlength:'Please Enter Characters Less Than 25',
    		checkDuplicateVendorId:'Vendor Sap Id Already Exists',
    	}
    		
    },
    errorPlacement:function( error, element ){
    	error.appendTo(element.parent().after());
	  },
    submitHandler: function(form) {
        form.submit();
        navigateToFormStep(2);
    }
});

$.ajax({
	url: fetchVendorMasterDetailsURL,
	type:'get',
	dataType:'json',
	success: function(data){
		vendorMasterDetailsDatatable(data, "#cafe-vendor-master-table");
	}
});
	
});

function vendorMasterDetailsDatatable(data,id){
	
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
					    	var vendorName=meta.vendorName;
					    		return '<span>'+vendorName+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Vendor Sap ID</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var vendorSapId=meta.vendorSapId;
					    		return '<span>'+vendorSapId+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Vendor Address</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var vendorAddress=meta.vendorAddress;
					    		return '<span>'+vendorAddress+'</span>';
					    }
					},
					
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 

