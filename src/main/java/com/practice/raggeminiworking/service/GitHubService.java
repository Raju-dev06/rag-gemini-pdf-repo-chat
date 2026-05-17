//package com.practice.raggeminiworking.service;
//
//import org.eclipse.jgit.api.Git;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class GitHubService {
//
//    private static final String CLONE_DIR = "repos";
//
//    public File cloneRepository(String repoUrl) throws Exception {
//
//        String repoName = repoUrl.substring(repoUrl.lastIndexOf("/") + 1)
//                .replace(".git", "");
//
//        File repoDir = new File(CLONE_DIR + "/" + repoName);
//
//        if (repoDir.exists()) {
//            deleteDirectory(repoDir.toPath());
//        }
//
//        Git.cloneRepository()
//                .setURI(repoUrl)
//                .setDirectory(repoDir)
//                .call();
//
//        return repoDir;
//    }
//
//    public List<File> getCodeFiles(File repoDir) throws IOException {
//
//        List<File> files = new ArrayList<>();
//        Files.walk(repoDir.toPath())
//                .filter(Files::isRegularFile)
//                .forEach(path -> {
//
//                    String fileName = path.toString();
//
//                    if (
//                            fileName.endsWith(".java") ||
//                                    fileName.endsWith(".js") ||
//                                    fileName.endsWith(".ts") ||
//                                    fileName.endsWith(".jsx") ||
//                                    fileName.endsWith(".tsx") ||
//                                    fileName.endsWith(".py") ||
//                                    fileName.endsWith(".md")
//                    ) {
//                        files.add(path.toFile());
//                    }
//                });
//
//        return files;
//    }
//
//    private void deleteDirectory(Path path) throws IOException {
//
//        Files.walk(path)
//                .sorted((a, b) -> b.compareTo(a))
//                .forEach(p -> {
//                    try {
//                        Files.delete(p);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//}

package com.practice.raggeminiworking.service;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private static final String CLONE_DIR = "repos";

    public File cloneRepository(String repoUrl) {

        try {

            String repoName =
                    repoUrl.substring(repoUrl.lastIndexOf("/") + 1)
                            .replace(".git", "");

            File parentDir = new File(CLONE_DIR);

            if (!parentDir.exists()) {

                parentDir.mkdirs();
            }

            // Create unique folder every upload
            File repoDir = new File(
                    CLONE_DIR + "/"
                            + repoName
                            + "_"
                            + System.currentTimeMillis()
            );

            System.out.println("Cloning repository...");

            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(repoDir)
                    .call();

            System.out.println("Repository cloned successfully.");

            return repoDir;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public List<File> getCodeFiles(File repoDir)
            throws IOException {

        List<File> files = new ArrayList<>();

        Files.walk(repoDir.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {

                    String fileName = path.toString();

                    if (
                            fileName.endsWith(".java") ||
                                    fileName.endsWith(".js") ||
                                    fileName.endsWith(".ts") ||
                                    fileName.endsWith(".jsx") ||
                                    fileName.endsWith(".tsx") ||
                                    fileName.endsWith(".py") ||
                                    fileName.endsWith(".md")
                    ) {

                        files.add(path.toFile());
                    }
                });

        return files;
    }
}