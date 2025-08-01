package com.epam.ai.controller;

import com.epam.ai.dto.Location;
import com.epam.ai.dto.SunResponse;
import com.epam.ai.dto.SunTimes;
import com.epam.ai.dto.SimpleContext;
import com.epam.ai.service.GeoCodingService;
import com.epam.ai.service.SunAssistant;
import com.epam.ai.service.SunriseSunsetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SunriseSunsetControllerTest {

    @Mock
    private GeoCodingService geoCodingService;
    @Mock
    private SunAssistant sunAssistant;
    @Mock
    private SunriseSunsetService sunriseSunsetService;

    @InjectMocks
    private SunriseSunsetController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTodaySunriseSunset_ValidCity() {
        Location location = new Location("Berlin", 52.5, 13.41, "IST");
        SunTimes sunTimes = new SunTimes("2025-08-01T08:55", "2025-08-01T00:30");
        SimpleContext context = new SimpleContext("Berlin", "2025-08-01T08:55", "2025-08-01T00:30", "IST");
        SunResponse aiResponse = new SunResponse("Berlin", "2025-08-01T08:55", "2025-08-01T00:30", "Tomorrow in Berlin, the sun will rise at 8:55 AM IST and set at 12:30 AM IST.");

        when(geoCodingService.lookup("Berlin")).thenReturn(location);
        when(sunriseSunsetService.getSunTimes(52.5, 13.41, "IST")).thenReturn(sunTimes);
        when(sunAssistant.askSun(context)).thenReturn(aiResponse);

        ResponseEntity<?> response = controller.getTodaySunriseSunset("Berlin");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(aiResponse, response.getBody());
    }

    @Test
    void testGetTodaySunriseSunset_InvalidCity() {
        when(geoCodingService.lookup("")).thenThrow(new IllegalArgumentException("Invalid city name"));
        assertThrows(IllegalArgumentException.class, () -> controller.getTodaySunriseSunset(""));
    }
}
