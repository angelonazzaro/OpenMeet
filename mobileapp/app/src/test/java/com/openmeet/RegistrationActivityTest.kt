package com.openmeet

import android.os.StrictMode
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.openmeet.logic.RegistrationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(JUnit4::class)
class RegistrationActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RegistrationActivity::class.java)

    @Mock
    lateinit var strictMode: StrictMode

//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        Mockito.`when`(strictMode.allowThreadDiskReads()).thenReturn(null)
//    }
//
    @Test
    fun testExample() {
    val scenario = launch(RegistrationActivity::class.java)
    }
}