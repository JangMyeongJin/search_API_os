package net.clush.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFormDTO {
	private String query;
	private String[] indexes;
	private String[] searchField;
	private String[] highlightField;
	private String[] sort;
	private int page;
	private int size;
}
