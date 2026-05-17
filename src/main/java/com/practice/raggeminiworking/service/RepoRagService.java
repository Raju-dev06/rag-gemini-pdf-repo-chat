package com.practice.raggeminiworking.service;

import com.practice.raggeminiworking.model.VectorChunk;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
public class RepoRagService {

    private final GitHubService gitHubService;
    private final CodeChunkService chunkService;
    private final EmbeddingService embeddingService;
    private final VectorStoreService vectorStoreService;

    public RepoRagService(
            GitHubService gitHubService,
            CodeChunkService chunkService,
            EmbeddingService embeddingService,
            VectorStoreService vectorStoreService
    ) {
        this.gitHubService = gitHubService;
        this.chunkService = chunkService;
        this.embeddingService = embeddingService;
        this.vectorStoreService = vectorStoreService;
    }
//
//    public void processRepository(String repoUrl, String sessionId) {
//
//        try {
//
//            File repoDir = gitHubService.cloneRepository(repoUrl);
//
//            List<File> files = gitHubService.getCodeFiles(repoDir);
//
//            for (File file : files) {
//
//                String content = Files.readString(file.toPath());
//
//                List<String> chunks = chunkService.chunkText(content);
//
//                for (String chunkText : chunks) {
//
//                    List<Double> embedding =
//                            embeddingService.createEmbedding(chunkText);
//
//                    VectorChunk chunk = new VectorChunk();
//
//                    chunk.setSessionId(sessionId);
//                    chunk.setSourceType("github");
//                    chunk.setFileName(file.getName());
//                    chunk.setText(chunkText);
//                    chunk.setEmbedding(embedding);
//
//                    vectorStoreService.storeChunk(chunk);
//                }
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
public void processRepository(String repoUrl, String sessionId) {

    try {

        System.out.println("Starting repository processing...");

        File repoDir = gitHubService.cloneRepository(repoUrl);

        System.out.println("Repository cloned successfully.");

        List<File> files = gitHubService.getCodeFiles(repoDir);

        System.out.println("Total files found: " + files.size());

        for (File file : files) {

            System.out.println("Processing file: " + file.getName());

            String content = Files.readString(file.toPath());

            List<String> chunks = chunkService.chunkText(content);

            System.out.println("Chunks created: " + chunks.size());

            for (String chunkText : chunks) {

                System.out.println("Generating embedding...");

                List<Double> embedding =
                        embeddingService.createEmbedding(chunkText);

                System.out.println(
                        "Embedding generated. Size: "
                                + embedding.size()
                );

                VectorChunk chunk = new VectorChunk();

                chunk.setSessionId(sessionId);
                chunk.setSourceType("github");
                chunk.setFileName(file.getName());
                chunk.setText(chunkText);
                chunk.setEmbedding(embedding);

                vectorStoreService.storeChunk(chunk);

                System.out.println("Chunk stored in database.");
            }
        }

        System.out.println("Repository processing completed.");

    } catch (Exception e) {

        e.printStackTrace();

        throw new RuntimeException(e);
    }
}

}