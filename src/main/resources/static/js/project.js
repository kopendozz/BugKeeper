$(document).ready(function () {
    $('#users').tokenize2();
    if ($.validator) {
        $("#projectForm").validate({
            rules: {
                name: {
                    required: true,
                    minlength: 3,
                    maxlength: 45
                }
            },
            messages: {
                name: {
                    required: "Name is required.",
                    minlength: "Name must be at least 3 characters.",
                    maxlength: "Name must be less than 45 characters."
                }
            },
            errorPlacement: function (label, element) {
                label.insertAfter(element);
            },
            wrapper: 'span',
            submitHandler: createOrUpdateProject
        });
    }
});

function deleteProject(id) {
    $.ajax({
        url: '/admin/projects/' + id,
        type: 'DELETE',
        success: function () {
            window.location.replace('/admin/projects')
        }
    });
}

function addProject() {
    $('#projectDialog').find('form')[0].reset();
    $('#users').tokenize2().clear();
    $('#projectDialog').modal();
}

function editProject(id) {
    var projectDialogForm = $('#projectDialog').find('form');
    $('.modal-title').text('Update Project');
    projectDialogForm[0].reset();
    $('#users').tokenize2().clear();
    $.getJSON("/admin/projects/" + id, function (project) {
        projectDialogForm.find('input[name=id]').val(project.id);
        projectDialogForm.find('input[name=name]').val(project.name);
        project.users.forEach(function (entry) {
            $('#users').tokenize2().tokenAdd(entry.username, entry.firstName + ' ' + entry.lastName);
        });
    });
    $('#projectDialog').modal();
}

function createOrUpdateProject() {
    var project = $('#projectDialog').find('form').serializeObject();
    if (typeof project.users == 'string' || project.users instanceof String) {
        project.users = [{
            username : project.users
        }]
    } else {
        var tempusers = [];
        for (var user in project.users) {
            tempusers.push({ username: project.users[user] })
        }
        project.users = tempusers;
    }
    $.ajax({
        url: '/admin/projects/save',
        type: 'POST',
        data: JSON.stringify(project),
        contentType: 'application/json',
        success: function () {
            window.location.replace('/admin/projects')
        }
    });
}
