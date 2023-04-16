function deleteWorker(e) {
    let workerId = document.getElementById("workerId").getAttribute("value");

    let url = ("/workers/delete/" + workerId);

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/workers";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}