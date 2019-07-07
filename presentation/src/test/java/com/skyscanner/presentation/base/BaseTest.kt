package com.skyscanner.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()
}
