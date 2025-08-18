package com.forecast.ai.config;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AiConfig {

    @Value("${langchain4j.azure-open-ai.api-key}")
    private String apiKey;

    @Value("${langchain4j.azure-open-ai.endpoint}")
    private String apiUrl;

    @Value("${langchain4j.azure-open-ai.deployment-name}")
    private String deploymentName;

    @Bean
    public ChatModel chatModel() {
        return AzureOpenAiChatModel.builder()
                .apiKey(apiKey)
                .endpoint(apiUrl)
                .deploymentName(deploymentName)
                .build();
    }

   /* @Bean
    @Primary
    public AzureOpenAiEmbeddingModel embeddingModel() {
        return AzureOpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .endpoint(apiUrl)
                .deploymentName(deploymentName)
                .logRequestsAndResponses(true)
                .build();
    }*/
}
