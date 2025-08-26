package com.forecast.ai.service;

import com.forecast.ai.dto.HotelMenu;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class HotelMenuService {

    private final EmbeddingModel embeddingModel;

    private final QdrantEmbeddingStore qdrantEmbeddingStore;

    public void addHotelMenu(HotelMenu menu){
        Map<String,Object> metaDataMap = new HashMap<>();
        metaDataMap.put("dishName",menu.dishName());
        metaDataMap.put("category",menu.category());;
        metaDataMap.put("description",menu.description());
        metaDataMap.put("price",menu.price());
        metaDataMap.put("tags", String.join(",", menu.tags()));
        Metadata metadata = new Metadata(metaDataMap);
        TextSegment textSegment = new TextSegment(menu.dishName(),metadata);
        Embedding embedding = embeddingModel.embed(textSegment).content();
        qdrantEmbeddingStore.add(embedding,textSegment);

    }



   /* public Object searchFromVectorStore(String query,HotelMenu menu){
        Embedding embedding = embeddingModel.embed(query).content();

        Points.QueryPoints request = Points.QueryPoints.newBuilder()
                .setCollectionName("menu_collection")
                .setQuery(nearest(embedding.vector()))
                .setFilter(
                        Points.Filter.newBuilder()
                                .addMust(matchKeyword("category", "Main Course"))
                                .addMust(matchKeyword("tags", "vegetarian"))
                                .build()
                )
                .setLimit(5)
                .setWithPayload(io.qdrant.client.WithPayloadSelectorFactory.enable(true))
                .build();


       *//* RetrievalAugmentedGenerationChain ragChain = RetrievalAugmentedGenerationChain.builder()
                .chatLanguageModel(chatModel)
                .retriever(retriever)
                .build();

        String answer = ragChain.execute(userQuery);
        System.out.println(answer);*//*
        return null;
    }*/

}
