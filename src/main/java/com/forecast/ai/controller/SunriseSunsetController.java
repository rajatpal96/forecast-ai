package com.forecast.ai.controller;

import com.forecast.ai.service.SunAssistant;
import com.forecast.ai.service.SunriseSunsetService;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SunriseSunsetController {

    private final SunAssistant assistant;

    public SunriseSunsetController(SunAssistant assistant, SunriseSunsetService sunriseSunsetService, ChatModel model) {
        this.assistant = assistant;
    }


    @GetMapping("/sun-forecast")
    public ResponseEntity<?> getTodaySunriseSunset(@RequestParam(name = "city") String city) {
        return new ResponseEntity<>(assistant.askSubForecast(city), HttpStatus.OK);
    }
}
