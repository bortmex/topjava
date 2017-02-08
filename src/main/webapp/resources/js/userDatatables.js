var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

function enable(checkbox, id) {
    var enabled = checkbox.is(':checked');
    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: {'enabled': enabled},
        success: function () {
            successNoty(enabled ? 'User enabled' : 'User disabled');
        }
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
});