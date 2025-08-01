package com.forecast.ai.service;

import com.forecast.ai.dto.SunTimes;
import com.forecast.ai.exceptions.BadRequestException;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SunriseSunsetService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final static String OPEN_MOTO_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=sunrise,sunset&timezone=%s";

    @Tool("This method will fetch next 10 days Sunrise and sunset time")
    public String getSunTimes(@P("it is a latitude of given city") double lat,
                                @P("it is a longitude of given city") double lon,
                                @P("It's is timezone of the city , if not passed default is IST") String tz) {
        String url = String.format(OPEN_MOTO_URL, lat, lon, tz);
        return restTemplate.getForObject(url, String.class);
    }

}


