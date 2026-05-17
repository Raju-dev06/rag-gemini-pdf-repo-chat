package com.practice.raggeminiworking.controller;

import com.practice.raggeminiworking.dto.RepoRequest;
import com.practice.raggeminiworking.service.RepoRagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repo")
@CrossOrigin
public class GitHubController {

    private final RepoRagService repoRagService;

    public GitHubController(RepoRagService repoRagService) {
        this.repoRagService = repoRagService;
    }

    @PostMapping("/upload")
    public String uploadRepository(@RequestBody RepoRequest request) {

        repoRagService.processRepository(
                request.getRepoUrl(),
                request.getSessionId()
        );

        return "Repository processed successfully";
    }
}