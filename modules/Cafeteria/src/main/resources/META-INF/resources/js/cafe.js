$(document).ready(function() {
	
	fetchCafeteriaReport();
	fetchDataAndPopulateTable();
	setInterval(fetchDataAndPopulateTable, 60000);

});  

function fetchCafeteriaReport() {
    $.ajax({
        url: fetchCouponsDetailsURL,
        type: 'get',
        dataType: 'json',
        success: function(data) {
            if (data.length > 0) {
                data.forEach(function(record) {
                    createCouponCard(record); 
                });
            }else {
                $('#coupon-card-container').append('<label class="text-center col-md-12">No Coupons Exists...</label>');
            }
        }
    });
}

function createCouponCard(record) {
    var createdTime = new Date(record.created).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false});

    var couponCardDiv = $('<div class="col-md-4">');
    var backgroundColor = record.itemQuantity === 1 ? 'lightblue' : record.itemQuantity > 2 ? 'ghostwhite' : 'lightcoral';
    couponCardDiv.append(
        '<div class="card coupon-card shadow text-center" style="background-color: ' + backgroundColor + ';">' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label for="" class="lable-ht">Item: ' + record.itemName + '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label for="">Quantity: ' + record.qty + '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label for="">Coupon No.: ' + record.couponNo + '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label for="">Created Time: (' + createdTime + ')</label>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</div>');

    $('#coupon-card-container').append(couponCardDiv);
}


function fetchDataAndPopulateTable() {
    $.ajax({
        url: fetchUserCouponsDetailsURL,
        type: 'get',
        dataType: 'json',
        success: function(data) {
                inventoryDetailsDatatable(data, "#coupons-generated-today");
        }
    });
}

function inventoryDetailsDatatable(data,id){
	
	var table = $(id).DataTable({
          "paging" : true,
         // "scrollX":true,
		  "ordering" : false,
		  "searching" : false,
		  "bLengthChange" : false,
		  "pageLength" : 5,
		  "bInfo": false,
		  "destroy" : true,
		  language: {
			  //"search": "Search",
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
					    "sTitle":'<span>Item</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var item=meta.itemName;
					    		return '<span>'+item+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Quantity</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var itemQuantity=meta.qty;
					    		return '<span>'+itemQuantity+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Status</span>',
					    "defaultContent": "",					    
					    "render" : function(data, type, meta){
					    	var status=meta.status;
					    	return '<span>'+status+'</span>';
					    }
					}
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 
