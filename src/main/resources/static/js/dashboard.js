$(document).ready(function(){
    $('#issuesList').dataTable( {

        initComplete: function () {
            var api = this.api();

            api.columns().indexes().flatten().each(function (i) {
                if (i != 0 && i != 2) {
                    var column = api.column(i);
                    var select = $('<select><option value=""></option></select>')
                        .appendTo($(column.footer()).empty())
                        .on('change', function () {
                            var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );

                            column
                                .search(val ? '^' + val + '$' : '', true, false)
                                .draw();
                        });

                    column.data().unique().sort().each(function (d, j) {
                        select.append('<option value="' + d + '">' + d + '</option>')
                    });
                }
            });
        },
        columnDefs: [
            { type: 'enum', targets: 1 }
        ]
    } );
});
