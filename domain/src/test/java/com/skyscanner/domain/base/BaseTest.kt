package com.skyscanner.domain.base

import org.junit.Rule
import org.mockito.junit.MockitoJUnit

open class BaseTest {

    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()
}
