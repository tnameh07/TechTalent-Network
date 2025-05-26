package project.techTalent.Network.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileService {
    String uploadImage(String path, MultipartFile file) throws IOException;
    void deleteImage(String imageUrl) throws IOException;
} 