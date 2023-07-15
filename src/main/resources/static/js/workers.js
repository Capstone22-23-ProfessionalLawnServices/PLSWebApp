function rowSearch(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/workers/update/" + rowId);
}

function workerSelect(e) {
    let workerId = e.target.parentNode.getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/appointments/update/" + jobId + "/select-worker?workerId=" + workerId);

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