package com.yongho.babybook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yongho.babybook.databinding.FragmentMainBinding
import com.yongho.babybook.entity.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val pageListViewModel: PageViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setBtnListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        val adapter = PageAdapter({ page ->
            //TODO: launch page fragment

        }, { page ->
            showDeleteDialog(page)
        })

        binding.pageList.adapter = adapter
        binding.pageList.layoutManager = LinearLayoutManager(activity)
        binding.pageList.setHasFixedSize(true)

        pageListViewModel.getAll().observe(this) { pages ->
            Log.d(TAG, "page list is changed")
            adapter.setPages(pages)
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            //TODO: launch page fragment
        }
    }

    private fun showDeleteDialog(page: Page) {
        activity?.let {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Delete selected page?")
                .setNegativeButton("NO") { _, _ -> }
                .setPositiveButton("YES") { _, _ ->
                    pageListViewModel.delete(page)
                }

            builder.show()
        }
    }

    companion object {
        private const val TAG = "BabyBook.MainFragment"
    }
}