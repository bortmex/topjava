var ajaxUrl = 'ajax/meals/';
var datatableApi;

function filterClear() {
    $('#startDate').val(null);
    $('#startTime').val(null);
    $('#endDate').val(null);
    $('#endTime').val(null);
    updateTable();
}


function filterTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});
