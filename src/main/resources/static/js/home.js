let debounceTimer;

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



    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            console.log(response);
            //Moves div from one container to another
            let data = e.dataTransfer.getData("text");
            let targetContainer = e.target;
            let originalContainer = document.getElementById(e.dataTransfer.getData("text")).parentElement;
            let targetId = "#" + targetContainer.id;

            try {
                if (!($(targetId).attr("class").split(/\s+/).includes("date-content"))) {
                    targetContainer = $(targetId).parent()[0];
                    targetContainer.appendChild(document.getElementById(data));
                } else {
                    targetContainer.appendChild(document.getElementById(data));
                }

                if($("#" + targetContainer.id).parent().parent().css("background-color") === "rgb(235, 170, 61)") {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#FFE5B8");
                }
                else if($("#" + targetContainer.id).parent().parent().css("background-color") === "rgb(42, 151, 147)") {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#B1E1DF");
                }
                else {
                    $("#" + document.getElementById(data).id).css("background-color",
                        "#6D98AB");
                }

                if (originalContainer.id === "day-0-content") {
                    removeAppointmentToClientList(data);
                }

                if (targetContainer.id === "day-0-content") {
                    addAppointmentToClientList(data);
                }


            }
            catch (Exception) {
                console.log("There was an error moving the appointment button")
            }
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function startSession() {

    let clientList = $('#client-list');
    let jobId = clientList.val();
    let selectedOption = $('#client-list :selected');
    let name = selectedOption.text();
    let url = "/home/start-session?jobId=" + jobId;
    let calendarButton = $('#job_' + jobId);

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            $("#start-session-button").attr("hidden",true);
            $("#client-list").attr("hidden",true);
            $("#client-list-label").attr("hidden",true);
            selectedOption.remove();
            calendarButton.remove();
            $("#end-session-button").removeAttr('hidden');
            $("#active-client").removeAttr('hidden');
            $("#active-client").text(name);
            $("#active-client-label").removeAttr('hidden');
            $('#active-client-id').val(jobId);
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });

}

function endSession() {
    let clientList = $('#client-list');
    let jobId = $('#active-client-id').val();
    let url = "/home/end-session?jobId=" + jobId;

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            $("#end-session-button").attr("hidden",true);
            $("#active-client").attr("hidden",true);
            $("#active-client-label").attr("hidden",true);
            $("#start-session-button").removeAttr('hidden');
            $("#client-list").removeAttr('hidden');
            $("#client-list-label").removeAttr('hidden');
            $('#active-client-id').val('');
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}

function addAppointmentToClientList(data) {

    let clientList = $('#client-list');
    let clientButton = $('#' + data);
    let value = clientButton[0].firstChild.value;
    let name = clientButton.children().eq(1)[0].textContent;



    let optionHtml = "<option value='" + value + "'>" + name + "</option>";

    clientList.append(optionHtml);
}

function removeAppointmentToClientList(data) {
    let clientList = $('#client-list');
    let clients = clientList.children();
    let clientButton = $('#' + data);
    let removeId = clientButton[0].firstChild.value;

    for (let i = 0; i < clients.length; i++) {
        if (clients[i].value === removeId) {
            clients[i].remove();
        }
    }
}

