// function addCustomerClick(e) {
//     let cost = document.getElementById("cost").value == null ? "0.0":document.getElementById("cost").value;
//     let location = document.getElementById("location").value == null ? "":document.getElementById("location").value;
//     let scheduledDate = document.getElementById("date").value == null ? "null":document.getElementById("date").value;
//     let startTime = document.getElementById("start-time").value == null ? "":document.getElementById("start-time").value;
//     let endTime = document.getElementById("end-time").value == null ? "":document.getElementById("end-time").value;
//
//     if (scheduledDate === "") {
//         alert("An appointment must have a scheduled date.")
//         return;
//     }
//
//     let url = "/add/select-customer?"
//         + "cost=" + cost
//         + "&location=" + location
//         + "&scheduledDate=" + scheduledDate
//         + "&startTime=" + startTime
//         + "&endTime=" + endTime;
//
//     window.location.href = $.ajax({type: "post", url: url, async: false}).responseText;
//     //window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
// }

function addAppointment(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let url = "/appointments/add" + getUrlParams();

    console.log(url);

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/appointments";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function updateAppointment(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "appointments/update/" + jobId + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/appointments/update/" + jobId;
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function updateCustomerClick(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "appointments/update/" + jobId + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/appointments/update/" + jobId + "/select-customer";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function updateContactClick(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "appointments/update/" + jobId + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/appointments/update/" + jobId + "/select-customer";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function removeContactClick(e) {
    let contactId = e.target.parentNode.getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = "/update-appointment/" + jobId + "/delete-help?contactId=" + contactId;

    window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}

function deleteJob(e) {
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/appointments/delete/" + jobId);

    $.ajax({type: "POST", url: url, async: false}).responseText;

    window.location.href = "/appointments";
}

function getUrlParams() {
    let cost = document.getElementById("cost") == null ? "0.0":document.getElementById("cost").value;
    console.log(cost);
    let location = document.getElementById("location") == null ? "":document.getElementById("location").value;
    console.log(location);
    let scheduledDate = document.getElementById("date") == null ? "":document.getElementById("date").value;
    console.log(scheduledDate);
    let startTime = document.getElementById("start-time") == null ? "":document.getElementById("start-time").value;
    console.log(startTime);
    let endTime = document.getElementById("end-time") == null ? "":document.getElementById("end-time").value;
    console.log(endTime);

    if (scheduledDate === "" || scheduledDate === null) {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let params = "?";

    if (cost !== "") {
        params += "cost=" + paramSpaceReplace(cost) + "&";
    }

    if (location !== "") {
        params += "location=" + paramSpaceReplace(location) + "&";
    }

    if (scheduledDate !== "") {
        params += "scheduledDate=" + paramSpaceReplace(scheduledDate) + "&";
    }

    if (startTime !== "") {
        params += "startTime=" + paramSpaceReplace(startTime) + "&";
    }

    if (endTime !== "") {
        params += "endTime=" + paramSpaceReplace(endTime) + "&";
    }

    if (params.charAt(params.length - 1) === '$') {
        params = params.substring(0, (params.length - 2))
    }

    return params;
}

function paramSpaceReplace(param) {

    for (let i = 0; i < param.length; i++) {
        if (param.charAt(i) === ' ') {
            param = param.substring(0, i) + "%20" + param.substring(i + 1);
        }
    }

    return param;

}