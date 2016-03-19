package com.shaad.server.controller;

import com.shaad.server.dao.BankDAO;
import com.shaad.server.dao.OfficeDAO;
import com.shaad.server.entities.Bank;
import com.shaad.server.entities.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/banks")
public class MainController {
    @Autowired
    private BankDAO bankDAO;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Bank>> getBanksByCity(@RequestParam(value = "text", required = false) String city) {
        final List<Bank> items = (null == city || city.isEmpty()) ? bankDAO.getAllBank() : bankDAO.getBanksByCity(city);
        return new ResponseEntity<List<Bank>>(items, HttpStatus.OK);
    }
}
