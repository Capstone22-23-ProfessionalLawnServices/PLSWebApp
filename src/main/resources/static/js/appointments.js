function rowClick(e) {
    let rowId = e.target.parentNode.getAttribute("value");

    window.location.href = ("/appointments/update/" + rowId);
}

function nextPage(e) {
    let pageNumber = Number(document.getElementById("current-page").value);
    let nextPage = pageNumber;

    window.location.href = ("/appointments?page=" + nextPage);
}

function previousPage(e) {
    let pageNumber = Number(document.getElementById("current-page").value);
    let previousPage = pageNumber - 2;

    window.location.href = ("/appointments?page=" + previousPage);
}

function pageInBox(e) {
    if (e.keyCode === 13) {
        let pageNumber = Number(document.getElementById("current-page").value);

        window.location.href = ("/appointments?page=" + pageNumber);
    }
}