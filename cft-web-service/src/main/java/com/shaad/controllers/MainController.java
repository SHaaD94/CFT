package com.shaad.controllers;

import com.shaad.models.Bank;
import com.shaad.models.BankDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private BankDao bankDao;

    @RequestMapping(value = "/getList", method = RequestMethod.GET, produces = "application/xhtml+xml")
    @ResponseBody
    public String getList() {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<bankList>";
        for (Bank bank : bankDao.findAll()) {
            result += bank.getContent();
        }
        result += "</bankList>";
        return result;
    }
}
