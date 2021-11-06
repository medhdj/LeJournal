package com.medhdj.lejournal.features.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.medhdj.lejournal.databinding.ArticleListItemBinding

class ArticlesFeedPagingAdapter :
    PagingDataAdapter<ArticleUIModel, ArticlesFeedPagingAdapter.ArticleItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder =
        ArticleItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ArticleItemViewHolder(private val articleItemBinding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(articleItemBinding.root) {
        fun bind(articleUIModel: ArticleUIModel) = with(articleItemBinding) {
            headline.text = articleUIModel.id
        }

        companion object {
            fun create(parent: ViewGroup): ArticleItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val articleItemBinding = ArticleListItemBinding.inflate(inflater, parent, false)
                return ArticleItemViewHolder(articleItemBinding)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleUIModel>() {
            override fun areItemsTheSame(
                oldItem: ArticleUIModel, newItem: ArticleUIModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleUIModel, newItem: ArticleUIModel
            ): Boolean = oldItem == newItem
        }
    }
}
