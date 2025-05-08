package net.clush.search.service;

import net.clush.search.dto.SearchRequest;
import net.clush.search.dto.SearchResponse;

public interface SearchService {

	SearchResponse SearchQuery (SearchRequest request);
}

