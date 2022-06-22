package com.newspage.backend.search

import com.fasterxml.jackson.annotation.JsonInclude

data class SearchRequestDto(
    val title: String = "",
    val content: String = "",
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val retrieve: RetrieveRequestDto? = null
)
