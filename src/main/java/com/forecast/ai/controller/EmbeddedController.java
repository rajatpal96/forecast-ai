package com.forecast.ai.controller;

import com.forecast.ai.dto.EmbeddedText;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/generate-embedding")
@AllArgsConstructor
public class EmbeddedController {

  private final EmbeddingModel embeddingModel;

  @PostMapping
  public ResponseEntity<?> generateEmbedding(@RequestBody EmbeddedText request) {
    return  new ResponseEntity<>(embeddingModel.embed(request.text()).content().vector(), HttpStatus.OK);
  }

}
