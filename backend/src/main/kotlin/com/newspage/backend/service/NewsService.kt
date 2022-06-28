package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class NewsService(val newsRepository: NewsRepository) {
    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 4
    }


    fun allNews(
        retrieveRequestDto: RetrieveRequestDto = RetrieveRequestDto(
            DEFAULT_PAGE,
            DEFAULT_PAGE_SIZE
        )
    ): List<News> {
        val pageable =
            retrieveRequestDto.let { PageRequest.of(it.page, retrieveRequestDto.size) }

        return newsRepository.findAll(pageable).toList()
    }

    fun searchNews(searchRequestDto: SearchRequestDto): List<News> {

        val pageable = searchRequestDto.retrieve?.let { PageRequest.of(it.page, it.size) } ?: PageRequest.of(
            DEFAULT_PAGE, DEFAULT_PAGE_SIZE
        )

        return newsRepository.findByTitleOrContent(
            searchRequestDto.title,
            searchRequestDto.content,
            pageable
        ).toList()
    }

    fun getNews(id: Int): News {
        return newsRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "News with id $id not found")
        }
    }


}



