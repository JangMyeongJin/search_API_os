package net.clush.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SearchController {
	
	private static final Logger log = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchServiceFactory searchServiceFactory;
	
	private final PropertiesUtil propertiesUtil;
	
	@GetMapping("/query")
    public ResponseEntity<ResponseDTO> search(@ModelAttribute RequestDTO requestDTO) {
		
		SearchFormDTO searchFormDTO = new SearchFormDTO();
		PropertiesDTO propertiesDTO = propertiesUtil.getPropertiesDTO();
		String[] indexes = {};
		
		if(propertiesDTO.getIndexes().indexOf(StringUtil.COMMA) > -1) {
			indexes = propertiesDTO.getIndexes().split(StringUtil.COMMA);
		}
		
		String reqQuery = requestDTO.getQuery();
		searchFormDTO.setQuery(reqQuery);
		
		String reqIndex = requestDTO.getIndex(); 
		
		if(reqIndex.equals("ALL")) {
			searchFormDTO.setIndexes(indexes);
		}else {
			indexes = new String[] {reqIndex};
			searchFormDTO.setIndexes(indexes);
		}
		
        return ResponseEntity.ok(searchServiceFactory.getSearchService("query").search(searchFormDTO));
    }

}
