function validateForm(e) {

    e.preventDefault();

    let user = {
        username: document.getElementById("username-field").value,
        password: document.getElementById("password-field").value
    }

    console.log(user.username);

    if(!(user.username === "admin@admin" && user.password === "password")) {
        $(".login-form").css("margin-top","0")
        $("#invalid-login-prompt").show(200);
        document.getElementById("password-field").value = "";
    }

    else {
        window.location = "www/modules/home/home.html"
    }
}
