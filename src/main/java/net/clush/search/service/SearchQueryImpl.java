package net.clush.search.service;

import org.springframework.stereotype.Service;

import net.clush.search.dto.SearchForm;
import net.clush.search.dto.SearchRequest;
import net.clush.search.dto.SearchResponse;

@Service
public class SearchQueryImpl implements SearchService {

	@Override
	public SearchResponse SearchQuery (SearchRequest request) {
		SearchForm searchForm = new SearchForm();
		
		searchForm.setQuery(request.getQuery());
		
		String[] indeies = {};
		String index = request.getIndex();
		
		if(index.equals("ALL")) {
			
		}
		
		
		return null;
	}

}
