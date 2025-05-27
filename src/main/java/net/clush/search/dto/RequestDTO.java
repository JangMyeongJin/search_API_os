package net.clush.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "검색 요청 DTO")
public class RequestDTO {
	@Schema(description = "검색어", example = "검색할 내용")
	private String query;
	@Schema(description = "검색할 인덱스 (ALL 또는 특정 인덱스)", example = "ALL")
	private String index = "ALL";
	@Schema(description = "페이지 번호", example = "1")
	private int page = 1;
	@Schema(description = "페이지당 결과 수", example = "10")
	private int size = 10;
}
