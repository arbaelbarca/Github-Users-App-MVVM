package com.arbaelbarca.githublistapp.ui.view.users

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbaelbarca.githublistapp.R
import com.arbaelbarca.githublistapp.databinding.FragmentHomeBinding
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.onclick.OnClickItem
import com.arbaelbarca.githublistapp.presentation.onclick.OnScrollListener
import com.arbaelbarca.githublistapp.presentation.viewmodel.users.ViewModelUsers
import com.arbaelbarca.githublistapp.ui.adapter.AdapterUsers
import com.arbaelbarca.githublistapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val perPage = 10
    var page = 1

    val viewModelHome: ViewModelUsers by viewModels()
    val fragmentBinding: FragmentHomeBinding by viewBinding()

    var listUsers: MutableList<ResponseSearchUsers.ItemUsers?> = arrayListOf()
    var adapterUsers: AdapterUsers? = null

    lateinit var linearLayoutManager: LinearLayoutManager

    var totalSizeList = 0
    var totalCount = 0
    var textSearch = "arba"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }

    private fun initial() {
        initCallApi()
        initObserver()
        initTextWatcher()
    }

    private fun initTextWatcher() {
        fragmentBinding.edSearchUsers.addTextChangedListener {
            adapterUsers?.listUsers?.clear()
            if (it?.isNotEmpty() == true) {
                textSearch = it.toString()
                val mutableMap = mutableMapOf(
                    "per_page" to perPage.toString(),
                    "page" to "1",
                    "q" to it.toString(),
                    "order" to "asc"
                )

                viewModelHome.getListUsersOnNext(mutableMap)
            }
        }
    }

    private fun initObserver() {
        viewModelHome.observerGetUsers()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        showView(fragmentBinding.pbList)
                    }
                    is UiState.Success -> {
                        val dataItem = it.data.items
                        if (dataItem?.isNotEmpty() == true) {
                            totalCount = it.data.total_count!!
                            totalSizeList = dataItem.size
                            initAdapter(dataItem)
                        }
                    }
                    is UiState.Failure -> {
                        hideView(fragmentBinding.pbList)
                        it.throwable.printStackTrace()
                        showToast("error throw", requireContext())
                    }
                }
            }

        viewModelHome.observerGetUsersOnNext()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        adapterUsers?.showLoading()
                    }
                    is UiState.Success -> {
                        val dataItem = it.data.items
                        if (dataItem!!.isNotEmpty()) {
                            totalCount = it.data.total_count!!
                            totalSizeList = dataItem.size
                            listUsers.addAll(dataItem)
                            adapterUsers?.apply {
                                hideLoading()
                                notifyDataSetChanged()
                            }
                        }

                    }
                    is UiState.Failure -> {
                        adapterUsers?.hideLoading()
                        it.throwable.printStackTrace()
                        showToast("error throw", requireContext())
                    }
                }
            }

    }

    private fun initAdapter(dataItem: List<ResponseSearchUsers.ItemUsers?>?) {
        listUsers = dataItem?.toMutableList()!!
        linearLayoutManager = LinearLayoutManager(requireContext())
        hideView(fragmentBinding.pbList)
        adapterUsers = AdapterUsers(
            listUsers,
            object : OnClickItem {
                override fun clickItem(any: Any, pos: Int) {
                    val usersItem = any as ResponseSearchUsers.ItemUsers
                    clickDetail(usersItem)
                }
            })


        setRvAdapterVertikalDefault(
            fragmentBinding.rvListUsers,
            linearLayoutManager,
            adapterUsers!!,
            object : OnScrollListener {
                override fun scrollRecyclerview() {
                    if (page != perPage) {
                        page++
                        val mutableMap = mutableMapOf(
                            "per_page" to perPage.toString(),
                            "page" to page.toString(),
                            "q" to textSearch,
                            "order" to "asc"
                        )

                        viewModelHome.getListUsersOnNext(mutableMap)
                    } else adapterUsers?.setLoadings(false)
                }
            }
        )

        adapterUsers?.setLoadings(true)
    }

    private fun clickDetail(usersItem: ResponseSearchUsers.ItemUsers) {
        val bundle = Bundle()
        bundle.putParcelable("users", usersItem)
        val usersDetailFragment = UsersDetailFragment()
        usersDetailFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, usersDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initCallApi() {
        callApiUsers(perPage, page)
    }

    private fun callApiUsers(perPage: Int, page: Int) {
        val mutableMap = mutableMapOf(
            "per_page" to perPage.toString(),
            "page" to page.toString(),
            "q" to "arba",
            "order" to "asc"
        )

        viewModelHome.getListUsers(mutableMap)
    }
}