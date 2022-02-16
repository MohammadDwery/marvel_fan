package com.toters.marvelfan.ui.characters

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.paging.LoadState
import com.toters.marvelfan.ui.base.BaseFragment
import com.toters.marvelfan.R
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.databinding.CharacterListItemBinding
import com.toters.marvelfan.databinding.FragmentCharactersBinding
import com.toters.marvelfan.ui.util.PagingLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class CharactersFragment : BaseFragment<FragmentCharactersBinding, CharactersViewModel>(),
    CharactersAdapter.CharacterClickListener {
    private val viewModel: CharactersViewModel by viewModel()

    private val characterAdapter by lazy { CharactersAdapter() }

    private val itemDecorator by lazy {
        CharactersMarginDecoration(
            requireContext(),
            R.dimen.dp_16
        )
    }

    override fun layoutId(): Int = R.layout.fragment_characters

    override fun initFragment() {
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        with(binding) {
            with(characterAdapter) {
                charactersRecyclerView.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                    addItemDecoration(itemDecorator)
                    adapter = withLoadStateHeaderAndFooter(
                        header = PagingLoadStateAdapter(characterAdapter),
                        footer = PagingLoadStateAdapter(characterAdapter)
                    )
                }
                swipeRefresh.setOnRefreshListener { refresh() }
                characterClickListener = this@CharactersFragment
            }
        }
    }

    private fun initObservers() = with(viewModel) {
        launchOnLifecycleScope {
            charactersFlow.collectLatest {
                characterAdapter.submitData(it)
            }
        }

        launchOnLifecycleScope {
            characterAdapter.loadStateFlow.map { it.refresh }.collectLatest {
                when (it) {
                    is LoadState.Loading -> bindLoadingState()
                    is LoadState.Error -> bindFailureState(it.error.message?:"")
                    is LoadState.NotLoading -> bindNotLoadingState()
                }
            }
        }
    }

    private fun bindLoadingState() {
        binding.swipeRefresh.isRefreshing = true

        with(binding.networkStateLayout) {
            root.visibility = View.VISIBLE
            errorMsg.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    private fun bindFailureState(message: String) {
        binding.swipeRefresh.isRefreshing = false

        with(binding.networkStateLayout) {
            root.visibility = View.VISIBLE
            errorMsg.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            errorMsg.text = message

            retryButton.setOnClickListener {
                characterAdapter.refresh()
            }
        }
    }

    private fun bindNotLoadingState() {
        binding.swipeRefresh.isRefreshing = false

        with(binding.networkStateLayout) {
            root.visibility = View.GONE
        }
    }

    override fun onCharacterClicked(binding: CharacterListItemBinding, character: CharacterModel) {
        val extras = FragmentNavigatorExtras(
            binding.characterImageView to "image_${character.id}",
            binding.nameCard to "name_${character.id}",
        )
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersToCharacterDetail(character),
            extras
        )
    }
}