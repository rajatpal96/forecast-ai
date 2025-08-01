package com.forecast.ai.service;

import com.forecast.ai.dto.Location;
import com.forecast.ai.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GeoCodingService {
    public static final String OPEN_MOTO_URL_GEO = "https://geocoding-api.open-meteo.com/v1/search?name=";
    private final RestTemplate rt = new RestTemplate();

    public Location lookup(String city) {
        String url = OPEN_MOTO_URL_GEO + UriUtils.encode(city, StandardCharsets.UTF_8) + "&count=1";
        @SuppressWarnings("unchecked")
        Map<String, Object> resp = rt.getForObject(url, Map.class);

        List<?> results = (List<?>) resp.get("results");

        return Optional.ofNullable(results)
                .filter(r -> !r.isEmpty())
                .map(r -> (Map<?, ?>) r.get(0))
                .map(loc -> new Location(
                        (String) loc.get("name"),
                        ((Number) loc.get("latitude")).doubleValue(),
                        ((Number) loc.get("longitude")).doubleValue(),
                        (String) loc.get("timezone")
                ))
                .orElseThrow(() -> new BadRequestException("City not found"));
    }

}
