package com.gllce.artbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gllce.artbook.api.ArtRepositoryInterface
import com.gllce.artbook.roomdb.FakeArtRepository
import com.gllce.artbook.util.Status
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.MainCoroutineRule
import com.task.noteapp.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        //Test Doubles (copy)
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Test", "Artist", "")
        val result = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("", "Artist", "1999")
        val result = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Test", "", "1999")
        val result = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(result.status).isEqualTo(Status.ERROR)
    }
}