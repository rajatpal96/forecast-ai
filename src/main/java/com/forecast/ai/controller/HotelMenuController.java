package com.forecast.ai.controller;

import com.forecast.ai.dto.HotelMenu;
import com.forecast.ai.dto.ResponseMessage;
import com.forecast.ai.service.HotelMenuAssistant;
import com.forecast.ai.service.HotelMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
public class HotelMenuController {

    private final HotelMenuService menuService;
    private final HotelMenuAssistant assistant;

    public HotelMenuController(HotelMenuService menuService, HotelMenuAssistant assistant) {
        this.menuService = menuService;
        this.assistant = assistant;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHotelMenu(@RequestBody HotelMenu menu){
        menuService.addHotelMenu(menu);
        return new ResponseEntity<>(new ResponseMessage("Menu item added successfully!"), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getHotelMenu(@RequestParam String query){
        return new ResponseEntity<>(assistant.getHotelMenu(query).menuList(), HttpStatus.OK);
    }

}
