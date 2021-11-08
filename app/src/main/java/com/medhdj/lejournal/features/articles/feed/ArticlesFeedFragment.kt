package com.medhdj.lejournal.features.articles.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.medhdj.core.platform.setupToolbar
import com.medhdj.lejournal.databinding.FragmentArticlesFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFeedFragment : Fragment() {

    private var _binding: FragmentArticlesFeedBinding? = null
    private val binding get() = _binding!!

    private val articlesFeedViewModel by viewModels<ArticlesFeedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesFeedBinding.inflate(inflater, container, false)
            .apply {
                viewModel = articlesFeedViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            toolbar = binding.toolbar,
            showBack = false
        )
        binding.articlesList.adapter.onItemClickListener = {
            val action = ArticlesFeedFragmentDirections.actionGoToDetails(articleId = it.id)
            findNavController().navigate(action)
        }
    }
}
