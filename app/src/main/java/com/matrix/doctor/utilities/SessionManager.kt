package com.svu.epetrol.session

import android.content.Context
import android.content.SharedPreferences


class SessionManager private constructor(val context: Context) {

    private val sharedPref: SharedPreferences

    internal var editor: SharedPreferences.Editor

    val host: String
        get() = sharedPref.getString(HOST,"");

    val HOST = "host";

    val SHARED_PREF_NAME = "e__petrol"
    val PRIVATE_MODE = 0


    init {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE)
        editor = sharedPref.edit()
    }

    companion object : SingletonHolder<SessionManager, Context>(::SessionManager)

    //logout
    fun clearAndLogout() {
        editor.clear()
        editor.commit()
    }

    public fun setUrlHost(host: String) {

        editor.putString(HOST,host)

        editor.commit()
    }

}
