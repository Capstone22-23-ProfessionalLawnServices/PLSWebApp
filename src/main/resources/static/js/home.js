function timeClock() {
    setTimeout("timeClock()", 1000);
    let now = new Date();
    let date;

    if (now.getHours() > 12) {
        date = (now.getHours() - 12).toString().padStart(2,"0") + ":" +
                now.getMinutes().toString().padStart(2,"0") + ":" +
                now.getSeconds().toString().padStart(2,"0") + " PM";
    }
    else {
        date = now.getHours().toString().padStart(2,"0") + ":" +
                now.getMinutes().toString().padStart(2,"0") + ":" +
                now.getSeconds().toString().padStart(2,"0") + " AM";
    }

    $("#clock-numbers").html(date);
}

function setDates() {

    let date = new Date();
    let day_id = "#day-";
    let day_id_content = "#day--content";
    let dateColors = getDateColors(date);
    let currentMonth = date.getMonth();


    for(let i = 0; i < 14; i++) {
        day_id = day_id.substring(0, (day_id.lastIndexOf("-") + 1)) + i;
        day_id_content = day_id_content.substring(0, day_id_content.indexOf("-") + 1) + i +
            day_id_content.substring(day_id_content.lastIndexOf("-"));
        $(day_id).html(date.toDateString());

        if(currentMonth === date.getMonth()) {
            $(day_id).css("background-color", dateColors.headerColor);
            $(day_id).css("color", dateColors.fontColor);
            $(day_id).parent().parent().css("background-color", dateColors.headerColor);
            $(day_id_content).children().css("background-color", dateColors.contentColor);
        }
        else {
            $(day_id).css("background-color", dateColors.headerNextColor);
            $(day_id).css("color", dateColors.fontNextColor);
            $(day_id).parent().parent().css("background-color", dateColors.headerNextColor);
            $(day_id_content).children().css("background-color", dateColors.contentNextColor);
        }

        date.setDate(date.getDate() + 1);
    }
}

function getDateColors(date) {

    let dateColors = {
        headerColor: "",
        headerNextColor: "",
        contentColor: "",
        fontColor: "",
        fontNextColor: "",
        contentNextColor: ""
    };

    if((date.getMonth() % 2) === 0) {
        dateColors.headerColor = "#EBAA3D";
        dateColors.fontColor = "#000000";
        dateColors.contentColor = "#FFE5B8";
        dateColors.headerNextColor = "#2A9793";
        dateColors.fontNextColor = "#000000";
        dateColors.contentNextColor = "#B1E1DF";
    }
    else if((date.getMonth() % 2) === 1) {
        dateColors.headerColor = "#2A9793";
        dateColors.fontColor = "#000000";
        dateColors.contentColor = "#B1E1DF";
        dateColors.headerNextColor = "#EBAA3D";
        dateColors.fontNextColor = "#000000";
        dateColors.contentNextColor = "#FFE5B8";
    }

    return dateColors;
}

function moduleInfo(e) {

    let button_id = "#" + e.target.id;
    let button_info_id = button_id + "-info";
    let set_color = $(button_id).parent().parent().parent().css("background-color");


    if($(button_info_id).css("display") === "none") {
        $(button_id).css("border-radius", "8px 8px 0 0");
        $(button_id).css("margin-bottom", "0");

        if (set_color === "rgb(42, 151, 147)") {
            $(button_id).css("background-color", "#72C5C2");
        }
        else {
            $(button_id).css("background-color", "#FAD28D");
        }

        $(button_info_id).show();
    }
    else {
        let background_color = $(button_id).css("background-color");

        let set_color = $(button_id).parent().parent().parent().css("background-color");

        if(set_color === "rgb(42, 151, 147)") {
            background_color = "#B1E1DF";
        }
        else {
            background_color = "#FFE5B8";
        }

        $(button_id).css("border-radius", "8px");
        $(button_id).css("margin-bottom", "5px");
        $(button_id).css("background-color", background_color);
        $(button_info_id).hide();
    }
}

function drag(e) {
    e.dataTransfer.setData("text", e.target.id);
}

function allowDrop(e) {
    e.preventDefault();
}

function drop(e) {
    e.preventDefault();

    let dateHeaderText = e.target.parentElement.firstElementChild.textContent;
    let date = new Date(dateHeaderText.substring(4, dateHeaderText.length));
    let dateFormatted = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    let jobButtonId = e.dataTransfer.getData("text");
    let jobId = jobButtonId.substring((jobButtonId.indexOf('_') + 1), jobButtonId.length);

    let url = "/home/reschedule?jobId=" + jobId + "&scheduleDate=" + dateFormatted;
    console.log(url);


    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            //window.location.href = "/appointments/update/" + jobId;
            let data = e.dataTransfer.getData("text");
            let target = e.target;
            let targetId = "#" + target.id;

            try {
                if (!($(targetId).attr("class").split(/\s+/).includes("date-content"))) {
                    target = $(targetId).parent()[0];
                    target.appendChild(document.getElementById(data));
                } else {
                    target.appendChild(document.getElementById(data));
                }

                if($("#" + target.id).parent().parent().css("background-color") === "rgb(235, 170, 61)") {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#FFE5B8");
                }
                else if($("#" + target.id).parent().parent().css("background-color") === "rgb(42, 151, 147)") {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#B1E1DF");
                }
                else {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#6D98AB");
                }

                console.log($(targetId).parent().parent().css("background-color"));
            }
            catch (Exception) {

            }
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
/*
    let data = e.dataTransfer.getData("text");
    let target = e.target;
    let targetId = "#" + target.id;

    try {
        if (!($(targetId).attr("class").split(/\s+/).includes("date-content"))) {
            target = $(targetId).parent()[0];
            target.appendChild(document.getElementById(data));
        } else {
            target.appendChild(document.getElementById(data));
        }

        if($("#" + target.id).parent().parent().css("background-color") === "rgb(235, 170, 61)") {
            $("#" + document.getElementById(data).id).css("background-color",
                "#FFE5B8");
        }
        else if($("#" + target.id).parent().parent().css("background-color") === "rgb(42, 151, 147)") {
            $("#" + document.getElementById(data).id).css("background-color",
                "#B1E1DF");
        }
        else {
            $("#" + document.getElementById(data).id).css("background-color",
        "#6D98AB");
        }

        console.log($(targetId).parent().parent().css("background-color"));
    }
    catch (Exception) {

    }
    */
}

function startSession() {
    $("#start-session-button").hide();
    $("#client-list").hide();
    $("#client-list-label").hide();
    $("#end-session-button").show();
    $("#active-client").show();
    $("#active-client-label").show();
}

function endSession() {
    $("#end-session-button").hide();
    $("#active-client").hide();
    $("#active-client-label").hide();
    $("#start-session-button").show();
    $("#client-list").show();
    $("#client-list-label").show();
}