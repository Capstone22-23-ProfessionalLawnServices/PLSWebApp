function deleteCustomer(e) {
    let customerId = document.getElementById("customerId").getAttribute("value");

    let url = ("/customers/delete/" + customerId);

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/customers";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}