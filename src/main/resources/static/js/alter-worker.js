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

function createWorker() {
    let name = document.getElementById("name") == null
        ? "":document.getElementById("name").value;
    let email = document.getElementById("email") == null
        ? "":document.getElementById("email").value;
    let phone = document.getElementById("phone") == null
        ? "":document.getElementById("phone").value;

    let url = "/workers/add?name=" + name + "&email=" + email + "&phone=" + phone;

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

function updateWorkerClick() {
    let currentUrl = window.location.href;
    let workerId = currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
    let name = document.getElementById("name") == null
        ? "":document.getElementById("name").value;
    let email = document.getElementById("email") == null
        ? "":document.getElementById("email").value;
    let phone = document.getElementById("phone") == null
        ? "":document.getElementById("phone").value;

    let url = "/workers/update/" + workerId + "?name=" + name + "&email=" + email + "&phone=" + phone;

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/workers/update/" + workerId;
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });

}

function addWorkerPLSAccount() {
    let currentUrl = window.location.href;
    let workerId = currentUrl.substring(currentUrl.lastIndexOf('/') + 1);

    let url = "/worker-account/create-account/" + workerId;

    window.location.href = url;
}