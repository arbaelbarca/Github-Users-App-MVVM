package com.arbaelbarca.githublistapp.ui.view.users

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbaelbarca.githublistapp.R
import com.arbaelbarca.githublistapp.databinding.FragmentUsersDetailBinding
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUserName
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsersRepo
import com.arbaelbarca.githublistapp.presentation.onclick.OnScrollListener
import com.arbaelbarca.githublistapp.presentation.viewmodel.users.ViewModelUsers
import com.arbaelbarca.githublistapp.ui.adapter.AdapterUsersRepo
import com.arbaelbarca.githublistapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UsersDetailFragment : Fragment(R.layout.fragment_users_detail) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val perPage = 10
    var page = 1

    var bundle: Bundle? = null
    var getUsers: ResponseSearchUsers.ItemUsers? = null

    val viewModelHome: ViewModelUsers by viewModels()
    val fragmentBinding: FragmentUsersDetailBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsersDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsersDetailFragment().apply {
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
        getUsers = bundle?.getParcelable("users")

        initCallApi()
        initObserver()
    }

    private fun initObserver() {
        viewModelHome.observerGetUsersName()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        showView(fragmentBinding.pbList)
                    }
                    is UiState.Success -> {
                        hideView(fragmentBinding.pbList)
                        val dataItem = it.data
                        initDetailUsers(dataItem)

                    }
                    is UiState.Failure -> {
                        hideView(fragmentBinding.pbList)
                        showToast("error throw", requireContext())
                    }
                }
            }
        viewModelHome.observerGetUsersRepo()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        showView(fragmentBinding.pbList)
                    }
                    is UiState.Success -> {
                        hideView(fragmentBinding.pbList)
                        val dataItem = it.data
                        if (dataItem.isNotEmpty())
                            initAdapter(dataItem)

                    }
                    is UiState.Failure -> {
                        hideView(fragmentBinding.pbList)
                        showToast("error throw", requireContext())
                    }
                }
            }
    }

    private fun initAdapter(dataItem: List<ResponseUsersRepo.ResponseUsersRepoItem>) {
        val adapterUsersRepo =
            AdapterUsersRepo(dataItem as MutableList<ResponseUsersRepo.ResponseUsersRepoItem>)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        setRvAdapterVertikalDefault(
            fragmentBinding.rvListUsersRepo,
            linearLayoutManager,
            adapterUsersRepo,
            object : OnScrollListener {
                override fun scrollRecyclerview() {

                }
            }
        )
    }

    private fun initDetailUsers(dataItem: ResponseUserName) {
        showView(fragmentBinding.llDetailUsers)
        fragmentBinding.imgUsers.loadImageUrl(dataItem.avatar_url.toString(), requireContext())
        fragmentBinding.tvNameUsersDetail.text = dataItem.name
        fragmentBinding.tvSummaryUsersDetail.text = dataItem.bio.toString()
        fragmentBinding.tvTotalFollowers.text = "${dataItem.followers} Followers ."
        fragmentBinding.tvTotalFollowing.text = "${dataItem.followers} Following"
        fragmentBinding.tvLocationDetail.text = dataItem.location
        fragmentBinding.tvEmailUsersDetail.text = dataItem.email
        fragmentBinding.tvUsernameDetail.text = dataItem.company

        val mutableMap = mutableMapOf(
            "per_page" to perPage.toString(),
            "page" to page.toString(),
        )
        viewModelHome.getDetailUersRepo(dataItem.login.toString(), mutableMap)
    }

    private fun initCallApi() {
        viewModelHome.getDetailUsername(getUsers?.login.toString())
    }

}