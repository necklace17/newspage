package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class NewsService(val newsRepository: NewsRepository) {
    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 10
    }

    fun createPageable(page: Int?, size: Int?): PageRequest =
        PageRequest.of(page ?: DEFAULT_PAGE, size ?: DEFAULT_PAGE_SIZE)


    fun latestNews(page: Int?, size: Int?): List<News> {
        val pageable = createPageable(page, size)

        return newsRepository.findAll(pageable).toList()
    }

    fun searchNews(searchString: String, page: Int?, size: Int?): List<News> {
        val pageable = createPageable(page, size)

        return newsRepository.searchNewsByTitleContainingOrContentContainingOrAuthorContaining(
            searchString,
            searchString,
            searchString,
            pageable
        )
            .toList()
    }

    fun getNews(id: Int): News = newsRepository.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "News with id $id not found")
    }

}



