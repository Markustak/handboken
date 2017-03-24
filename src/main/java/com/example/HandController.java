package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.DataSource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-21.
 */

@Controller
public class HandController {

    @Autowired
    private dbconnect dbconnecthand;

    @GetMapping("/larare")
    public ModelAndView larare2() {
        String w = "Enter password";
        User user = new User("", "");
        return new ModelAndView("larare").addObject("user", user).addObject("wrong,w");
    }

    @PostMapping("/larare")
    public ModelAndView larare(@Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("larare");
        } else if (user.getUsername().equalsIgnoreCase("test") && user.getPassword().equalsIgnoreCase("pass")) {
            session.setAttribute("user", user);
            System.out.println("sessionSTART");
            return new ModelAndView("redirect:/");
        } else {
            String w = "wrong password";
            return new ModelAndView("larare").addObject("wrong", w);
        }

    }
    @GetMapping("/")
    public ModelAndView index(HttpSession session) throws Exception {
        System.out.println("ID: "+ counter);
        if (session.getAttribute("user") != null) {
            System.out.println("inloggadyey");
            String removeBtn = "Remove";
            ModelAndView modelmodel = new ModelAndView("index");
            return modelmodel.addObject("knappen", removeBtn);
        } else {
            return new ModelAndView("index");
        }
    }


    Hand handObject = new Hand();
    int counter = 0;
    int dbCounter = 0;
    @MessageMapping("/hello")
    @SendTo("/topic/message/room")
    public Hand hand(HandMessage message) throws Exception {

        if (message.getName().equals("hej")) {
            counter++;
            String snothing = "";
            handObject.addContent(snothing);
            return handObject;
        } else {
            dbCounter = dbconnecthand.getId();
            dbconnecthand.addHand(message);
            String s = "";
            s = s + "<tr><td><i class=\"fa fa-hand-paper-o\" aria-hidden=\"true\"></i>" + message.getName() + "</td><td>" + message.getMessage() + "</td><td>" + message.getRoom() + " </td><td><button class=\"removeBtn\" id=\"btn" + counter +"."+dbCounter + "\" onclick=\"getParent(this)\">Remove</button></td></tr>'";
            counter++;
            System.out.println("COUNTER: " + counter);
            System.out.println("dbCOunter: " + dbCounter);
            handObject.addContent(s);
            return handObject;
        }
    }

    

    @MessageMapping("/delete")
    @SendTo("/topic/message/room")
    public Hand handDelete(HandMessage message) throws Exception {
        int splitz = message.toString().indexOf('.'); //används för att separera databasid från buttonId
        int away = Integer.parseInt(message.getName().substring(3,splitz+1)); //används för att separera databasid från buttonId
        dbconnecthand.changeHand(Integer.parseInt(message.getName().substring(splitz+2))+1); //Sätter databas på "löst"
        handObject.getContent().set(away, ""); //tar bort från html


        return handObject;
    }

}
