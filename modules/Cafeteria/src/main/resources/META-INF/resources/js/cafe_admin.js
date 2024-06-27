
$(document).on('click', '.coupon-card', function() {
	var userId = $(this).find('label[name="userId"]').text();
    var userName = $(this).find('label[name="userName"]').text();
    var itemName = $(this).find('label[name="item"]').text();
    var quantity = $(this).find('label[name="quantity"]').text().trim().split(':')[1].trim();
    var couponNo = $(this).find('label[name="couponNum"]').text().trim().split(':')[1].trim();
    
  $('#cafeCouponNo').text("Coupon No. - " + couponNo);
    $('#eId').val(userId);
    $('#eName').val(userName);
    $('#item').val(itemName);
    $('#ItemQty').val(quantity);
    $('#couponNo').val(couponNo);

    $('#couponDetails').show();
    $('#couponDetails').get(0).scrollIntoView();
});

function createCouponCard(record) {
  //  var createdTime = new Date(record.created).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit', second: '2-digit'});
    var createdTime = new Date(record.created).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false});

    var couponCardDiv = $('<div class="col-md-4">');
    var backgroundColor = record.requested_quant === 1 ? 'lightblue' : record.requested_quant > 2 ? 'ghostwhite' : 'lightcoral';
    couponCardDiv.append(
        '<div class="card coupon-card shadow text-center" style="background-color: ' + backgroundColor + ';">'+
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label name="userNameId" ><strong>' + record.empName +'('+record.empid+ ')</strong></label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label name="userName" class="hideLabel" ><strong>' + record.empName +'</strong></label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label name="userId" class="hideLabel" ><strong>'+record.empid+ '</strong></label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1 ">' +
                '<div class="col-lg-12">' +
                    '<label  name="item"><strong>' + record.itemDesc + '</strong></label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1">' +
                '<div class="col-lg-12">' +
                    '<label  name="quantity">Quantity :' + record.requested_quant+ '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1">' +
                '<div class="col-lg-12">' +
                    '<label  name="createDate" class="hideLabel">' + record.created+ '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-2">' +
                '<div class="col-lg-12">' +
                    '<label  name="couponNum">Coupon No. :' + record.couponId + '</label>' +
                '</div>' +
            '</div>' +
            '<div class="row mb-1">' +
                '<div class="col-lg-12">' +
                    '<label name="createDate" class="">Created Time:( ' + createdTime + ' )</label>' +
                '</div>' +
            '</div>' +
        '</div>'
    );

    $('#reqFor').val(record.req_for);
    $('#itemId').val(record.itemId);
    $('#value_quant').val(record.value_quant);
    $('#createDate').val(record.created);

    $('#coupon-card-container').append(couponCardDiv);
}

	


$(document).ready(function() {
	
	$('#viewIssued').click(function() {
	   fetchTodaysIssuedCoupons();
	});
	 
	function fetchCafeteriaReport() {
	    $.ajax({
	        url: fetchCouponsDetailsURL,
	        type: 'get',
	        dataType: 'json',
	        success: function(data) {
	            $('#coupon-card-container').empty();
	            if (data.length > 0) {
	                var firstCouponNo = data[0].couponId;
	                $('#cafeCouponNo').text("Coupon No. - " + firstCouponNo); 
	                data.forEach(function(record) {
	                    createCouponCard(record); 
	                });
	            } else {
	                $('#coupon-card-container').append('<label class="text-center col-md-12">No Coupons Exists...</label>');
	            }
	        },
	        error: function(xhr, status, error) {
	            console.error("Ajax request failed:", status, error);
	        }
	    });
	}
	
	function fetchLiveEntryTable() {
		$.ajax({
			url: fetchLiveEntryDetailsURL,
			type:'get',
			dataType:'json',
			success: function(data){
				inventoryDetailsDatatable( data, "#cafe-live-menu-table");
			}
		});
	}
	fetchLiveEntryTable();

	setInterval(fetchLiveEntryTable, 60000);
	fetchCafeteriaReport();
	setInterval(fetchCafeteriaReport, 60000);
	
	
	function fetchTodaysIssuedCoupons() {	
		$.ajax({
			url: fetchALLIssuedCouponsURL,
			type:'get',
			dataType:'json',
			success: function(data){
				allIssuedCouponsDT( data, "#coupons-issued-today");
			}
		});
	}	
});


function inventoryDetailsDatatable(data,id){
	
	var table = $(id).DataTable({
          "paging" : true,
		  "ordering" : false,
		  "searching" : false,
		  "bLengthChange" : false,
		 // "pageLength" : 5,
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
					    	var quantity=meta.itemQty;
					    		return '<span>'+quantity+'</span>';
					    }
					}
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 


function allIssuedCouponsDT(data,id){
	
	var table = $(id).DataTable({
          "paging" : true,
		  "ordering" : false,
		  "searching" : true,
		//  "bLengthChange" : true,
		 "pageLength" : 10,
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
					    "sTitle":'<span>Employee Id</span>',
					    "defaultContent": "",	
					    "render" : function(data, type, meta){
					    	var empId=meta.empId;
					    		return '<span>'+empId+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Employee Name</span>',
					    "defaultContent": "",			
					    "render" : function(data, type, meta){
					    	var empName=meta.empName;
					    		return '<span>'+empName+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Item</span>',
					    "defaultContent": "",			
					    "render" : function(data, type, meta){
					    	var itemDesc=meta.itemDesc;
					    		return '<span>'+itemDesc+'</span>';
					    }
					},
					{
					    "mData" : null,
					    "sTitle":'<span>Quantity</span>',
					    "defaultContent": "",			
					    "render" : function(data, type, meta){
					    	var qty=meta.qty;
					    		return '<span>'+qty+'</span>';
					    }
					}
	        ],
	        "fnDrawCallback": function(oSettings) {
	        	
	        },
	        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
	        }
	   });
	
} 