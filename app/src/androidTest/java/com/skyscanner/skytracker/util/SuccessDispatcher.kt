package com.skyscanner.skytracker.util

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.skyscanner.skytracker.util.AssetReaderUtil.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        APIPaths.CREATE_SESSION to MockFiles.RESPONSE_CREATE_SESSION,
        APIPaths.GET_FLIGHT_INFO to MockFiles.RESPONSE_GET_FLIGHT_INFO
    )

    override fun dispatch(request: RecordedRequest?): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody).apply {
                if (request?.method == "POST") {
                    setHeader(Header.HEADER_KEY, Header.HEADER_VALUE)
                }
            }
        } else {
            errorResponse
        }
    }
}