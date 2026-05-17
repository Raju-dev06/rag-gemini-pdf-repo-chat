package com.practice.raggeminiworking.controller;

import com.practice.raggeminiworking.model.VectorChunk;
import com.practice.raggeminiworking.service.EmbeddingService;
import com.practice.raggeminiworking.service.GeminiService;
import com.practice.raggeminiworking.service.VectorStoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final GeminiService geminiService;
    private final VectorStoreService vectorStoreService;
    private final EmbeddingService embeddingService;

    public ChatController(GeminiService geminiService,
                          VectorStoreService vectorStoreService,
                          EmbeddingService embeddingService) {

        this.geminiService = geminiService;
        this.vectorStoreService = vectorStoreService;
        this.embeddingService = embeddingService;
    }

    @GetMapping
    public String ask(@RequestParam String sessionId,
                      @RequestParam String question) {

        List<Double> questionEmbedding =
                embeddingService.createEmbedding(question);

        List<VectorChunk> chunks =
                vectorStoreService.searchSimilarChunks(sessionId, questionEmbedding, 5);

        String context = chunks.stream()
                .map(VectorChunk::getText)
                .reduce("", (a, b) -> a + "\n" + b);

        return geminiService.askGeminiWithContext(context, question);
    }
}