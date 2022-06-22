package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import com.newspage.backend.search.RetrieveRequestDto
import com.newspage.backend.search.SearchRequestDto
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class NewsServiceTest {

    private val newsRepository = mockk<NewsRepository>()
    private val newsService = NewsService(newsRepository)
    private val newsId = "42"
    private val news = News(newsId, "Good News", "here", "John Doe", "Lorem Ipsum")


    @Test
    fun getNewsWithRequestDto() {
        val page = 1
        val size = 4
        Assertions.assertNotEquals(setOf(page, size), setOf(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE))

        val retrieveRequestDto = RetrieveRequestDto(page, size)
        val pageable = PageRequest.of(page, size)
        every { newsRepository.findAll(pageable) } returns PageImpl(listOf(news))
        val newsResult = newsService.getNews(retrieveRequestDto)
        Assertions.assertEquals(newsResult[0], news)
    }

    @Test
    fun getNewsWithoutRequestDto() {
        val pageable = PageRequest.of(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE)
        every { newsRepository.findAll(pageable) } returns PageImpl(listOf(news))
        newsService.getNews()
    }

    @Test
    fun findNewsWithRetrieve() {
        val page = 1
        val size = 4
        Assertions.assertNotEquals(setOf(page, size), setOf(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE))
        val pageable = PageRequest.of(page, size)

        val retrieveRequestDto = RetrieveRequestDto(page, size)
        val searchRequestDto = SearchRequestDto("hi", "some Content", retrieveRequestDto)

        every {
            newsRepository.findByTitleOrContent(
                searchRequestDto.title,
                searchRequestDto.content,
                pageable
            )
        } returns listOf(news)
        val newsResult = newsService.findNews(searchRequestDto)
        Assertions.assertEquals(newsResult[0], news)
    }

    @Test
    fun findNewsWithoutRetrieve() {
        val searchRequestDto = SearchRequestDto("hi", "some Content")
        val pageable = PageRequest.of(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE)
        every {
            newsRepository.findByTitleOrContent(
                searchRequestDto.title,
                searchRequestDto.content,
                pageable
            )
        } returns listOf(news)
        val newsResult = newsService.findNews(searchRequestDto)
        Assertions.assertEquals(newsResult[0], news)
    }
}
