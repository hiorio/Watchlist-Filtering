package com.watchlist.controller;

import com.watchlist.service.CustService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
@AllArgsConstructor
public class WatchlistController {
    // @Autowired 잘 안씀. 생성자 주입을 지향함.
    private final CustService custService;

    @GetMapping("/status/{custId}")
    public String getWatchlistStatus(@PathVariable String custId) {
        // getWlfYn() 까지 서비스 레이어에서 하면 좋을 듯
        return custService.findByCustId(custId).getWlfYn();
    }
}
