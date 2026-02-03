package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*") // Allow requests from any origin
public class ImageUploadController {

    private static final Path DIR = Paths.get("uploads");
    private final ProductImageDAO dao = new ProductImageDAO();
    
    // Allowed image types
    private static final List<String> ALLOWED_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );
    
    // Maximum file size (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // Ensure uploads directory exists
    static {
        try {
            if (Files.notExists(DIR)) {
                Files.createDirectories(DIR);
            }
        } catch (IOException e) {
            System.err.println("Failed to create uploads directory: " + e.getMessage());
        }
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> upload(@PathVariable int productId,
                                    @RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }
            
            if (file.getSize() > MAX_FILE_SIZE) {
                return ResponseEntity.badRequest().body("File size exceeds 5MB limit");
            }
            
            if (!ALLOWED_TYPES.contains(file.getContentType())) {
                return ResponseEntity.badRequest().body("Invalid file type. Only images allowed.");
            }

            // Create directory if it doesn't exist
            if (Files.notExists(DIR)) {
                Files.createDirectories(DIR);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                originalFilename = "image";
            }
            
            // Clean filename and add timestamp
            String cleanFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
            String filename = Instant.now().toEpochMilli() + "_" + cleanFilename;
            
            // Save file
            Path filePath = DIR.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save to database
            String url = "/uploads/" + filename;
            ProductImage img = new ProductImage();
            img.setProductId(productId);
            img.setImageUrl(url);
            
            int imageId = dao.addImage(img);

            // Return success response
            return ResponseEntity.ok().body(new ImageUploadResponse(imageId, url, "Image uploaded successfully"));
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload error: " + e.getMessage());
        }
    }
    
    // Response class for consistent JSON structure
    public static class ImageUploadResponse {
        private int imageId;
        private String url;
        private String message;
        
        public ImageUploadResponse(int imageId, String url, String message) {
            this.imageId = imageId;
            this.url = url;
            this.message = message;
        }
        
        // Getters
        public int getImageId() { return imageId; }
        public String getUrl() { return url; }
        public String getMessage() { return message; }
    }
}
