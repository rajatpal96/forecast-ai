package com.forecast.ai.service;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final ContentRetriever contentRetriever;

    public RagService(EmbeddingModel embeddingModel, ContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    @Tool("this method will retrieve hotel menu from qdrant vector store apply if require into query")
    public List<Content> searchFromHotelMenu(@P("always convert it into english if another language data is fed convert it based on language param") String query ,
                                             @P("this is the language in which data needs to search , default is english. Language has to identify based on query") String language){
        Query qdrantQuery = Query.from(query);
        return contentRetriever.retrieve(qdrantQuery);
    }

   /* @Tool("This method convert query to english")
    public String queryInEnglish(@P("translate the query to english if other other than than english is identify") String query){
        return query;
    }
*/
}
