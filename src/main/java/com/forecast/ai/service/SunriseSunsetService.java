package com.forecast.ai.service;

import com.forecast.ai.dto.SunTimes;
import com.forecast.ai.exceptions.BadRequestException;
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

    public SunTimes getSunTimes(double lat, double lon, String tz) {
        String url = String.format(OPEN_MOTO_URL, lat, lon, tz);

        var resp = restTemplate.getForObject(url, Map.class);
        return Optional.ofNullable(resp)
                .map(r -> r.get("daily"))
                .filter(d -> d instanceof Map)
                .map(d -> (Map<?, ?>) d)
                .map(daily -> {
                    List<String> sr = (List<String>) daily.get("sunrise");
                    List<String> ss = (List<String>) daily.get("sunset");
                    return Optional.ofNullable(sr)
                            .filter(list -> !list.isEmpty())
                            .flatMap(list -> Optional.ofNullable(ss)
                                    .filter(slist -> !slist.isEmpty())
                                    .map(slist ->
                                            new SunTimes(list.get(0), slist.get(0))
                                    )
                            );
                })
                .flatMap(Function.identity())
                .orElseThrow(() -> new BadRequestException("Sun times not available"));
    }
}


