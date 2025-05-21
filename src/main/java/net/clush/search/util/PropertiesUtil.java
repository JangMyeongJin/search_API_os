package net.clush.search.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import net.clush.search.dto.SearchProperties;

@Component
public class PropertiesUtil {
	
	@Value("${properties.file.path}")
	private String filePath;
	
	@Value("${properties.file.name}")
	private String fileName;
	
	private SearchProperties searchProperties;
	
	@PostConstruct
	public void init() {
		try {
			searchProperties = load();
		} catch (Exception e) {
			// 초기화 실패 시 빈 객체 생성
			searchProperties = new SearchProperties();
			e.printStackTrace();
		}
	}
	
	public SearchProperties load() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			FileInputStream is = new FileInputStream(filePath + fileName);

			JsonNode rootNode = mapper.readTree(is);
        	String indexes = rootNode.get("indexes").asText();

			SearchProperties properties = new SearchProperties();
			properties.setIndexes(indexes);
			
			HashMap<String, SearchProperties.IndexConfig> indexConfigs = new HashMap<>();
			
			if(indexes.indexOf(FileUtil.COMMA) > -1) {
				String[] indexList = indexes.split(FileUtil.COMMA);
				
				for(String index : indexList) {
					SearchProperties.IndexConfig config = getIndexConfig(index, rootNode);
					indexConfigs.put(index, config);
				}
				
			}else {
				SearchProperties.IndexConfig config = getIndexConfig(indexes, rootNode);
				indexConfigs.put(indexes, config);
			}
			
			properties.setIndexConfigs(indexConfigs);
			
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Properties file load failed: " + filePath + fileName, e);
		}
	}

	public SearchProperties.IndexConfig getIndexConfig(String index, JsonNode rootNode) {
		JsonNode indexNode = rootNode.get(index);
		if (indexNode != null) {
			SearchProperties.IndexConfig config = new SearchProperties.IndexConfig();
			config.setSearchField(indexNode.get("searchField").asText());
			config.setHighlightField(indexNode.get("highlightField").asText());
			config.setSort(indexNode.get("sort").asText());
			return config;
		}
		return null;
	}

	public SearchProperties getSearchProperties() {
		return searchProperties;
	}
}
