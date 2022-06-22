package com.newspage.backend.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.newspage.backend.model.News
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import com.newspage.backend.service.NewsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest
internal class NewsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var newsService: NewsService
    private val mapper = jacksonObjectMapper()

    private val endpoint = "/api/v1/news"

    private val newsId = "42"
    private val news = News(newsId, "Good News", "here", "John Doe", "Lorem Ipsum")

    @Test
    fun getNewsWithoutRetrieveRequestDto() {
        every { newsService.getNews() } returns listOf(news)
        mockMvc.perform(
            get(endpoint).contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("\$.[0].id").value(newsId))
    }

    @Test
    fun getNewsWithRetrieveRequestDto() {
        val retrieveRequestDto = RetrieveRequestDto(0, 1)
        every { newsService.getNews(retrieveRequestDto) } returns listOf(news)
        mockMvc.perform(
            get(endpoint).content(
                mapper.writeValueAsString(retrieveRequestDto)
            ).contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("\$.[0].id").value(newsId))
    }


    @Test
    fun findNews() {
        val searchRequestDto = SearchRequestDto("hi", "some content", RetrieveRequestDto(0, 1))
        every { newsService.findNews(searchRequestDto) } returns listOf(news)
        mockMvc.perform(
            get("$endpoint/search").content(
                mapper.writeValueAsString(searchRequestDto)
            ).contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("\$.[0].id").value(newsId))
    }
}
