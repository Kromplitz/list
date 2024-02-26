package com.example.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
         herolist.add(Hero("Batman", Image ("","","","")))

         val viewModel = MyViewModel(repository)
         val eventList = mutableListOf<MyViewModel.UIState>()
         viewModel.uiState.observeForever {
             eventList.add(it)}
             runBlocking {
                 Mockito.`when`(repository.getHeroByName()).thenReturn(herolist)
                 viewModel.getData()
                 delay(10)
             }


         Assert.assertEquals(MyViewModel.UIState.Empty, eventList[0])
         Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
         val heroResult = eventList[2] as MyViewModel.UIState.Result
        Assert.assertEquals(false, heroResult.heroes.isNullOrEmpty())
     }

    @Test
    fun getNullResponse(){
        val repository = Mockito.mock(Repository::class.java)
        val viewModel = MyViewModel(repository)
        val eventList = mutableListOf<MyViewModel.UIState>()
        viewModel.uiState.observeForever {
            eventList.add(it)}
        runBlocking {
            Mockito.`when`(repository.getHeroByName()).thenReturn(null)
            viewModel.getData()
            delay(10)
        }

        Assert.assertEquals(MyViewModel.UIState.Empty, eventList[0])
        Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
        val nullResult = eventList[2] as MyViewModel.UIState.Error
        Assert.assertEquals("Error", nullResult.description)

    }




}