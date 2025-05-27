package net.clush.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.clush.search.dto.PropertiesDTO;
import net.clush.search.dto.RequestDTO;
import net.clush.search.dto.ResponseDTO;
import net.clush.search.dto.SearchFormDTO;
import net.clush.search.service.SearchServiceFactory;
import net.clush.search.util.PropertiesUtil;
import net.clush.search.util.StringUtil;

@RestController
@RequestMapping("/search") 
@RequiredArgsConstructor
@Tag(name = "Search API")
public class SearchController {
	
	private static final Logger log = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchServiceFactory searchServiceFactory;
	
	private final PropertiesUtil propertiesUtil;
	
	@GetMapping("/query")
	@Operation(description = "검색어 기반으로 검색을 수행합니다.")
    public ResponseEntity<ResponseDTO> search(
            @ModelAttribute RequestDTO requestDTO) {

		log.info("Called [QuerySearch] : {}", requestDTO);
		
		SearchFormDTO searchFormDTO = new SearchFormDTO();
		PropertiesDTO propertiesDTO = propertiesUtil.getPropertiesDTO();
		String[] indexes = {};
		
		// query
		String reqQuery = requestDTO.getQuery();
		searchFormDTO.setQuery(reqQuery);
		
		// index
		String reqIndex = requestDTO.getIndex(); 
		if(propertiesDTO.getIndexes().indexOf(StringUtil.COMMA) > -1) {
			indexes = propertiesDTO.getIndexes().split(StringUtil.COMMA);
		}else {
			indexes = new String[] {propertiesDTO.getIndexes()};
		}
		
		if(reqIndex.equals("ALL")) {
			searchFormDTO.setIndexes(indexes);
		}else {
			indexes = new String[] {reqIndex};
			searchFormDTO.setIndexes(indexes);
		}
		
		// page
		int reqPage = requestDTO.getPage();
		searchFormDTO.setPage(reqPage);
		
		// size
		int reqSize = requestDTO.getSize();
		searchFormDTO.setSize(reqSize);

		for(String index : indexes) {
			PropertiesDTO.IndexConfig indexConfig = propertiesDTO.getIndexConfigs().get(index);
			
			// searchField
			String[] searchFieldList = indexConfig.getSearchField().split(StringUtil.COMMA);
			searchFormDTO.setSearchField(searchFieldList);
			
			// highlightField
			String[] highlightField = indexConfig.getHighlightField().split(StringUtil.COMMA);
 			 searchFormDTO.setHighlightField(highlightField);
			// searchFormDTO.setSort(indexConfig.getSort().split(StringUtil.COMMA));
		}
		
		log.info("searchFormDTO : ", searchFormDTO.getQuery());
		
        return ResponseEntity.ok(searchServiceFactory.getSearchService("query").search(searchFormDTO));
    }

}
