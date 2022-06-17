package com.newspage.backend.controller

import com.newspage.backend.model.News
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import com.newspage.backend.service.NewsService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/news")
class NewsController(val newsService: NewsService) {

    @RequestMapping()
    fun getNews(@RequestBody(required = false) retrieveRequestDto: RetrieveRequestDto?): List<News> {
        return newsService.getNews(retrieveRequestDto)
    }

    @RequestMapping("/search")
    fun findNews(@RequestBody(required = false) searchRequestDto: SearchRequestDto): List<News> {
        return newsService.findNews(searchRequestDto)
    }


}
