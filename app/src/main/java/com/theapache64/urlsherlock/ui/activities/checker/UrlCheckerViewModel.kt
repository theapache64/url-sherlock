package com.theapache64.urlsherlock.ui.activities.checker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.urlsherlock.data.repositories.RevealerRepository
import javax.inject.Inject

class UrlCheckerViewModel @Inject constructor(
    private val revealerRepository: RevealerRepository
) : ViewModel() {
    private val sourceUrl = MutableLiveData<String>()
    private val revealResponse = Transformations.switchMap(sourceUrl) {
        revealerRepository.revealUrl(it)
    }

    fun getRevealedResponse() = revealResponse
    fun revealUrl(_url: String) {
        val url = if (_url.startsWith("http://", true) || _url.startsWith("https://", true)) {
            _url
        } else {
            "http://$_url"
        }
        this.sourceUrl.value = url
    }

    fun getSourceUrl(): String? {
        return sourceUrl.value
    }

    fun getDestinationUrl(): String? {
        return revealResponse.value?.data
    }
}