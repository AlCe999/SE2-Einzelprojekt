package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.mockito.Mockito.`when` as whenever

class GameResultControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = GameResultController(mockedService)
    }

    @Test
    fun test_getAllGameResults() {
        val list = listOf(GameResult(1, "p1", 10, 5.0))
        whenever(mockedService.getGameResults()).thenReturn(list)

        val res = controller.getAllGameResults()

        verify(mockedService).getGameResults()
        assertEquals(1, res.size)
        assertEquals(list[0], res[0])
    }

    @Test
    fun test_getGameResult_existingId() {
        val game = GameResult(1, "p1", 10, 5.0)
        whenever(mockedService.getGameResult(1)).thenReturn(game)

        val res = controller.getGameResult(1)

        verify(mockedService).getGameResult(1)
        assertEquals(game, res)
    }

    @Test
    fun test_getGameResult_nonExistingId() {
        whenever(mockedService.getGameResult(99)).thenReturn(null)

        val res = controller.getGameResult(99)

        verify(mockedService).getGameResult(99)
        assertNull(res)
    }

    @Test
    fun test_addGameResult() {
        val game = GameResult(0, "p1", 10, 5.0)

        controller.addGameResult(game)

        verify(mockedService).addGameResult(game)
    }

    @Test
    fun test_deleteGameResult() {
        controller.deleteGameResult(1)

        verify(mockedService).deleteGameResult(1)
    }
}