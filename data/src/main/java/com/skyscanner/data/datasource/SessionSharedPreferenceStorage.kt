package com.skyscanner.data.datasource

import android.content.SharedPreferences
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.skyscanner.data.model.SessionInput
import com.skyscanner.data.util.Mockable

/**
 * A class for storing a sessionId and validating it for later use
 */
@Mockable
class SessionSharedPreferenceStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SessionInDeviceStorage {

    /**
     * @return the sessionId if available and
     * requesting SessionInput is equal to the SessionInput
     * for which the previous sessionId was saved for and
     * if sessionId not older than 30 mins
     * or throw InvalidSessionException
     */
    override fun getSessionId(input: SessionInput): String =
        sharedPreferences.getString(SESSION_KEY, null)?.let {
            val session: Session? = gson.fromJson(it)
            if (session?.sessionInput == input) {
                if (isWithIn30Mins(session.timeStamp)) {
                    session.sessionId
                } else throw InvalidSessionException()
            } else throw InvalidSessionException()
        } ?: throw InvalidSessionException()

    /**
     * to store the sessionId along with the SessionInput
     * Session is a wrapper for sessionId and SessionInput
     */
    override fun storeSession(sessionId: String, input: SessionInput) {
        val session = Session(sessionId, input, System.currentTimeMillis())
        sharedPreferences.edit()
            .putString(SESSION_KEY, gson.toJson(session))
            .apply()
    }

    private fun isWithIn30Mins(timeStamp: Long): Boolean =
        ((((System.currentTimeMillis() - timeStamp) / 1000) % 3600) / 60) < 30

    companion object {
        private const val SESSION_KEY = "session_key"
    }

    private data class Session(
        val sessionId: String,
        val sessionInput: SessionInput,
        val timeStamp: Long
    )
}

class InvalidSessionException : RuntimeException()



