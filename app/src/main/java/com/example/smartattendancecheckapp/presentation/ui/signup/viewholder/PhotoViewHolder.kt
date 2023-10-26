package com.example.smartattendancecheckapp.presentation.ui.signup.viewholder

import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.smartattendancecheckapp.databinding.ItemPhotoBinding

class PhotoViewHolder(
    private val binding: ItemPhotoBinding,
    private val onRemovePhotoClick: (position: Int) -> Unit = { _ -> }
) : RecyclerView.ViewHolder(binding.root) {

    init {
        // 등록한 이미지 제거
        binding.btnRemoveEnrollPhoto.setOnClickListener {
            onRemovePhotoClick(adapterPosition)
        }
    }

    fun bind(uri: Uri) {

        binding.apply {
            ivEnrollPhoto.setImageURI(uri)
            executePendingBindings()
        }
    }


}