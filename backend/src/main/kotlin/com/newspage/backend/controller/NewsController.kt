package com.newspage.backend.controller

import com.newspage.backend.model.News
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import com.newspage.backend.service.NewsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/news")
class NewsController(val newsService: NewsService) {

    @RequestMapping()
    fun allNews(@RequestBody(required = false) retrieveRequestDto: RetrieveRequestDto?): List<News> {
        return retrieveRequestDto?.let { newsService.allNews(it) } ?: newsService.allNews()
    }

    @RequestMapping("/search")
    fun searchNews(
        @RequestParam(required = false, defaultValue = "") title: String,
        @RequestParam(required = false, defaultValue = "") content: String,
        @RequestParam(required = false, defaultValue = "0") page: Int = 0,
        @RequestParam(required = false, defaultValue = "4") size: Int = 5,
    ): List<News> {
        if (title.isEmpty() && content.isEmpty()) {
            return newsService.allNews()
        }
        val searchRequestDto = SearchRequestDto(title, content, RetrieveRequestDto(page, size))
        return newsService.searchNews(searchRequestDto)
    }


    @RequestMapping("/{id}")
    fun getNews(@PathVariable id: Int): News {
        return newsService.getNews(id)
    }


}
