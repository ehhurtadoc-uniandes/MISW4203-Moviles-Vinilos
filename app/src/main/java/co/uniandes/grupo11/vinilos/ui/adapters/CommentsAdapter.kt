package co.uniandes.grupo11.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {
    
    private var comments: List<Comment> = emptyList()

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val commentDescription: TextView = view.findViewById(R.id.comment_description)
        private val commentRating: RatingBar = view.findViewById(R.id.comment_rating)

        fun bind(comment: Comment) {
            commentDescription.text = comment.description
            commentRating.rating = comment.rating.toFloat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount() = comments.size

    fun updateComments(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged()
    }
}

