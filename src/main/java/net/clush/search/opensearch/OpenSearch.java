package net.clush.search.opensearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.opensearch.action.search.MultiSearchRequest;
import org.opensearch.action.search.MultiSearchResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.clush.search.controller.SearchController;
import net.clush.search.dto.SearchFormDTO;
import net.clush.search.opensearch.builder.Builder;
import net.clush.search.opensearch.client.Client;

public class OpenSearch {
	private static final Logger log = LoggerFactory.getLogger(SearchController.class);
	
	private RestHighLevelClient CLIENT;

	private MultiSearchRequest multiSearchRequest;
	private MultiSearchResponse multiSearchResponse;
	private Map<String, Integer> searchIndexMap;
	
	protected final String USER;
	protected final String PASSWORD;
	protected final String URL;
	protected final boolean isHttps;
	
	public OpenSearch(String user, String password, String url) {
		
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("OpenSearch URL cannot be null or empty");
		}
		
		this.isHttps = url.toLowerCase().startsWith("https");
		this.USER = user;
		this.PASSWORD = password;
		this.URL = url;

		this.multiSearchRequest = new MultiSearchRequest();
		this.searchIndexMap = new HashMap<>();
	}
	
	// 검색 조건
	private String QUERY;
	private String[] INDEXES;
	private String[] SEARCHFIELD;
	private String[] HIGHLIGHTFIELD;
	private int SIZE;
	private int FROM;
	
	public void connection() {
		CLIENT = new Client(USER, PASSWORD, URL).Connection();
	}

	public MultiSearchResponse search(SearchFormDTO searchFormDTO) {
		
		setSearchForm(searchFormDTO);
		
		for(int i=0; i<INDEXES.length; i++) {
			SearchRequest searchRequest = searchRequest(INDEXES[i]);

			multiSearchRequest.add(searchRequest);
			searchIndexMap.put(INDEXES[i], i);
		}

		try {
			multiSearchResponse = CLIENT.msearch(multiSearchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return multiSearchResponse;
	}
	
	public SearchRequest searchRequest(String indexName) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		
		BoolQueryBuilder boolQuery = new BoolQueryBuilder();
		BoolQueryBuilder searchQuery = new BoolQueryBuilder();
		
		if(QUERY.equals("")) {
			searchQuery.must(QueryBuilders.matchAllQuery());
		}else {
			QueryBuilder simpleQueryBuilder = Builder.getSimpleQueryStringBuilder(QUERY, SEARCHFIELD);
			searchQuery.should(simpleQueryBuilder);
			// nested Query 추가해야됨
//			QueryBuilder simpleQueryBuilder = Builder.getSimpleQueryBuilder(QUERY, INDEXES);
//			
//			searchQuery.should(simpleQueryBuilder);
		}
		
		boolQuery.must(searchQuery);
		
		// 검색어 관련 queryBuilder는 boolQuery에 담은다음 query()로 넘기기
		searchSourceBuilder.query(boolQuery);

		if(HIGHLIGHTFIELD.length > 0) {
			HighlightBuilder highlightBuilder = Builder.getHighlightBuilder(HIGHLIGHTFIELD);
			searchSourceBuilder.highlighter(highlightBuilder);
		}
		
		// SearchRequest 생성
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(indexName).source(searchSourceBuilder);
		
		log.info("GET {}/_search {} ", searchRequest.indices()[0], searchRequest.source().toString());
		
		return searchRequest;
	}
	
	private void setSearchForm(SearchFormDTO searchFormDTO) {
		QUERY = searchFormDTO.getQuery();
		INDEXES = searchFormDTO.getIndexes();
		SEARCHFIELD= searchFormDTO.getSearchField();
		HIGHLIGHTFIELD = searchFormDTO.getHighlightField();
		SIZE = searchFormDTO.getSize();
		FROM = (searchFormDTO.getPage() - 1) * SIZE;
	}
	
	
}
