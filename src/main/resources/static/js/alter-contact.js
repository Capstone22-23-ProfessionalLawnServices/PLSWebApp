function deleteContact(e) {
    let contactId = document.getElementById("contactId").getAttribute("value");

    let url = ("/contacts/delete/" + contactId);

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/contacts";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}