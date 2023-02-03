function rowClick(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/update-contact/" + rowId);
}

function searchContacts() {
    let searchString = $("#search-contacts-field").val();
    console.log(searchString);

    window.location.href = ("/search-contacts?name=" + searchString);
}