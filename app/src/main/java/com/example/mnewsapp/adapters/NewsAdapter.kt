package com.example.mnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mnewsapp.R
import com.example.mnewsapp.databinding.FragmentArticleBinding
import com.example.mnewsapp.databinding.FragmentBreakingNewsBinding

import com.example.mnewsapp.databinding.ItemArticlePreviewBinding
import com.example.mnewsapp.models.Article
import com.example.mnewsapp.ui.fragments.BreakingNewsFragment
import com.example.mnewsapp.ui.fragments.BreakingNewsFragmentDirections

//import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private var binding: ItemArticlePreviewBinding? = null
    inner class ArticleViewHolder(itemBinding: ItemArticlePreviewBinding ): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//        return ArticleViewHolder(
//            LayoutInflater.from(parent.context).inflate(
    //                R.layout.item_article_preview,
//                parent,
//                false
//            )
//        )
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
binding= ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding!!)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding?.ivArticleImage!!)
          binding?.tvSource?.text = article.source.name
            binding?.tvTitle?.text = article.title
            binding?.tvDescription?.text = article.description
            binding?.tvPublishedAt?.text = article.publishedAt

//           setOnClickListener {
//               onItemClickListener?.let { it(article) }
//            }
        }
            .setOnClickListener { mView ->
                val direction = BreakingNewsFragmentDirections
                    .actionBreakingNewsFragmentToArticleFragment(article)

                mView.findNavController().navigate(direction)
            }
    }

//    fun setOnItemClickListener(listener: (Article) -> Unit) {
//        onItemClickListener = listener
//    }

}








