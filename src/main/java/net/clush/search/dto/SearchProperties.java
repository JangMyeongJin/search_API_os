package net.clush.search.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class SearchProperties {
	private String indexes;
    private HashMap<String, IndexConfig> indexConfigs;
    
    @Data
    public static class IndexConfig {
        private String searchField;
        private String highlightField;
        private String sort;
    }
}
