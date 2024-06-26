$(document).ready(function() {
    const currentDate = new Date();
    const currentMonth = currentDate.getMonth() + 1;
    const currentYear = currentDate.getFullYear();

    $('#month').val(currentMonth);
    var yearsSelect = document.getElementById('year');
    for (var year = currentYear; year >= currentYear - 5; year--) {
        var option = document.createElement('option');
        option.value = year;
        option.text = year;
        yearsSelect.appendChild(option);
    } 
    
    function changeMonthFun(event) {
        event.preventDefault();

        var selectedMonth = $('#month').val();
        var selectedYear = $('#year').val();
        fetchCafeteriaReport(selectedMonth, selectedYear);
    }

    $('#sub').on('click', changeMonthFun);
    fetchCafeteriaReport(currentMonth, currentYear);
    
    $('#ModelFilter').on('change', function() {
        var modelFilter = $(this).val();
        console.log("Model Filter:", modelFilter);
        var table = $('#cafeteria-report-table').DataTable();
        if (table) {
            if (modelFilter === 'All') {
                table.column(3).search('').draw(); 
            } else {
                table.column(3).search(modelFilter).draw();
            }
        }
        var totalSum = calculateTotalConsumedAmount(table);
        $('#total-consumed-amount').text('Total Consumed Amount : Rs. ' + totalSum+' /-');
    });
    
});

function fetchCafeteriaReport(selectedMonth, selectedYear) {
    $.ajax({
        url: fetchCafeteriaReportURL,
        type: 'get',
        dataType: 'json',
        data: {
            selectedMonth: selectedMonth,
            selectedYear: selectedYear
        },
        success: function(data) {
            cafeteriaReportDatatable(data, "#cafeteria-report-table");
        },
        error: function(xhr, status, error) {
            console.error("Ajax request failed:", status, error);
        }
    });
}

function cafeteriaReportDatatable(data, id) {
    var table = $(id).DataTable({
        "paging": true,
        "scrollX": true,
        "ordering": false,
        "searching": true,
        "bLengthChange": true,
        "pageLength": 10,
        "lengthMenu": [[10, 20, 50], [10, 20, 50]],
        "destroy": true,
        "bInfo": true,
        "dom": 'lBfrtip',
        "buttons": [
            {
                extend: 'print',
                text: 'Print',
                customize: function(win) {
                    $(win.document.body).prepend('<img src="/documents/d/pll/petronet-logo-png" style="width:80px; margin-bottom:20px; display: block; margin-left: auto; margin-right: auto;">');
                }
            },
            'excel'
        ],
        language: {
            "search": "Search",
            "emptyTable": "No Data Available...",
            "infoFiltered": "",
        },
        "aaData": data,
        "aoColumns": [
            {
                "mData": null,
                "sTitle": '<span>Sl No.</span>',
                "defaultContent": "",
                "render": function(data, type, row, meta) {
                    return '<span>' + (meta.row + 1) + '</span>';
                }
            },
            {
                "mData": null,
                "sTitle": '<span>Date</span>',
                "defaultContent": "",
                "render": function(data, type, meta) {
                    var createDate = meta.date;
                    var dateObject = new Date(createDate);

                    var formattedDate = ('0' + dateObject.getDate()).slice(-2) + "-" +
                        ('0' + (dateObject.getMonth() + 1)).slice(-2) + "-" +
                        dateObject.getFullYear()

                    return '<span>' + formattedDate + '</span>';
                }
            },
            {
                "mData": null,
                "sTitle": '<span>Item Name</span>',
                "defaultContent": "",
                "render": function(data, type, meta) {
                    var item = meta.itemName;
                    return '<span>' + item + '</span>';
                }
            },
            {
                "mData": null,
                "sTitle": '<span>Requested For</span>',
                "defaultContent": "",
                "render": function(data, type, meta) {
                    return '<span>' + (meta.reqFor === 'S' ? 'Self' : (meta.reqFor === 'G' ? 'Guest' : 'Unknown')) + '</span>';
                }

            },
            {
                "mData": null,
                "sTitle": '<span>Ordered Quantity</span>',
                "defaultContent": "",
                "render": function(data, type, meta) {
                    var itemQuantity = meta.qty;
                    return '<span>' + itemQuantity + '</span>';
                }
            },
            {
                "mData": null,
                "sTitle": '<span>Consumed Amount</span>',
                "defaultContent": "",
                "render": function(data, type, meta) {
                    var itemValue = meta.value;
                    return '<span>' + itemValue + '</span>';
                }
            },
        ],
        "fnDrawCallback": function(oSettings) {

        },
        "fnRowCallback": function(row, data, iDisplayIndex, iDisplayIndexFull) {
        }
    });
    
    updateTotalConsumedAmount(table);
} 

function updateTotalConsumedAmount(table) {
    var totalSum = calculateTotalConsumedAmount(table);
    $('#total-consumed-amount').text('Total Consumed Amount : Rs. ' + totalSum+' /-');
}

function calculateTotalConsumedAmount(table) {
    var total = 0;
    table.column(5, { search: 'applied' }).data().each(function(rowData) {
        if (typeof rowData === 'object' && 'value' in rowData) {
            if (!isNaN(rowData.value)) {
                total += parseFloat(rowData.value);
            }
        }
    });
    return total;
}
