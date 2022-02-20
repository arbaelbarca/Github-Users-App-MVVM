package com.arbaelbarca.githublistapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.githublistapp.presentation.onclick.OnScrollListener


fun showView(view: View) {
    view.visibility = View.VISIBLE
}


fun hideView(view: View) {
    view.visibility = View.GONE
}

fun setRvAdapterVertikalDefault(
    recyclerView: RecyclerView,
    linearLayoutManager: LinearLayoutManager,
    adapterDefault: RecyclerView.Adapter<*>,
    onScrollListener: OnScrollListener
) {
    recyclerView.apply {
        adapter = adapterDefault
        layoutManager = linearLayoutManager
        hasFixedSize()

        addOnScrollListener(PaginationScrollListener(linearLayoutManager) {
            onScrollListener.scrollRecyclerview()
        })
    }
}

fun showToast(message: String?, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}



