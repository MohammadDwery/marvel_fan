package com.toters.marvelfan.ui.characters

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

class CharactersFragment : BaseFragment<FragmentCharactersBinding, CharactersViewModel>(),
    CharactersAdapter.CharacterClickListener {
    private val viewModel: CharactersViewModel by viewModel()

    private val characterAdapter by lazy {
        CharactersAdapter()
    }

    private val itemDecorator by lazy {
        CharactersMarginDecoration(
            requireContext(),
            R.dimen.dp_16
        )
    }

    override fun layoutId(): Int = R.layout.fragment_characters

    override fun initFragment() {
        with(binding) {
            with(characterAdapter) {
                rvCharacters.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                    addItemDecoration(itemDecorator)
                }
                rvCharacters.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener { refresh() }

                characterClickListener = this@CharactersFragment

                with(viewModel) {
                    launchOnLifecycleScope {
                        charactersFlow.collectLatest { submitData(it) }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
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