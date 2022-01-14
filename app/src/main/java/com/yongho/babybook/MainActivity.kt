package com.yongho.babybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yongho.babybook.databinding.ActivityMainBinding
import com.yongho.babybook.entity.Page
import com.yongho.babybook.viewmodel.PageViewModel

class MainActivity : AppCompatActivity() {

    private val pageViewModel by lazy {
        ViewModelProvider(this).get(PageViewModel::class.java)
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        setBtnListener()
    }

    private fun initRecyclerView() {
        val adapter = PageAdapter({ page ->
            val intent = Intent(this, PageActivity::class.java).apply {
                putExtra(PageActivity.EXTRA_PAGE_DATE, page.date)
                putExtra(PageActivity.EXTRA_PAGE_CONTENT, page.content)
            }

            startActivity(intent)

        }, { page ->
            showDeleteDialog(page)
        })

        binding.pageList.adapter = adapter
        binding.pageList.layoutManager = LinearLayoutManager(this)
        binding.pageList.setHasFixedSize(true)

        pageViewModel.getAll().observe(this) { pages ->
            adapter.setPages(pages)
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, PageActivity::class.java))
        }
    }

    private fun showDeleteDialog(page: Page) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected page?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                pageViewModel.delete(page)
            }

        builder.show()
    }
}