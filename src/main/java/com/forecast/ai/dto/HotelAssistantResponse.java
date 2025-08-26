package com.forecast.ai.dto;

import java.util.List;

public record HotelAssistantResponse(List<HotelMenu> menuList,String enhancedMessage) {
}
