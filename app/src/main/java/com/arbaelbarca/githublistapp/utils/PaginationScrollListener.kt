package com.arbaelbarca.githublistapp.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    val layoutManager: LinearLayoutManager,
    val listener: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) { // scroll down
            totalItemCount = layoutManager.itemCount
            lastVisibleItem = layoutManager
                .findLastVisibleItemPosition()
            listener()
            loading = false
        }
    }
}