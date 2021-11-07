package com.medhdj.lejournal.features.articles.feed

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medhdj.lejournal.features.articles.ArticleUIModels

class ArticlesFeedListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), LifecycleOwner {

    private val listAdapter by lazy { ArticlesFeedPagingAdapter() }
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        initList()
    }

    fun updateList(data: PagingData<ArticleUIModels>) {
        listAdapter.submitData(lifecycle, data)
    }

    private fun initList() {
        layoutManager = LinearLayoutManager(context)
        adapter = listAdapter
    }

    override fun getAdapter(): ArticlesFeedPagingAdapter = listAdapter

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}

@BindingAdapter("data")
fun ArticlesFeedListView.bindData(data: PagingData<ArticleUIModels>?) {
    data?.let {
        updateList(data)
    }
}
