function rowClick(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/update-customer/" + rowId);
}

function customerSelect(e) {
    let customerId = e.target.parentNode.getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/update-appointment/" + jobId + "?customerId=" + customerId);

    $.post(url);
}