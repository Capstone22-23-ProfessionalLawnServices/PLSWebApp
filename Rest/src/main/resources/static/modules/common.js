function navbarDropdown(e) {

    let button_id = "#" + e.target.id;
    let dropdown_display_id = $(button_id).parent().next();

    if ($(dropdown_display_id).css("display") === "none") {
        $(button_id).prop("src", "../../../images/caret-down-square.svg")
        $(dropdown_display_id).css("display", "block")
    }
    else {
        $(button_id).prop("src", "../../../images/caret-right-square.svg")
        $(dropdown_display_id).css("display", "none")
    }
}