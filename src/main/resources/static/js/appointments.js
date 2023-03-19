function rowClick(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("appointments/update/" + rowId);
}