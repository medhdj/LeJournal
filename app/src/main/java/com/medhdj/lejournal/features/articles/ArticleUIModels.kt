package com.medhdj.lejournal.features.articles

import androidx.annotation.StringRes
import com.medhdj.business.articles.Article
import com.medhdj.core.extension.asString
import com.medhdj.core.extension.endOfTheWeek
import com.medhdj.core.extension.startOfTheWeek
import org.joda.time.DateTime
import org.joda.time.Interval

sealed class ArticleUIModels {
    data class ArticleItemUIModel(
        val id: String,
        val headline: String,
        val thumbnailUrl: String,
        val publicationDate: String,
        val articleAgeCategory: ArticleAgeCategoryEnum = ArticleAgeCategoryEnum.OLDER
    ) : ArticleUIModels()

    data class SectionHeaderUIModel(@StringRes val titleResId: Int) : ArticleUIModels()
}

enum class ArticleAgeCategoryEnum {
    THIS_WEEK,
    LAST_WEEK,
    OLDER
}

internal fun Article.toArticleItemUIModel(): ArticleUIModels =
    ArticleUIModels.ArticleItemUIModel(
        id = id,
        headline = headline,
        thumbnailUrl = thumbnailUrl,
        publicationDate = publicationDate.asString(),
        articleAgeCategory = determineArticleAge(this)
    )

private fun determineArticleAge(article: Article): ArticleAgeCategoryEnum {
    val now = DateTime.now()
    val currentWeekInterval = Interval(now.startOfTheWeek(), now.endOfTheWeek())

    val lastWeek = now.minusWeeks(1)
    val lastWeekInterval = Interval(lastWeek.startOfTheWeek(), lastWeek.endOfTheWeek())

    return when {
        currentWeekInterval.contains(article.publicationDate.time) -> ArticleAgeCategoryEnum.THIS_WEEK
        lastWeekInterval.contains(article.publicationDate.time) -> ArticleAgeCategoryEnum.LAST_WEEK
        else -> ArticleAgeCategoryEnum.OLDER
    }
}



