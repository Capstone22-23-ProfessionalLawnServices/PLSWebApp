function addAppointmentSelectCustomer() {
    //window.location.href = ("/add-appointment/select-customer");
    let url = "/add-appointment/select-customer";
    let jobRequest = document.getElementById("jobRequest").getAttribute("value");
    console.log(jobRequest);
    console.log(jobRequest.json);

    //$().post(url, {}, "json");
}


function updateAppointmentSelectCustomer() {
    let jobId = document.getElementById("jobId").getAttribute("value");

    window.location.href = ("/update-appointment/" + jobId + "/select-customer");}

function updateAppointmentSelectContact() {
    let jobId = document.getElementById("jobId").getAttribute("value");

    window.location.href = ("/update-appointment/" + jobId + "/select-contact");
}

function addCustomerClick(e) {
    let cost = document.getElementById("cost").value;
    let location = document.getElementById("location").value;
    let scheduledDate = document.getElementById("date").value;
    let startTime = document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time").value;

    let url = ("/add-appointment/select-customer?"
        + "cost=" + cost
        + "&location=" + location
        + "&scheduledDate=" + scheduledDate
        + "&startTime=" + startTime
        + "&endTime=" + endTime);


    console.log("youre here");
    let response = $.post(url, "text");

    console.log(response);
}

function loadUpdateAppointmentSelectCustomer(jobId) {

    let url = "/update-appointment/" + jobId + "/select-customer";

    window.location.href = url;
}