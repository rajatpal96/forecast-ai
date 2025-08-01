package com.epam.ai.controller;

import com.epam.ai.dto.Location;
import com.epam.ai.dto.SimpleContext;
import com.epam.ai.dto.SunTimes;
import com.epam.ai.service.GeoCodingService;
import com.epam.ai.service.SunAssistant;
import com.epam.ai.service.SunriseSunsetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SunriseSunsetController {

    private final GeoCodingService geo;
    private final SunAssistant assistant;
    private final SunriseSunsetService sunriseSunsetService;

    public SunriseSunsetController(GeoCodingService geo, SunAssistant assistant, SunriseSunsetService sunriseSunsetService) {
        this.geo = geo;
        this.assistant = assistant;
        this.sunriseSunsetService = sunriseSunsetService;
    }


    @GetMapping("/sun-forecast")
    public ResponseEntity<?> getTodaySunriseSunset(@RequestParam(name = "city") String city) {
        Location loc = geo.lookup(city);
        SunTimes times = sunriseSunsetService.getSunTimes(loc.latitude(), loc.longitude(), loc.timezone());
        SimpleContext ctx = new SimpleContext(loc.name(), times.sunRise(), times.sunSet(), loc.timezone());
        return new ResponseEntity<>(assistant.askSubForecast(ctx), HttpStatus.OK);

    }
}
