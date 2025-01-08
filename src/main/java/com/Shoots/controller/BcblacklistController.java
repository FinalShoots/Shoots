package com.Shoots.controller;

import com.Shoots.domain.BcBlacklist;
import com.Shoots.service.BcBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bcBlacklist")
public class BcblacklistController {

    private static Logger logger = (Logger) LoggerFactory.getLogger(BcblacklistController.class);
    private BcBlacklistService bcBlacklistService;

    public BcblacklistController(BcBlacklistService bcBlacklistService) {
        this.bcBlacklistService = bcBlacklistService;
    }

    @PostMapping("/add")
    public String bcBlacklistAdd(BcBlacklist bcBlacklist) {

        bcBlacklistService.insertBcBlacklist(bcBlacklist);

        return "redirect:/business/dashboard";

    }
}
