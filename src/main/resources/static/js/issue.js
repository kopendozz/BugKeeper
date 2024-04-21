$(document).ready(function () {
    var url = window.location.pathname;
    var res = url.split("/");
    var id = res[2];
    if (id != null) {
        $.getJSON("/issues/" + id, function (issue) {
            $('#priority').val(issue.priority.id);
            $('#status').val(issue.status.id);
            $('#responsible').val(issue.responsible.username);
            $('#name').val(issue.name);
            $('#description').val(issue.description)
        });
    }

    if ($.validator) {
        $("#issueForm").validate({
            rules: {
                priority: {
                    required: true
                },
                responsible: {
                    required: true
                },
                status: {
                    required: true
                },
                dueDate: {
                    required: true
                },
                name: {
                    required: true,
                    minlength: 20,
                    maxlength: 140
                },
                description: {
                    required: true,
                    minlength: 20,
                    maxlength: 5000
                }
            },
            messages: {
                priority: {
                    required: 'Please select priority.'
                },
                responsible: {
                    required: 'Please assign issue.'
                },
                status: {
                    required: 'Please select status.'
                },
                name: {
                    required: 'Please specify name.',
                    minlength: 'Name must be at least 20 characters.',
                    maxlength: 'Name must be less than 140 characters.'
                },
                description: {
                    required: 'Please specify description.',
                    minlength: 'Description must be at least 20 characters.',
                    maxlength: 'Description must be less than 5000 characters.'
                }
            },
            errorPlacement: function (label, element) {
                label.insertAfter(element);
            },
            wrapper: 'span',
            submitHandler: createOrUpdateIssue
        });

    }
});

function deleteIssue() {
    $.ajax({
        url: '/issues/' + $('#deleteIssueButton').attr('issueId'),
        type: 'DELETE',
        success: function () {
            window.location.replace('/issues')
        },
        error: function (response, msg) {
            $('#myModal').modal()
        }
    });
}

function fillValues(id) {
    if (id != null) {
        $.getJSON("/issues/" + id, function (issue) {
            $('#priority').val(issue.priority.id);
            $('#status').val(issue.status.id);
            $('#responsible').val(issue.responsible.username);
            $('#name').val(issue.name);
            $('#description').val(issue.description)
        });
    }
}

function deleteAttachment(id) {
    $.ajax({
        url: '/attachment/' + id,
        type: 'DELETE',
        success: function () {
            window.location.reload()
        }
    });
}

function createOrUpdateIssue() {
    var issue = $('#issueForm').serializeObject();
    issue.project = {
        id: issue.project
    };
    issue.priority = {
        id: issue.priority
    };
    issue.status = {
        id: issue.status
    };
    issue.reporter = {
        username: issue.reporter
    };
    issue.responsible = {
        username: issue.responsible
    };
    console.log(issue);
    $.ajax({
        url: '/issues/save',
        type: 'POST',
        data: JSON.stringify(issue),
        contentType: 'application/json',
        success: function () {
            window.location.replace('/issues')
        }
    });
}

function enableUpload() {
    var uploadInput = document.getElementById('uploadInput');

    if (uploadInput.value != '') {
        //enables the submit button to upload the file
        document.getElementById('submitButton').disabled = false;

        //makes the file picker element invisible
        document.getElementById('uploadDiv').style.display = 'none';

        //shows the element which show the filename
        document.getElementById('selectedFileDiv').style.display = '';

        //removes span content and add the file name
        var filenameSpan = document.getElementById('filename');
        while (filenameSpan.firstChild) {
            filenameSpan.removeChild(filenameSpan.firstChild);
        }
        //adds the filename to the span
        filenameSpan.appendChild(document.createTextNode(uploadInput.value));
    }
}

function selectNewFile() {
    //shows the file picker element
    document.getElementById('uploadDiv').style.display = '';

    //makes the element which show the filename invisible
    document.getElementById('selectedFileDiv').style.display = 'none';

    //disable the submit button to upload the file
    document.getElementById('submitButton').disabled = true;

    //removes the file name in the input
    document.getElementById('uploadInput').value = '';
}
