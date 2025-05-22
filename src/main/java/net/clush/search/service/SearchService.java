package net.clush.search.service;

import net.clush.search.dto.SearchFormDTO;

import org.springframework.beans.factory.annotation.Value;

import net.clush.search.dto.ResponseDTO;

public abstract class SearchService {
	
	@Value("${search.user}")
	protected String SEARCHUSER;
	
	@Value("${search.password}")
	protected String SEARCHPASSWORD;
	
	@Value("${search.url}")
	protected String SEARCHURL;

	public abstract ResponseDTO search (SearchFormDTO searchFormDTO);
	
	public abstract String getSearchType();
}

