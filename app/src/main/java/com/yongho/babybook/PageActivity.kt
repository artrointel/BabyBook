package com.yongho.babybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yongho.babybook.databinding.ActivityPageBinding
import com.yongho.babybook.entity.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class PageActivity : AppCompatActivity() {

    private val pageViewModel : PageViewModel by viewModels()

    private val binding by lazy {
        ActivityPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadInitData()
        setBtnListener()
    }

    private fun loadInitData() {
        intent?.let {
            var dateText = intent.getStringExtra(EXTRA_PAGE_DATE)
            if (dateText.isNullOrEmpty()) {
                dateText = LocalDate.now().toString()
            }

            pageViewModel.getContentByDate(dateText).observe(this) {
                Log.d(TAG, "loaded content : $it")
                binding.page = Page(dateText, it)
            }
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            val date = binding.date.text.toString()
            val content = binding.content.text.toString()

            pageViewModel.insert(Page(date, content))
            finish()
        }
    }

    companion object {
        const val TAG = "PageActivity"
        const val EXTRA_PAGE_DATE = "EXTRA_PAGE_DATE"
        const val EXTRA_PAGE_CONTENT = "EXTRA_PAGE_CONTENT"
    }
}