function searchCustomerOnType() {
    let customerSearchList = $('#customer-search-results');
    let searchField = $('#customer-search-field');
    let searchQuery = searchField.val();

    customerSearchList.empty();
    clearTimeout(debounceTimer);

    debounceTimer = setTimeout(function() {

        if (searchQuery === "") {
            return;
        }

        let url = "/home/search-customers?customerName=" + searchQuery;

        fetch(url, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(customerArrayList => {
                let customerList = customerArrayList.map(function(array) {
                    return {
                        customerName: array.customerName,
                        customerId: array.customerId,
                    };
                });

                if (customerList.length === 0) {
                    return;
                }

                for (let i = 0; i < customerList.length; i++) {
                    let html = "<option value='" + customerList[i].customerId + "'> " +
                        customerList[i].customerName + "</option>";

                    customerSearchList.append(html);
                }

            })
            .catch(error => {
                alert("There was an issue with the fetch request.")
            });

        console.log("Searching...");
    }, 1500);
}

function appointmentClick(e) {
    let quickAddHeader = $('#add-header');

    //This checks whether it is the employee view
    if (quickAddHeader.length !== 0) {
        let appointmentDiv = $('#' + e.currentTarget.id);
        let appointmentInput = appointmentDiv.children().eq(0);
        let appointmentId = appointmentInput.val();

        window.location.href = "/appointments/update/" + appointmentId;
    }
}

function datalistClick(e) {
    let value = e.target.value;
    let dateSelector = $('#add-schedule-date');
    let inputField = $('#customer-search-field');
    let selectedCustomer = $('#selected-customer');
    let customerId = $('#selected-customer-id');
    let selectedOption = document.querySelector('#customer-search-results option[value="' + value + '"]');
    let changeCustomerButton = $('#change-customer-button');

    if (selectedOption) {
        let selectedOptionText = selectedOption.textContent;
        inputField.attr("hidden",true);
        selectedCustomer.text('Selected: ' + selectedOptionText);
        selectedCustomer.removeAttr('hidden');
        changeCustomerButton.removeAttr('hidden');
        customerId.attr('value', value);
        dateSelector.css('margin', '5px 0 5px 0');
    }
}

function changeCustomerClick(e) {
    let inputField = $('#customer-search-field');
    let selectedCustomer = $('#selected-customer');
    let customerId = $('#selected-customer-id');
    let changeCustomerButton = $('#change-customer-button');
    let dateSelector = $('#add-schedule-date');

    inputField.removeAttr('hidden');
    selectedCustomer.attr('hidden', true);
    changeCustomerButton.attr('hidden', true);
    customerId.attr('value', '');
    inputField.text('');
    inputField.val('');
    dateSelector.css('margin', '15px 0 19px 0');
}

function quickScheduleClick(e) {
    let scheduledDate = document.getElementById("add-schedule-date") == null ? "":
        document.getElementById("add-schedule-date").value;
    let customerId = $('#selected-customer-id').val();
    let appointmentId;

    if (scheduledDate !== '' && customerId !== '') {
        let url = "/appointments/add?scheduledDate=" + scheduledDate + "&cost=0";
        let customerUrl;
        let currentDate = new Date();
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        let plannedDate = new Date(scheduledDate);
        plannedDate.setHours(0);
        plannedDate.setMinutes(0);
        plannedDate.setSeconds(0);
        plannedDate.setDate(plannedDate.getDate() + 1);
        let value;


        fetch(url, {
            method: 'POST'
        })
            .then(response => response.text())
            .then(data => {
                appointmentId = data;
                customerUrl = '/appointments/update/' + data + '/select-customer?customerId=' + customerId;
                value = data;
            })
            .then(something => {
                fetch(customerUrl, {
                    method: 'POST'
                })
                    .then(response => response.text())
                    .then(data => {
                            let timeDifference = Math.abs(plannedDate - currentDate);
                            let daysDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));

                            if (plannedDate.getUTCDate() === currentDate.getUTCDate()) {
                                daysDifference = 0;
                            }

                            if (daysDifference < 12) {
                                let appointmentDiv = "<button id='job_" + appointmentId +
                                    "' class='content-module' draggable='true' ondragstart='drag(event)'" +
                                    " onclick='appointmentClick(event)'>" +
                                    "<input value='"+ value + "' hidden/>" +
                                    "<span>" + data + "</span>" +
                                    "</button>";

                                let appointmentDivId = 'job_' + appointmentId;
                                let containerId = "day-" + daysDifference + "-content";
                                let container = $('#' + containerId);
                                container.append(appointmentDiv);

                                if(container.parent().parent().css("background-color") === "rgb(235, 170, 61)") {
                                    $("#" + appointmentDivId).css("background-color",
                                        "#FFE5B8");
                                }
                                else if(container.parent().parent().css("background-color") === "rgb(42, 151, 147)") {
                                    $("#" + appointmentDivId).css("background-color",
                                        "#B1E1DF");
                                }
                                else {
                                    $("#" + appointmentDivId).css("background-color",
                                        "#6D98AB");
                                }

                                changeCustomerClick(e);

                                if (daysDifference === 0) {
                                    addAppointmentToClientList('job_' + appointmentId);
                                }

                            }
                    })
                    .catch(error => {
                        alert("There was an issue with the fetch request.")
                    });
            })
            .catch(error => {
                alert("There was an issue with the fetch request.")
            });
    }
    else{
        alert("A customer must be selected from the dropdown and a date must be entered.");
    }
}

//Adds appropriate space if the employee calendar is loaded

function setDateSpacing() {
    let quickAddHeader = $('#add-header');

    if (quickAddHeader.length === 0) {

        let dayDiv = $('#day-4').parent().parent();
        dayDiv.css({"margin": "10px 19px 5px 10px"});

        for (let i = 5; i < 9; i++) {
            dayDiv = $('#day-' + i).parent().parent();
            dayDiv.css({"margin": "10px 19px 5px 19px"});
        }

    }

}