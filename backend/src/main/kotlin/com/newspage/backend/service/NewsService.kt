package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
class NewsService(val newsRepository: NewsRepository) {
    companion object {
        val PAGE_REQUEST = PageRequest.of(0, 4)
    }

    fun getNews(retrieveRequestDto: RetrieveRequestDto?): List<News> {
        val pageable =
            retrieveRequestDto?.let { PageRequest.of(it.page, retrieveRequestDto.size) }
                ?: PAGE_REQUEST

        return newsRepository.findAll(pageable).toList()
    }

    fun findNews(searchRequestDto: SearchRequestDto): List<News> {

        val pageable = searchRequestDto.retrieve?.let { PageRequest.of(it.page, it.size) } ?: PAGE_REQUEST

        return newsRepository.findByTitleOrContent(
            searchRequestDto.title,
            searchRequestDto.content,
            pageable
        ).toList()
    }


}
