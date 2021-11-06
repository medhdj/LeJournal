package com.medhdj.lejournal.features.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.medhdj.lejournal.databinding.FragmentArticlesFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFeedFragment : Fragment() {

    private val articlesFeedViewModel by viewModels<ArticlesFeedViewModel>()
    private var _binding: FragmentArticlesFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesFeedBinding.inflate(inflater, container, false).apply {
            viewModel = articlesFeedViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        articlesFeedViewModel.articlesFeedData.observe(viewLifecycleOwner) {
//            binding.articlesList.updateList(lifecycle,it)
//        }
    }
}
