package com.forecast.ai.dto;

import java.util.List;

public record HotelMenu(String dishName, String category, String description, Double price, List<String> tags) {
}
