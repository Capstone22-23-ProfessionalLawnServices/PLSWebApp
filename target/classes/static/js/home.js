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

async function setWeather() {

    let day_id = "#weather-module-day-";
    let params = {
        lat: "34.19",
        long: "-79.76",
        units: "imperial"
    }

    //Loads the connection credentials from the git.ignored json file "connections.json"

    let responseJsonConnections = await fetch("../../../connections.json")
        .then(response => response.json())
        .then(json => {
            return json;
        })

    //For api information: https://openweathermap.org/forecast5

    let urlWeather = responseJsonConnections.production.OWAPIWeatherURL + "lat=" + params.lat + "&lon=" +
        params.long + "&units=" + params.units + "&appid=" + responseJsonConnections.production.OWAPIKey;

    let responseJsonWeather = await fetch(urlWeather, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(json => {
            return json;
        })

    $(day_id + "0").html("Weather: " + Math.round(Number(responseJsonWeather.main.temp_max)) + "|" +
        Math.round(Number(responseJsonWeather.main.temp_min)));
    let weather_info_html = "              <ul>\n" +
        "                <li><span>Humidity: " + responseJsonWeather.main.humidity + "</span></li>\n" +
        "                <li><span>Description: " + responseJsonWeather.weather[0].description + " </span></li>\n" +
        "              </ul>\n";

    console.log(weather_info_html);

    $(day_id + "0-info").html(weather_info_html);

    let urlForecast = responseJsonConnections.production.OWAPIForecastURL + "lat=" + params.lat + "&lon=" +
        params.long + "&units=" + params.units + "&appid=" + responseJsonConnections.production.OWAPIKey;

    let responseJsonForecast = await fetch(urlForecast, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(json => {
            return json;
        })

    let prevDt = new Date().getDate() + 1;
    let weekday = 1;
    let tempMin = "200";
    let tempMax = "-100";
    let tempHumidity = "";
    let tempDescription = "";

    //Sets the max, min, and info for weather modules 1-5

    for(let timeInterval of responseJsonForecast.list) {
        let intervalDate = new Date (timeInterval.dt * 1000).getDate();

        if(intervalDate === new Date().getDate()) {
            continue;
        }

        if(prevDt !== intervalDate) {
            day_id = day_id.substring(0, day_id.lastIndexOf("-") + 1) + weekday;

            if(new Date().getDate() === intervalDate){
                $(day_id).html("Weather: " + timeInterval.main.temp_max + "|" + timeInterval.main.temp_min);
            }
            else if(tempMax === tempMin) {
                $(day_id).html(timeInterval.main.temp);
            }
            else {
                $(day_id).html("Weather: " + Math.round(Number(tempMax)) + "|" + Math.round(Number(tempMin)));
            }

            weather_info_html = "              <ul>\n" +
                "                <li><span>Humidity: " + tempHumidity + "</span></li>\n" +
                "                <li><span>Description: " + tempDescription + " </span></li>\n" +
                "              </ul>\n";

            $(day_id + "-info").html(weather_info_html);

            prevDt = intervalDate;
            weekday += 1;
            tempMin = "200";
            tempMax = "-100";
        }

        if(Number(timeInterval.main.temp_max) > Number(tempMax)) {
            tempMax = timeInterval.main.temp_max;
        }

        if(Number(timeInterval.main.temp_min) < Number(tempMin)) {
            tempMin = timeInterval.main.temp_min;
        }

        tempHumidity = timeInterval.main.humidity;
        tempDescription = timeInterval.weather[0].description;

    }

    day_id = day_id.substring(0, day_id.lastIndexOf("-") + 1) + 5;
    $(day_id).html("Weather: " + Math.round(Number(tempMax)) + "|" + Math.round(Number(tempMin)));

    weather_info_html = "              <ul>\n" +
        "                <li><span>Humidity: " + tempHumidity + "</span></li>\n" +
        "                <li><span>Description: " + tempDescription + " </span></li>\n" +
        "              </ul>\n";

    $(day_id + "-info").html(weather_info_html);

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
            background_color = "#72C5C2";
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
                "#72C5C2");
        }
        else {
            $("#" + document.getElementById(data).id).css("background-color",
        "#6D98AB");
        }

        console.log($(targetId).parent().parent().css("background-color"));
    }
    catch (Exception) {

    }
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