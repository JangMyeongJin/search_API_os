package net.clush.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.clush.search.dto.SearchRequest;
import net.clush.search.dto.SearchResponse;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
	
	@GetMapping
    public ResponseEntity<SearchResponse> search(@ModelAttribute SearchRequest request) {
        return ResponseEntity.ok(searchService.search(request));
    }

}
