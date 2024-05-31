package com.watchlist.controller;

import com.watchlist.service.CustService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final CustService custService;

    public WatchlistController(CustService custService) {
        this.custService = custService;
    }

    @GetMapping("/status/{custId}")
    public String getWatchlistStatus(@PathVariable String custId) {
        return custService.findByCustId(custId).getWlfYn();
    }
}
