package project.techTalent.Network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dj9aqjclc",
            "api_key", "244275682663235",
            "api_secret", "JwCrXnQ5dhoB02Onaibhw4XY0dU",
            "secure", true
        ));
    }
} 