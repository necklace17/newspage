package com.newspage.backend.repository

import com.newspage.backend.model.News
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface NewsRepository: ElasticsearchRepository<News, String> {
}
