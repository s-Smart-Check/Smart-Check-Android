package com.example.smartattendancecheckapp.presentation.ui.signup.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.smartattendancecheckapp.databinding.ItemPhotoBinding
import com.example.smartattendancecheckapp.presentation.ui.signup.viewholder.PhotoViewHolder

class PhotoAdapter(
    private val onRemovePhotoClick: (position: Int) -> Unit = { _ -> }
): ListAdapter<Uri, PhotoViewHolder>(PhotoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onRemovePhotoClick = onRemovePhotoClick
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PhotoDiffCallback(): DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

}