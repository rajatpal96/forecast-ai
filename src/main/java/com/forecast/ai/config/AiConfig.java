package com.forecast.ai.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;

@Configuration
public class AiConfig {

    @Value("${langchain4j.azure-open-ai.api-key}")
    private String apiKey;

    @Value("${langchain4j.azure-open-ai.endpoint}")
    private String apiUrl;

    @Value("${langchain4j.azure-open-ai.deployment-name}")
    private String deploymentName;

    @Value("${langchain4j.qdrant.url}")
    private String qdrantUrl;

    @Value("${langchain4j.qdrant.key}")
    private String qdrantKey;

    private static final Collections.Distance distance = Collections.Distance.Cosine;

    @Bean
    public ChatModel chatModel() {
        return AzureOpenAiChatModel.builder()
                .apiKey(apiKey)
                .endpoint(apiUrl)
                .deploymentName(deploymentName)
                .build();
    }

    @Bean
    public QdrantEmbeddingStore qdrantEmbeddingStore() {
        return QdrantEmbeddingStore.builder()
                .collectionName("hotel_menu")
                .host(qdrantUrl)
                .port(6334)
                .apiKey(qdrantKey)
                .useTls(true)
                .build();
    }

    @Bean
    public QdrantClient qdrantConfig() throws ExecutionException, InterruptedException {
        QdrantClient client = new QdrantClient(
                QdrantGrpcClient.newBuilder(qdrantUrl, 6334, true).withApiKey(qdrantKey)
                        .build());
        if (!client.collectionExistsAsync("hotel_menu").get()) {
            Collections.CollectionOperationResponse response = client.createCollectionAsync(
                    "hotel_menu",
                    Collections.VectorParams.newBuilder().setDistance(distance).setSize(768).build()).get();
        }
        return client;
    }

    @Bean
    protected ContentRetriever getEmbeddingStoreContentRetriever(EmbeddingModel embeddingModel) {
        EmbeddingStore<TextSegment> embeddingStore =
                QdrantEmbeddingStore.builder()
                        .collectionName("hotel_menu")
                        .host(qdrantUrl)
                        .port(6334)
                        .apiKey(qdrantKey)
                        .useTls(true)
                        .build();
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2)
                .minScore(0.6)
                .build();
    }
}
