package com.watchlist.controller;

import com.watchlist.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private CustService custService;

    @GetMapping("/status/{custId}")
    public String getWatchlistStatus(@PathVariable String custId) {
        return custService.findByCustId(custId).getWlfYn();
    }
}
