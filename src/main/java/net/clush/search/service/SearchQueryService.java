package net.clush.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opensearch.action.search.MultiSearchResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.clush.search.dto.ResponseDTO;
import net.clush.search.dto.SearchFormDTO;
import net.clush.search.opensearch.OpenSearch;
import net.clush.search.util.PropertiesUtil;

@Service
@RequiredArgsConstructor 
public class SearchQueryService extends SearchService {
	private String SEARCHTYPE = "query";

	public String getSearchType() {
		return SEARCHTYPE;
	}
	
	@Override
	public ResponseDTO search(SearchFormDTO searchFormDTO) {
		try {
			OpenSearch openSearch = new OpenSearch(SEARCHUSER, SEARCHPASSWORD, SEARCHURL);
			openSearch.connection();
			
			List<Map<String, Object>> results = new ArrayList<>();
			
			MultiSearchResponse multiSearchResponse = openSearch.search(searchFormDTO);
			
			if (multiSearchResponse == null) {
				return new ResponseDTO("OpenSearch connection failed", 500, null);
			}
			
			MultiSearchResponse.Item[] responses = multiSearchResponse.getResponses();

			for (MultiSearchResponse.Item response : responses) {
				if (response.getResponse() != null) {
					SearchResponse searchResponse = response.getResponse();
					searchResponse.getHits().forEach(hit -> {
						Map<String, Object> sourceAsMap = hit.getSourceAsMap();
						results.add(sourceAsMap);
					});
				}
			}
			
			return new ResponseDTO("success", 200, results);
			
		} catch (IllegalArgumentException e) {
			return new ResponseDTO("Invalid search parameters: " + e.getMessage(), 400, null);
		} catch (Exception e) {
			return new ResponseDTO("Internal server error: " + e.getMessage(), 500, null);
		}
	}
}
