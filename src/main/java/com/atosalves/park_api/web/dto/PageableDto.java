package com.atosalves.park_api.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PageableDto<T>(List<T> content, boolean first, boolean last, @JsonProperty("page") int number, int size,
                @JsonProperty("pageElements") int numberOfElements, int totalPages, long totalElements) {

}
