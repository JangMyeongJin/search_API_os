package net.clush.search.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.clush.search.dto.SearchForm;
import net.clush.search.dto.SearchProperties;
import net.clush.search.dto.SearchRequest;
import net.clush.search.dto.SearchResponse;
import net.clush.search.util.FileUtil;
import net.clush.search.util.PropertiesUtil;

@Service
@RequiredArgsConstructor 
public class SearchQueryImpl implements SearchService {

	private final PropertiesUtil propertiesUtil;

	@Override
	public SearchResponse SearchQuery (SearchRequest request) {
		
		SearchForm searchForm = new SearchForm();
		SearchProperties properties = propertiesUtil.getSearchProperties();
		String[] indexes = {};
		
		if(properties.getIndexes().indexOf(FileUtil.COMMA) > -1) {
			indexes = properties.getIndexes().split(FileUtil.COMMA);
		}
		
		String reqQuery = request.getQuery();
		searchForm.setQuery(reqQuery);
		
		String reqIndex = request.getIndex();
		
		if(reqIndex.equals("ALL")) {
			searchForm.setIndexes(indexes);
		}else {
			indexes = new String[] {reqIndex};
			searchForm.setIndexes(indexes);
		}
		
		
		return null;
	}

}
