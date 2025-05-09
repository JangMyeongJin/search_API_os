package net.clush.search.dto;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class PropertiesLoder {
	
	private String[] indexes = {};
	
	@Value("${properties.file.path}")
	private String filePath;
	
	@Value("${properties.file.name}")
	private String fileName;
	
	@PostConstruct
    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream is = new FileInputStream("config/search-config.json");

        JsonNode root = mapper.readTree(is);
    }

}
