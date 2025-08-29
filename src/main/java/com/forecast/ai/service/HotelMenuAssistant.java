package com.forecast.ai.service;

import com.forecast.ai.dto.HotelAssistantResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface HotelMenuAssistant {

    @SystemMessage("You are a Hotel Menu assistant which help customer provide hotel menu according to user query and based on data searched from vector db if no menu item found give appropriate message and if someone says just greeting type query respond accordingly" +
            " and on greeting message don't response with this line, I couldn't find any specific menu items.")
    HotelAssistantResponse getHotelMenu(String query);

}
