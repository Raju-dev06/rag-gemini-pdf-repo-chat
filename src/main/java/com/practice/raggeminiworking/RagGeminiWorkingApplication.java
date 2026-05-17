package com.practice.raggeminiworking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.practice.raggeminiworking.repository.ChunkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
@SpringBootApplication
public class RagGeminiWorkingApplication {
    @Autowired
    private ChunkRepository chunkRepository;
    public static void main(String[] args) {
        SpringApplication.run(RagGeminiWorkingApplication.class, args);
    }
    @PostConstruct
    public void clearDatabase() {

        chunkRepository.deleteAllChunks();
    }

}
