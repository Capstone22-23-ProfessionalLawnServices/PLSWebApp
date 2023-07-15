function addAppointment(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let url = "/appointments/add" + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => response.text())
        .then(data => {
            window.location.href = "/appointments/update/" + data;
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function updateAppointment(e) {

    updateWorkerPay();

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "/appointments/update/" + jobId + getUrlParams();

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

function updateCustomerAddJobClick(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "/appointments/add" + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => response.text())
        .then(data => {
            window.location.href = "/appointments/update/" + data + "/select-customer";
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

    let url = "/appointments/update/" + jobId + getUrlParams();

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

function updateWorkerClick(e) {

    let params = getUrlParams();

    if (params === null) {
        return;
    }

    let jobId = document.getElementById("jobId") == null ? "":document.getElementById("jobId").value;

    let url = "/appointments/update/" + jobId + getUrlParams();

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/appointments/update/" + jobId + "/select-worker";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function removeWorkerClick(e) {
    let targetElement = e.target;
    let removeButtonCellJQ = $(e.target);
    let idCellJQ = $(removeButtonCellJQ.parent().siblings()[0]);

    let workerId = idCellJQ[0].getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = "/help/delete?jobId=" + jobId + "&workerId=" + workerId;

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

function deleteJob(e) {
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/appointments/delete/" + jobId);

    $.ajax({type: "POST", url: url, async: false}).responseText;

    window.location.href = "/appointments";
}

function getUrlParams() {
    let cost = document.getElementById("cost") == null
        ? "0.0":document.getElementById("cost").value;
    let location = document.getElementById("location") == null
        ? "":document.getElementById("location").value;
    let scheduledDate = document.getElementById("date") == null
        ? "":document.getElementById("date").value;
    let startTime = document.getElementById("start-time") == null
        ? "":document.getElementById("start-time").value;
    let endTime = document.getElementById("end-time") == null
        ? "":document.getElementById("end-time").value;
    let notes = document.getElementById("notes") == null
        ? "":document.getElementById("notes").value;

    if (scheduledDate === "" || scheduledDate === null) {
        alert("An appointment must have a scheduled date.")
        return null;
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

    if (notes !== "") {
        params += "notes=" + paramSpaceReplace(notes) + "&";
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

function addTotalTime() {

    let startTimeString = document.getElementById("start-time") == null
        ? "":document.getElementById("start-time").value;
    let endTimeString = document.getElementById("end-time") == null
        ? "":document.getElementById("end-time").value;

    if (startTimeString !== "" && endTimeString !== "") {

        let [hours1, minutes1] = startTimeString.split(":");
        let [hours2, minutes2] = endTimeString.split(":");

        let date1 = new Date();
        date1.setHours(hours1);
        date1.setMinutes(minutes1);

        let date2 = new Date();
        date2.setHours(hours2);
        date2.setMinutes(minutes2);

        let differenceInMinutes = Math.abs(date2 - date1) / (1000 * 60);

        $('#total-time').text(Math.floor(differenceInMinutes) + " minute(s)");

    }
    else {
        $('#total-time').attr("hidden",true);
        $('#total-time-label').attr("hidden",true);
    }

}

function updateWorkerPay() {

    let tableBodyJQ = $('#worker-table-body');
    let tableRowsJQ = $(tableBodyJQ.children());

    for (let i = 0; i < tableRowsJQ.length; i++) {
        let workerPay = $($($(tableRowsJQ[i]).children())[2]).children()[0].innerHTML;
        let helpId = tableRowsJQ[i].getAttribute('value');

        let url = "/help/update-pay/" + helpId + "?workerPay=" + workerPay;

        fetch(url, {
            method: 'POST'
        })
            .then(response => {
                console.log("Updated pay for help: " + helpId);
            })
            .catch(error => {
                alert("There was an issue with the fetch request.")
            });
    }
}