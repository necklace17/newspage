package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import com.newspage.backend.search.SearchRequestDto
import org.springframework.stereotype.Service


@Service
class NewsService(val newsRepository: NewsRepository) {

    fun findNews(searchRequestDto: SearchRequestDto): List<News> {
        return newsRepository.findByTitleOrContent(searchRequestDto.title, searchRequestDto.content)
    }

}
