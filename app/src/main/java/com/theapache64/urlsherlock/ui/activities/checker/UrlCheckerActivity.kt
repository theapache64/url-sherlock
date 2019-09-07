package com.theapache64.urlsherlock.ui.activities.checker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.Resource
import com.theapache64.twinkill.utils.extensions.bindContentView
import com.theapache64.twinkill.utils.extensions.toast
import com.theapache64.urlsherlock.R
import com.theapache64.urlsherlock.databinding.ActivityUrlCheckerBinding
import com.theapache64.urlsherlock.ui.colorgen.MyColorGenerator
import dagger.android.AndroidInjection
import javax.inject.Inject


class UrlCheckerActivity : BaseAppCompatActivity(), UrlCheckerHandler {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, UrlCheckerActivity::class.java).apply {
                // data goes here
            }
        }
    }


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivityUrlCheckerBinding
    private lateinit var viewModel: UrlCheckerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = bindContentView(R.layout.activity_url_checker)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewModel = ViewModelProviders.of(this, factory).get(UrlCheckerViewModel::class.java)

        binding.handler = this
        binding.viewModel = viewModel
        binding.trvBg.apply {
            post {
                drawable.setColorGenerator(MyColorGenerator(this@UrlCheckerActivity))
            }
        }

        val url = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.tvSource.text = url


        viewModel.getRevealedResponse().observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.tvDest.setText(R.string.loading)
                }
                Resource.Status.SUCCESS -> {
                    binding.tvDest.text = it.data!!
                }
                Resource.Status.ERROR -> {
                    binding.tvDest.text = it.message!!
                }
            }
        })

        if (url != null && url.isNotEmpty()) {
            viewModel.revealUrl(url)
        } else {
            toast(R.string.error_url_is_empty)
            finish()
        }


    }

    override fun onSourceUrlClicked() {
        goto(viewModel.getSourceUrl())
    }

    private fun goto(url: String?) {
        if (url != null) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            finish()
        } else {
            toast(R.string.error_url_is_empty)
        }
    }

    override fun onDestinationUrlClicked() {
        goto(viewModel.getDestinationUrl())
    }
}
