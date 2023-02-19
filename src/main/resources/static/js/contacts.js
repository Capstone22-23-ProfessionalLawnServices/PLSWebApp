function rowSelect(e) {
    let rowId = e.target.parentNode.getAttribute("value");
    let url = window.location.href;
    let jobId = document.getElementById("jobId").getAttribute("value");

    if(url.split("/").includes("add-appointment")) {
        window.location.href = ("/add-appointment?add-contact=" + rowId);
    }
    else {
        window.location.href = ("/update-appointment/" + jobId + "?add-contact=" + rowId);
    }
}

function rowSearch(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/update-contact/" + rowId);
}

function searchContacts() {
    let searchString = $("#search-contacts-field").val();

    window.location.href = ("/search-contacts?name=" + searchString);
}

function contactSelect(e) {
    let contactId = e.target.parentNode.getAttribute("value");
    let jobId = document.getElementById("jobId").getAttribute("value");

    let url = ("/update-appointment/" + jobId + "?contactId=" + contactId);

    window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}