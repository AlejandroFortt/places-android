package com.fortatic.apps.places.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.fortatic.apps.places.R
import com.fortatic.apps.places.databinding.ViewProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private companion object {
        const val DEFAULT_LABEL = "button"
    }

    private val binding = ViewProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var text: String? = DEFAULT_LABEL
        set(value) {
            field = value
            binding.tvLabel.text = value
        }

    var loading: Boolean = false
        set(value) {
            field = value
            updateLoading(value)
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ProgressButton).apply {
            // Retrieving attributes from xml
            text = getString(R.styleable.ProgressButton_label) ?: DEFAULT_LABEL
            recycle()
        }
    }

    private fun updateLoading(loading: Boolean) {
        isClickable = !loading // Disable clickable when loading
        if (loading) {
            binding.tvLabel.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.tvLabel.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.tvLabel.isEnabled = enabled
    }
}