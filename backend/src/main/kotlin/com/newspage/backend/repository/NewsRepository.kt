package com.newspage.backend.repository

import com.newspage.backend.model.News
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface NewsRepository : ElasticsearchRepository<News, String> {

    override fun findAll(pageable: Pageable): Page<News>

    fun findByTitleOrContent(title: String, content: String, pageable: Pageable): List<News>

}
