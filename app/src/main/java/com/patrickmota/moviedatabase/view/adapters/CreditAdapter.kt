package com.patrickmota.moviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patrickmota.moviedatabase.R
import com.patrickmota.moviedatabase.model.CreditResponse
import com.patrickmota.moviedatabase.model.Cast
import kotlinx.android.synthetic.main.item_credit.view.*

class CreditAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var items: CreditResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CreditsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_credit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CreditsViewHolder -> {
                holder.bind(items.cast[position])
            }
        }
    }

    override fun getItemCount(): Int = items.cast.size

    class CreditsViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var cast: Cast

        fun bind(cast: Cast) {
            this.cast = cast

            itemView.castName.text = cast.name
            itemView.castPaperName.text = cast.character

            val bannerUrl = "https://image.tmdb.org/t/p/w500" + cast.profilePath

            Glide.with(itemView.context)
                .load(bannerUrl)
                .fitCenter()
                .into(itemView.castImage)

        }
    }

}