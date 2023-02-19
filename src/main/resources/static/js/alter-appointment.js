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

    window.location.href = ("/update-appointment/" + jobId + "/select-customer");
}

function updateAppointmentSelectContact() {
    let jobId = document.getElementById("jobId").getAttribute("value");

    window.location.href = ("/update-appointment/" + jobId + "/select-contact");
}

function addCustomerClick(e) {
    let cost = document.getElementById("cost").value == null ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location").value == null ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date").value == null ? "null":document.getElementById("date").value;
    let startTime = document.getElementById("start-time").value == null ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time").value == null ? "":document.getElementById("end-time").value;

    if (scheduledDate === "") {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let url = "/add-appointment/select-customer?"
        + "cost=" + cost
        + "&location=" + location
        + "&scheduledDate=" + scheduledDate
        + "&startTime=" + startTime
        + "&endTime=" + endTime;

    window.location.href = $.ajax({type: "post", url: url, async: false}).responseText;
    //window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}

function updateCustomerClick(e) {
    let jobId = document.getElementById("jobId").value == null ? "":document.getElementById("jobId").value;
    let cost = document.getElementById("cost").value == null ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location").value == null ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date").value == null ? "":document.getElementById("date").value;
    let startTime = document.getElementById("start-time").value == null ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time").value == null ? "":document.getElementById("end-time").value;

    if (scheduledDate === "") {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let url = "/update-appointment/" + jobId + "/select-customer?"
        + "cost=" + cost
        + "&location=" + location
        + "&scheduledDate=" + scheduledDate
        + "&startTime=" + startTime
        + "&endTime=" + endTime;

    window.location.href = $.ajax({type: "post", url: url, async: false}).responseText;
    //window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}

function loadUpdateAppointmentSelectCustomer(jobId) {

    let url = "/update-appointment/" + jobId + "/select-customer";

    window.location.href = url;
}

function updateContactClick(e) {
    let jobId = document.getElementById("jobId").value == null ? "":document.getElementById("jobId").value;
    let customerId = document.getElementById("customerId").value == null ? "":document.getElementById("customerId").value;
    let cost = document.getElementById("cost").value == null ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location").value == null ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date").value == null ? "":document.getElementById("date").value;
    let startTime = document.getElementById("start-time").value == null ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time").value == null ? "":document.getElementById("end-time").value;

    if (scheduledDate === "") {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let url = "/update-appointment/" + jobId + "/select-contact?"
        + "cost=" + cost
        + "&location=" + location
        + "&scheduledDate=" + scheduledDate
        + "&startTime=" + startTime
        + "&endTime=" + endTime
        + "&customerId=" + customerId;

    window.location.href = $.ajax({type: "post", url: url, async: false}).responseText;
}