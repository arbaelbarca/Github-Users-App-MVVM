package com.arbaelbarca.githublistapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.githublistapp.databinding.LayoutItemProgressbarBinding
import com.arbaelbarca.githublistapp.databinding.LayoutItemUserBinding
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsers
import com.arbaelbarca.githublistapp.presentation.onclick.OnClickItem
import com.arbaelbarca.githublistapp.utils.*

class AdapterUsers(
    val listUsers: MutableList<ResponseSearchUsers.ItemUsers?> = arrayListOf(),
    val onClickItem: OnClickItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            return ViewBindingVH.create(parent, LayoutItemUserBinding::inflate)
        } else
            return ViewBindingLoading.create(parent, LayoutItemProgressbarBinding::inflate)

    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listUsers.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val usersItem = listUsers[position]

        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            val holderItem = holder as ViewBindingVH
            (holderItem.binding as LayoutItemUserBinding).apply {
                imgUsers.loadImageUrl(
                    usersItem?.avatar_url.toString(),
                    context = holder.itemView.context
                )
                tvNameUsers.text = usersItem?.login

                holder.itemView.setOnClickListener {
                    if (usersItem != null) {
                        onClickItem.clickItem(usersItem, position)
                    }
                }
            }

        }

        if (holder.itemViewType == VIEW_TYPE_LOADING) {
            val holderLoading = holder as ViewBindingLoading
            (holderLoading.binding as LayoutItemProgressbarBinding).apply {
                if (isLoading) {
                    showView(pbLoading)
                } else hideView(pbLoading)
            }
        }
    }

    fun setLoadings(boolean: Boolean) {
        isLoading = boolean
    }

    fun showLoading(): Boolean {
        return isLoading
    }

    fun hideLoading(): Boolean {
        return isLoading
    }
}