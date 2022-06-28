package com.newspage.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "news")
data class News(
    @Id
    val id: Int? = null,
    val title: String,
    val publication: String,
    val author: String,
    val content: String,
)

