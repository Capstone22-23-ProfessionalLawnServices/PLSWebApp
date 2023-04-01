function rowClick(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/customers/update/" + rowId);
}

function customerSelect(e) {
    let customerId = e.target.parentNode.getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/appointments/update/" + jobId + "/select-customer?customerId=" + customerId);

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