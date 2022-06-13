package com.newspage.backend.controller

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/news")
class NewsController(val newsRepository: NewsRepository) {

    @RequestMapping
    fun getAllNews(): List<News> {
        return newsRepository.findAll().toList()
    }

    @PostMapping
    fun addNews(@RequestBody news: News): News {
        return newsRepository.save(news)
    }

    @DeleteMapping
    fun deleteNews(id: String) = newsRepository.deleteById(id)


}
