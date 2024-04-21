function setSelectedProject(id) {
    console.log(id);
    $('#project').val(id);
}

function selectProject() {
    var project = $('#project').val();
    console.log(project);
    $.ajax({
        url: '/project',
        type: 'POST',
        data: project,
        contentType: 'application/json',
        success: function () {
            window.location.replace('/issues')
        }
    });
}
