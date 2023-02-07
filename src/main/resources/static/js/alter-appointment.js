function addAppointmentSelectCustomer() {
    window.location.href = ("/add-appointment/select-customer");
}

function addApppointmentSelectContact() {
    window.location.href = ("/add-appointment/select-contact");
}


function updateAppointmentSelectCustomer() {
    let jobId = document.getElementById("jobId").getAttribute("value");

    window.location.href = ("/update-appointment/" + jobId + "/select-customer");}

function updateAppointmentSelectContact() {
    let jobId = document.getElementById("jobId").getAttribute("value");

    window.location.href = ("/update-appointment/" + jobId + "/select-contact");
}