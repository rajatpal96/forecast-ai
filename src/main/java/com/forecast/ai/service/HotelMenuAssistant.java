package com.forecast.ai.service;

import com.forecast.ai.dto.HotelAssistantResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface HotelMenuAssistant {

    @SystemMessage("You are a Hotel Menu assistant which help customer provide hotel menu according to user query and based on data searched from vector db if no menu item found give appropriate message")
    HotelAssistantResponse getHotelMenu(String query);

}
