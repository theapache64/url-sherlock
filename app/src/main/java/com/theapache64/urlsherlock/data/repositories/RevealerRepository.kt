package com.theapache64.urlsherlock.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theapache64.twinkill.utils.AppExecutors
import com.theapache64.twinkill.utils.Resource
import com.theapache64.urlsherlock.utils.UrlRevealer
import java.io.IOException
import javax.inject.Inject


class RevealerRepository @Inject constructor(
    private val appExecutors: AppExecutors
) {
    fun revealUrl(url: String): LiveData<Resource<String>> {

        val result = MutableLiveData<Resource<String>>()

        // Loading
        result.value = Resource.loading()

        appExecutors.networkIO().execute {
            try {
                val revealedUrl = UrlRevealer.reveal(url)
                result.postValue(Resource.success(revealedUrl))
            } catch (e: IOException) {
                e.printStackTrace()
                result.postValue(Resource.error("ERROR: ${e.message}"))
            }
        }

        return result
    }
}