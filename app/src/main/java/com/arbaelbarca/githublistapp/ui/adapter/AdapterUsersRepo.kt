package com.arbaelbarca.githublistapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.githublistapp.databinding.LayoutItemUsersRepoBinding
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsersRepo
import com.arbaelbarca.githublistapp.utils.TimeAgo
import com.arbaelbarca.githublistapp.utils.ViewBindingVH
import com.arbaelbarca.githublistapp.utils.loadImageUrl

class AdapterUsersRepo(
    val listUsersRepo: MutableList<ResponseUsersRepo.ResponseUsersRepoItem> = arrayListOf()
) : RecyclerView.Adapter<ViewBindingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingVH {
        return ViewBindingVH.create(parent, LayoutItemUsersRepoBinding::inflate)
    }

    override fun onBindViewHolder(holder: ViewBindingVH, position: Int) {
        val usersRepo = listUsersRepo[position]
        (holder.binding as LayoutItemUsersRepoBinding).apply {
            imgUsers.loadImageUrl(usersRepo.owner?.avatar_url.toString(), holder.itemView.context)
            tvNameUsersRepo.text = usersRepo.name
            tvDescUsersRepo.text = usersRepo.description

            tvHoursUsersRepo.text = TimeAgo().covertTimeToText(usersRepo.pushed_at)
            tvRateUsersRepo.text = usersRepo.stargazers_count.toString()

        }
    }

    override fun getItemCount(): Int {
        return listUsersRepo.size
    }
}