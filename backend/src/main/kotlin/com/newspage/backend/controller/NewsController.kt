package com.newspage.backend.controller

import com.newspage.backend.model.News
import com.newspage.backend.service.NewsService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/news")
class NewsController(val newsService: NewsService) {

    @RequestMapping()
    fun latestNews(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<News> = newsService.latestNews(page, size)


    @RequestMapping("/search")
    fun searchNews(
        @RequestParam(required = false) searchString: String? = "",
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<News> {
        if (searchString.isNullOrEmpty()) {
            return newsService.latestNews(page, size)
        }
        return newsService.searchNews(searchString, page, size)
    }

    @RequestMapping("/{id}")
    fun getNews(@PathVariable id: Int): News = newsService.getNews(id)


}
