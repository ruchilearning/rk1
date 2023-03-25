package com.rk1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class UserCreditCardController {

    private static final Logger logger = LoggerFactory.getLogger(UserCreditCardController.class);

    /**
     * Whoops, throw an IOException
     */
    @RequestMapping(value = "/userdetails/exception/{throw}", method = RequestMethod.GET)
    @ResponseBody
    public String getCardDetails(@PathVariable("throw") boolean throwException) throws IOException {

        logger.info("This will throw an IOException");

        if (throwException) {
            throw new IOException("This is my IOException");
        }

        return "home";
    }

}
