package com.newspage.backend.controller

import com.newspage.backend.model.News
import com.newspage.backend.service.NewsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
internal class NewsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var newsService: NewsService

    private val endpoint = "/api/v1/news"
    private val newsId = 42
    private val news = News(newsId, "Good News", "here", "John Doe", "Lorem Ipsum")
    private fun assertProperSingleNewsResponse(request: ResultActions) {
        assertProperNewsResponse(request)
    }

    private fun assertProperMultipleNewsResponse(request: ResultActions) {
        assertProperNewsResponse(request, true)
    }

    private fun assertProperNewsResponse(request: ResultActions, listResponse: Boolean = false) {
        // Create empty string and append with [0] if listResponse is true
        val placeInResponse = if (listResponse) "$[0]" else "$"

        request
            .andExpect(status().isOk)
            .andExpect(jsonPath("$placeInResponse.id").value(newsId))
            .andExpect(jsonPath("$placeInResponse.title").value(news.title))
            .andExpect(jsonPath("$placeInResponse.publication").value(news.publication))
            .andExpect(jsonPath("$placeInResponse.author").value(news.author))
            .andExpect(jsonPath("$placeInResponse.content").value(news.content))
    }


    @Test
    fun searchNewsWithoutSearchString() {
        every { newsService.latestNews(null, null) } returns listOf(news)
        assertProperMultipleNewsResponse(mockMvc.perform(get("$endpoint/search")))
    }

    @Test
    fun searchNewsWithSearchString() {
        val searchString = "Lorem"
        every { newsService.searchNews(searchString, null, null) } returns listOf(news)
        assertProperMultipleNewsResponse(
            mockMvc.perform(get("$endpoint/search").queryParam("searchString", searchString))
        )
    }


    @Test
    fun searchNewsWithPageable() {
        val searchString = "Lorem"
        val pageParameter = 2
        val pageSizeParameter = 42

        every { newsService.searchNews(searchString, pageParameter, pageSizeParameter) } returns listOf(news)
        assertProperMultipleNewsResponse(
            mockMvc.perform(
                get("$endpoint/search")
                    .queryParam("searchString", searchString)
                    .queryParam("page", pageParameter.toString())
                    .queryParam("size", pageSizeParameter.toString())
            )
        )

    }

    @Test
    fun latestNewsWithPageable() {
        val pageParameter = 2
        val pageSizeParameter = 42
        every { newsService.latestNews(pageParameter, pageSizeParameter) } returns listOf(news)
        assertProperMultipleNewsResponse(
            mockMvc.perform(
                get(endpoint)
                    .queryParam("page", pageParameter.toString())
                    .queryParam("size", pageSizeParameter.toString())
            )
        )
    }

    @Test
    fun latestNewsWithoutPageable() {
        every { newsService.latestNews(null, null) } returns listOf(news)
        assertProperMultipleNewsResponse(mockMvc.perform(get(endpoint)))
    }

    @Test
    fun getNews() {
        every { newsService.getNews(newsId) } returns news
        assertProperSingleNewsResponse(mockMvc.perform(get("$endpoint/$newsId")))
    }
}
