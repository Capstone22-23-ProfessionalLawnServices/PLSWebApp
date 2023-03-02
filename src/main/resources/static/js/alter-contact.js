function deleteContact(e) {
    let contactId = document.getElementById("contactId").getAttribute("value");

    let url = ("/delete-contact/" + contactId);

    window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}