package com.practice.raggeminiworking.service;

import com.practice.raggeminiworking.model.VectorChunk;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class PdfService {

    private final ChunkService chunkService;
    private final VectorStoreService vectorStoreService;
    private final EmbeddingService embeddingService;

    public PdfService(ChunkService chunkService,
                      VectorStoreService vectorStoreService,
                      EmbeddingService embeddingService) {

        this.chunkService = chunkService;
        this.vectorStoreService = vectorStoreService;
        this.embeddingService = embeddingService;
    }

    public String processPdf(MultipartFile file) throws Exception {

        String sessionId = UUID.randomUUID().toString();

        PDDocument document = Loader.loadPDF(file.getBytes());

        PDFTextStripper stripper = new PDFTextStripper();

        String pdfText = stripper.getText(document);

        document.close();

        List<String> chunks = chunkService.splitText(pdfText, 500);

        for (String chunk : chunks) {

            List<Double> embedding = embeddingService.createEmbedding(chunk);

            VectorChunk vectorChunk =
                    new VectorChunk(null, sessionId, chunk, embedding);

            vectorStoreService.storeChunk(vectorChunk);
        }

        return sessionId;
    }
}