package com.epam.ai.service;

import com.epam.ai.dto.SimpleContext;
import com.epam.ai.dto.SunResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface SunAssistant {
    @SystemMessage("You are a helpful assistant that gives sunrise/sunset info in a friendly way.")
    SunResponse askSun(SimpleContext ctx);
}
