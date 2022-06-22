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
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 4
    }

    fun getNews(
        retrieveRequestDto: RetrieveRequestDto = RetrieveRequestDto(
            DEFAULT_PAGE,
            DEFAULT_PAGE_SIZE
        )
    ): List<News> {
        val pageable =
            retrieveRequestDto.let { PageRequest.of(it.page, retrieveRequestDto.size) }

        return newsRepository.findAll(pageable).toList()
    }

    fun findNews(searchRequestDto: SearchRequestDto): List<News> {

        val pageable = searchRequestDto.retrieve?.let { PageRequest.of(it.page, it.size) } ?: PageRequest.of(
            DEFAULT_PAGE, DEFAULT_PAGE_SIZE
        )

        return newsRepository.findByTitleOrContent(
            searchRequestDto.title,
            searchRequestDto.content,
            pageable
        ).toList()
    }


}
