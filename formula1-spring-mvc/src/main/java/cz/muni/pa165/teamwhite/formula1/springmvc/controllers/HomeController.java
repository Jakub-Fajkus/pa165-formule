package cz.muni.pa165.teamwhite.formula1.springmvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {

    final static Logger log = LoggerFactory.getLogger(HomeController.class);



    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String list(Model model) {
        return "home";
    }
}
