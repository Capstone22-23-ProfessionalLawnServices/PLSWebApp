function deleteCustomer(e) {
    let customerId = document.getElementById("customerId").getAttribute("value");

    let url = ("/delete-customer/" + customerId);

    window.location.href = $.ajax({type: "POST", url: url, async: false}).responseText;
}