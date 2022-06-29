package com.newspage.backend.service

import com.newspage.backend.model.News
import com.newspage.backend.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class NewsServiceTest {

    private val newsRepository = mockk<NewsRepository>()
    private val newsService = NewsService(newsRepository)

    private val newsId = 42
    private val news = News(newsId, "Good News", "here", "John Doe", "Lorem Ipsum")
    val notExistingNewsId = newsId + 1

    private val notDefaultPage = NewsService.DEFAULT_PAGE + 1
    private val notDefaultPageSize = NewsService.DEFAULT_PAGE_SIZE + 1

    val searchString = "Lorem"

    @Test
    fun createPageableWithoutInputValues() {
        val pageable = newsService.createPageable(null, null)
        Assertions.assertEquals(PageRequest.of(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE), pageable)
    }

    @Test
    fun createPageableWithInputValues() {
        val pageable = newsService.createPageable(notDefaultPage, notDefaultPageSize)
        Assertions.assertNotEquals(PageRequest.of(NewsService.DEFAULT_PAGE, NewsService.DEFAULT_PAGE_SIZE), pageable)
    }

    @Test
    fun latestNewsWithoutPageable() {
        every {
            newsRepository.findAll(
                PageRequest.of(
                    NewsService.DEFAULT_PAGE,
                    NewsService.DEFAULT_PAGE_SIZE
                )
            )
        } returns PageImpl(listOf(news))

        val newsResponse = newsService.latestNews(null, null)
        Assertions.assertEquals(news, newsResponse[0])
    }

    @Test
    fun latestNewsWithPageable() {
        every {
            newsRepository.findAll(
                PageRequest.of(
                    notDefaultPage,
                    notDefaultPageSize
                )
            )
        } returns PageImpl(listOf(news))

        val newsResponse = newsService.latestNews(notDefaultPage, notDefaultPageSize)
        Assertions.assertEquals(news, newsResponse[0])
    }

    @Test
    fun searchNewsWithoutPageable() {
        every {
            newsRepository.searchNewsByTitleOrContentOrAuthor(
                searchString, searchString, searchString, PageRequest.of(
                    NewsService.DEFAULT_PAGE,
                    NewsService.DEFAULT_PAGE_SIZE
                )
            )
        } returns PageImpl(listOf(news))
        val newsResponse = newsService.searchNews(searchString, null, null)
        Assertions.assertEquals(news, newsResponse[0])
    }

    @Test
    fun searchNewsWithPageable() {
        every {
            newsRepository.searchNewsByTitleOrContentOrAuthor(
                searchString, searchString, searchString, PageRequest.of(
                    notDefaultPage,
                    notDefaultPageSize
                )
            )
        } returns PageImpl(listOf(news))
        val newsResponse = newsService.searchNews(searchString, notDefaultPage, notDefaultPageSize)
        Assertions.assertEquals(news, newsResponse[0])
    }

    @Test
    fun getNews() {
        every {
            newsRepository.findById(newsId)
        } returns Optional.of(news)
        val newsResponse = newsService.getNews(newsId)
        Assertions.assertEquals(news, newsResponse)
    }

    @Test
    fun getNewsThatDoesNotExist() {
        val expectedErrorMessage = "News with id $notExistingNewsId not found"
        every {
            newsRepository.findById(notExistingNewsId)
        } returns Optional.empty()

        val exception = Assertions.assertThrows(ResponseStatusException::class.java) {
            newsService.getNews(notExistingNewsId)
        }
        Assertions.assertTrue(exception.message.contains(expectedErrorMessage))
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }
}
