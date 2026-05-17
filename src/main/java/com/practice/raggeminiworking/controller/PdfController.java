package com.practice.raggeminiworking.controller;

import com.practice.raggeminiworking.service.PdfService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam MultipartFile file) throws Exception {

        String sessionId = pdfService.processPdf(file);

        Map<String, String> response = new HashMap<>();
        response.put("message", "PDF uploaded successfully");
        response.put("sessionId", sessionId);

        return response;
    }
}