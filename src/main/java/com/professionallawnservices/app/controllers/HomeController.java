package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.models.json.openweather.PlsWeather;
import com.professionallawnservices.app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.*;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewHome(Model model) {
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();
        ArrayList<PlsWeather> plsWeatherArrayList = homeService.getCalendarWeather();

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("weatherZero", plsWeatherArrayList.get(0));
        model.addAttribute("weatherOne", plsWeatherArrayList.get(1));
        model.addAttribute("weatherTwo", plsWeatherArrayList.get(2));
        model.addAttribute("weatherThree", plsWeatherArrayList.get(3));
        model.addAttribute("weatherFour", plsWeatherArrayList.get(4));
        model.addAttribute("weatherFive", plsWeatherArrayList.get(5));

        return "home";
    }

}