package com.example.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class MyViewModelTest{

    @get:Rule
    val testRule:TestRule = InstantTaskExecutorRule()
    @Before
    fun setup(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }
     @Test
     fun getSuccessResponse(){
         val repository = Mockito.mock(Repository::class.java)

         val herolist = mutableListOf<Hero>()
         val successfulResponse = herolist
         val viewModel = MyViewModel(repository)
         val eventList = mutableListOf<MyViewModel.UIState>()
         viewModel.uiState.observeForever {
             eventList.add(it)}
             runBlocking {
                 Mockito.`when`(repository.getHeroByName()).thenReturn(successfulResponse)
             }

         viewModel.getData()
         Assert.assertEquals(MyViewModel.UIState.Empty, eventList[0])
         Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
         val heroResult = eventList[2] as MyViewModel.UIState.Result
        // Assert.assertEquals("Batman", Hero.name)
     }

    @Test
    fun getNullResponse(){
        val repository = Mockito.mock(Repository::class.java)
        val herolist = mutableListOf<Hero>()
        val nullResponse = mutableListOf<Hero>().isNullOrEmpty()
        val viewModel = MyViewModel(repository)
        val eventList = mutableListOf<MyViewModel.UIState>()
        viewModel.uiState.observeForever {
            eventList.add(it)}
        runBlocking {
            Mockito.`when`(repository.getHeroByName()).thenReturn(nullResponse)
        }

        Assert.assertEquals(MyViewModel.UIState.Empty, eventList[0])
        Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
        val nullResult = eventList[2] as MyViewModel.UIState.Error

    }




}