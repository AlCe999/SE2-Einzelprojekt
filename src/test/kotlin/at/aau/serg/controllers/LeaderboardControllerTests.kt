package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(HttpStatus.OK, res.statusCode)
        val body = res.body!!
        assertEquals(3, body.size)
        assertEquals(first, body[0])
        assertEquals(second, body[1])
        assertEquals(third, body[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_correctTimeSorting() {
        val first = GameResult(1, "first", 20, 10.0)
        val second = GameResult(2, "second", 20, 15.0)
        val third = GameResult(3, "third", 20, 20.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(third, first, second))

        val res = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(HttpStatus.OK, res.statusCode)
        val body = res.body!!
        assertEquals(3, body.size)
        assertEquals(first, body[0])
        assertEquals(second, body[1])
        assertEquals(third, body[2])
    }
}