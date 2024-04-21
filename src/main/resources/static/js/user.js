$(document).ready(function () {
    if ($.validator) {
        $("#userForm").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 3,
                    maxlength: 45
                },
                role: {
                    required: true
                },
                firstName: {
                    required: true,
                    minlength: 3,
                    maxlength: 45
                },
                lastName: {
                    required: true,
                    minlength: 3,
                    maxlength: 45
                },
                password: {
                    required: true,
                    minlength: 3,
                    maxlength: 45
                }
            },
            messages: {
                username: {
                    required: "Username is required.",
                    minlength: "Username must be at least 3 characters.",
                    maxlength: "Username must be less than 45 characters."
                },
                role: {
                    required: "Role is required."
                },
                firstName: {
                    required: "Fist Name is required.",
                    minlength: "First Name must be at least 3 characters.",
                    maxlength: "First Name must be less than 45 characters."
                },
                lastName: {
                    required: "Last Name is required.",
                    minlength: "Last Name must be at least 3 characters.",
                    maxlength: "Last Name must be less than 45 characters."
                },
                password: {
                    required: "Password is required.",
                    minlength: "Password must be at least 3 characters.",
                    maxlength: "Password must be less than 45 characters."
                }
            },
            errorPlacement: function (label, element) {
                label.insertAfter(element);
            },
            wrapper: 'span',
            submitHandler: createOrUpdateUser
        });
    }
});

function deactivateUser(username) {
    $.ajax({
        url: '/admin/users/' + username + '/deactivate',
        type: 'DELETE',
        success: function (result) {
            window.location.replace('/admin/users')
        }
    });
}

function addUser() {
    $('#userDialog').find('form')[0].reset();
    $('.modal-title').text('Create User');
    $('#username').prop('disabled', false);
    $('#userDialog').modal();
}

function editUser(username) {
    $('.modal-title').text('Update User');
    $('#username').prop('disabled', true);
    $.getJSON("/admin/users/" + username, function (user) {
        var userDialogForm = $('#userDialog').find('form');
        userDialogForm[0].reset();
        userDialogForm.find('#username').val(user.username);
        userDialogForm.find('#role').val(user.role);
        userDialogForm.find('#firstName').val(user.firstName);
        userDialogForm.find('#lastName').val(user.lastName);
    });
    $('#userDialog').modal();
}

function createOrUpdateUser() {
    $('#username').prop('disabled', false);
    var user = $('#userDialog').find('form').serializeObject();
    user.enabled = true;
    console.log(user);
    $.ajax({
        url: '/admin/users/save',
        type: 'POST',
        data: JSON.stringify(user),
        contentType: 'application/json',
        success: function () {
            window.location.replace('/admin/users')
        }
    });
}
