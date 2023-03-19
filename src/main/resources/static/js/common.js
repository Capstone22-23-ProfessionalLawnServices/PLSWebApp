function navbarDropdown(e) {

    let button_id = "#" + e.target.id;
    let dropdown_display_id = $(button_id).parent().next();

    if ($(dropdown_display_id).css("display") === "none") {
        //$(button_id).prop("src", "../../../images/caret-down-square.svg")
        $(dropdown_display_id).css("display", "block")
    }
    else {
        //$(button_id).prop("src", "../../../images/caret-right-square.svg")
        $(dropdown_display_id).css("display", "none")
    }
}

function loadNavbar() {
    $('#navbar-insert').load("html-elements.html");
}

function formClear() {
    $(':input','#add-form').not(':button, :submit, :reset, :hidden').val('');
}

// Table navigation functions

function nextPage(e) {
    let currentUrl = window.location.pathname + window.location.search;
    let newUrl = removeParameters(currentUrl);
    let pageNumber = Number(document.getElementById("current-page").value);
    let nextPage = pageNumber;

    window.location.href = (newUrl + "?page=" + nextPage);

}

function previousPage(e) {
    let currentUrl = window.location.pathname + window.location.search;
    let newUrl = removeParameters(currentUrl);
    let pageNumber = Number(document.getElementById("current-page").value);
    let previousPage = pageNumber - 2;

    window.location.href = (newUrl + "?page=" + previousPage);
}

function pageInBox(e) {
    if (e.keyCode === 13) {
        let currentUrl = window.location.pathname + window.location.search;
        let newUrl = removeParameters(currentUrl);
        let pageNumber = Number(document.getElementById("current-page").value);

        window.location.href = (newUrl + "?page=" + pageNumber);
    }
}

function removeParameters(url) {
    console.log(url);
    return url.substring(0, url.indexOf('?'));
}