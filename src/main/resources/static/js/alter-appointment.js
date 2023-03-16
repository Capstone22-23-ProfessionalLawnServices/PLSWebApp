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

function updateCustomerClick(e) {
    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;
    let cost = document.getElementById("cost") == null ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location") == null ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date") == null ? "":document.getElementById("date").value;
    let startTime = document.getElementById("start-time") == null ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time") == null ? "":document.getElementById("end-time").value;

    if (scheduledDate === "" || scheduledDate === null) {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let url = "/update/" + jobId + "/select-customer?";

    if (cost !== "") {
        url += "cost=" + cost + "&";
    }

    if (location !== "") {
        url += "location=" + location + "&";
    }

    if (scheduledDate !== "") {
        url += "scheduledDate=" + scheduledDate + "&";
    }

    if (startTime !== "") {
        url += "startTime=" + startTime + "&";
    }

    if (endTime !== "") {
        url += "endTime=" + endTime + "&";
    }

    if (url.charAt(url.length - 1) === '$') {
        url = url.substring(0, (url.length - 1))
    }

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
    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;
    let cost = document.getElementById("cost") == null ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location") == null ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date") == null ? "":document.getElementById("date").value;
    let startTime = document.getElementById("start-time") == null ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time") == null ? "":document.getElementById("end-time").value;

    if (scheduledDate === "" || scheduledDate === null) {
        alert("An appointment must have a scheduled date.")
        return;
    }

    let url = "/update/" + jobId + "/select-contact?";

    if (cost !== "") {
        url += "cost=" + cost + "&";
    }

    if (location !== "") {
        url += "location=" + location + "&";
    }

    if (scheduledDate !== "") {
        url += "scheduledDate=" + scheduledDate + "&";
    }

    if (startTime !== "") {
        url += "startTime=" + startTime + "&";
    }

    if (endTime !== "") {
        url += "endTime=" + endTime + "&";
    }

    if (url.charAt(url.length - 1) === '$') {
        url = url.substring(0, (url.length - 1))
    }

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

    let url = ("/delete-appointment/" + jobId);

    window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}

