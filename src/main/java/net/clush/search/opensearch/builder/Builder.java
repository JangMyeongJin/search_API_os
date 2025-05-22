package net.clush.search.opensearch.builder;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.index.query.Operator;
import org.opensearch.index.query.QueryBuilder;
import org.opensearch.index.query.QueryBuilders;

import net.clush.search.util.StringUtil;


public class Builder {

	public static QueryBuilder getSimpleQueryBuilder(String query, String[] searchField) {
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
}
