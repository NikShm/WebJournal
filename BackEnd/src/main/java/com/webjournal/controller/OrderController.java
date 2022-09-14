package com.webjournal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/*
@author Микола
@project High_school_library
@class OrderController
@version 1.0.0
@since 05.09.2022 - 23.16
*/
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/order", produces = "application/json")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
}
