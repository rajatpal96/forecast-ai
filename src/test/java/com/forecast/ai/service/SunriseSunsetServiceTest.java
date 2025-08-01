package com.forecast.ai.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SunriseSunsetServiceTest {

    @Test
    void testGetSunTimes_ReturnsApiResponse() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        SunriseSunsetService service = new SunriseSunsetService();
        double lat = 52.5;
        double lon = 13.41;
        String tz = "IST";
        String expectedResponse = "{\"sunrise\":\"2025-08-01T08:55\",\"sunset\":\"2025-08-01T00:30\"}";
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=sunrise,sunset&timezone=%s", lat, lon, tz);
        // Use reflection to inject mock RestTemplate
        try {
            java.lang.reflect.Field field = SunriseSunsetService.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(service, restTemplate);
        } catch (Exception e) {
            fail("Failed to inject mock RestTemplate");
        }
        when(restTemplate.getForObject(url, String.class)).thenReturn(expectedResponse);
        String result = service.getSunTimes(lat, lon, tz);
        assertEquals(expectedResponse, result);
    }
}

