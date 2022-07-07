package com.newspage.backend.repository

import com.newspage.backend.model.News
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

interface NewsRepository : ElasticsearchRepository<News, String> {

    override fun findAll(pageable: Pageable): Page<News>

    fun searchNewsByTitleContainingOrContentContainingOrAuthorContaining(
        title: String,
        content: String,
        author: String,
        pageable: Pageable
    ): Page<News>

    fun findById(id: Int): Optional<News>

}
