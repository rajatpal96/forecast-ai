package com.forecast.ai.service;

import com.forecast.ai.dto.SimpleContext;
import com.forecast.ai.dto.SunResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface SunAssistant {
    @SystemMessage("You are a helpful assistant that gives sunrise/sunset info in a friendly way.")
    SunResponse askSubForecast(SimpleContext ctx);
}
