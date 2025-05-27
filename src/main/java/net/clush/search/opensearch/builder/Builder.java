package net.clush.search.opensearch.builder;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.index.query.Operator;
import org.opensearch.index.query.QueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.fetch.subphase.highlight.HighlightBuilder;

import net.clush.search.util.StringUtil;


public class Builder {

	public static QueryBuilder getSimpleQueryStringBuilder(String query, String[] searchField) {
		Map<String, Float> searchFieldInfo = new HashMap<String, Float>();
		
		float boost = 1.0f;
		
		for(String field : searchField) {
			if(field.indexOf(StringUtil.SLASH) > -1) {
				String[] list = field.split(StringUtil.SLASH);
				boost = Float.parseFloat(list[1]);
				
				searchFieldInfo.put(list[0], boost);
			}else {
				searchFieldInfo.put(field, boost);
			}
		}
		
		Operator op = Operator.AND;
		QueryBuilder queryBuilder = QueryBuilders
								.simpleQueryStringQuery(query)
								.fields(searchFieldInfo)
								.defaultOperator(op)
								.autoGenerateSynonymsPhraseQuery(true);
		
		return queryBuilder;
	}

	public static HighlightBuilder getHighlightBuilder(String[] highlightField) {
		HighlightBuilder highlightBuilder = new HighlightBuilder();

		int highlightSize = 200;
		
		for(String field : highlightField) {
			HighlightBuilder.Field fieldBuilder = new HighlightBuilder.Field(field);

			fieldBuilder.fragmentSize(highlightSize);
			fieldBuilder.noMatchSize(highlightSize);
			highlightBuilder.field(fieldBuilder);
		}

		highlightBuilder.preTags("<span>");
		highlightBuilder.postTags("</span>");

		highlightBuilder.highlighterType("unified");
		highlightBuilder.fragmenter("scan");

		highlightBuilder.order(HighlightBuilder.Order.SCORE);

		return highlightBuilder;
	}
}
