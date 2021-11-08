package com.medhdj.lejournal.features.articles.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.medhdj.core.extension.setImage
import com.medhdj.lejournal.R
import com.medhdj.lejournal.databinding.ArticleListItemBinding
import com.medhdj.lejournal.databinding.SectionHeaderItemBinding
import com.medhdj.lejournal.features.articles.ArticleUIModels

class ArticlesFeedPagingAdapter :
    PagingDataAdapter<ArticleUIModels, RecyclerView.ViewHolder>(COMPARATOR) {

    var onItemClickListener: ((ArticleUIModels.ArticleItemUIModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION_HEADER -> {
                SectionHeaderViewHolder.create(parent)
            }
            else -> {
                ArticleItemViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ArticleUIModels.SectionHeaderUIModel -> {
                (holder as SectionHeaderViewHolder).bind(item)
            }
            is ArticleUIModels.ArticleItemUIModel -> {
                val previousItem =
                    runCatching { getItem(position - 1) as ArticleUIModels.ArticleItemUIModel }.getOrNull()
                val nextItem =
                    runCatching { getItem(position + 1) as ArticleUIModels.ArticleItemUIModel }.getOrNull()
                val items = Triple(previousItem, item, nextItem)
                (holder as ArticleItemViewHolder).bind(items, onItemClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (val item = getItem(position)) {
        is ArticleUIModels.SectionHeaderUIModel -> VIEW_TYPE_SECTION_HEADER
        is ArticleUIModels.ArticleItemUIModel -> VIEW_TYPE_ARTICLE
        else -> throw IllegalStateException("Unknown view type for${item}")
    }

    // View Holders
    class SectionHeaderViewHolder(private val sectionHeaderItemBinding: SectionHeaderItemBinding) :
        RecyclerView.ViewHolder(sectionHeaderItemBinding.root) {
        fun bind(sectionHeaderUIModel: ArticleUIModels.SectionHeaderUIModel) =
            with(sectionHeaderItemBinding) {
                sectionTitle.text =
                    sectionTitle.resources.getString(sectionHeaderUIModel.titleResId)
            }

        companion object {
            fun create(parent: ViewGroup): SectionHeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val sectionHeaderItemBinding =
                    SectionHeaderItemBinding.inflate(inflater, parent, false)
                return SectionHeaderViewHolder(sectionHeaderItemBinding)
            }
        }
    }

    class ArticleItemViewHolder(private val articleItemBinding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(articleItemBinding.root) {
        fun bind(
            items: Triple<
                    ArticleUIModels.ArticleItemUIModel?,
                    ArticleUIModels.ArticleItemUIModel,
                    ArticleUIModels.ArticleItemUIModel?>,
            onItemClickListener: ((ArticleUIModels.ArticleItemUIModel) -> Unit)?
        ) = with(articleItemBinding) {
            val (previous, current, next) = items
            articleThumbnail.setImage(
                imageUrl = current.thumbnailUrl,
                circleCrop = true
            )

            headline.text = current.headline
            articlePublicationDate.text = current.publicationDate

            itemView.setOnClickListener {
                onItemClickListener?.invoke(current)
            }
            applyCorrectBackground(previous, next, articleItemBinding)
        }

        private fun applyCorrectBackground(
            previous: ArticleUIModels.ArticleItemUIModel?,
            next: ArticleUIModels.ArticleItemUIModel?,
            articleItemBinding: ArticleListItemBinding
        ) {
            when {
                previous == null && next == null -> {
                    articleItemBinding.root.setBackgroundResource(R.drawable.background_isolated_item)
                }
                previous == null && next != null -> {
                    articleItemBinding.root.setBackgroundResource(R.drawable.background_top_item)
                }
                previous != null && next == null -> {
                    articleItemBinding.root.setBackgroundResource(R.drawable.background_bottom_item)
                }
                else -> {
                    articleItemBinding.root.setBackgroundResource(R.drawable.background_midle_item)
                }
            }
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
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleUIModels>() {
            override fun areItemsTheSame(
                oldItem: ArticleUIModels, newItem: ArticleUIModels
            ): Boolean {
                val isSameArticle = oldItem is ArticleUIModels.ArticleItemUIModel
                        && newItem is ArticleUIModels.ArticleItemUIModel
                        && oldItem.id == newItem.id

                val isSameSection = oldItem is ArticleUIModels.SectionHeaderUIModel
                        && newItem is ArticleUIModels.SectionHeaderUIModel
                        && oldItem.titleResId == newItem.titleResId

                return isSameArticle || isSameSection
            }

            override fun areContentsTheSame(
                oldItem: ArticleUIModels, newItem: ArticleUIModels
            ): Boolean = oldItem == newItem
        }

        private const val VIEW_TYPE_SECTION_HEADER = 0
        private const val VIEW_TYPE_ARTICLE = 1
    }
}
