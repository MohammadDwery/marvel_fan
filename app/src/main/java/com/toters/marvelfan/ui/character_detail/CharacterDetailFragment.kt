package com.toters.marvelfan.ui.character_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.toters.marvelfan.R
import com.toters.marvelfan.data.model.BaseModel
import com.toters.marvelfan.data.model.CharacterModel
import com.toters.marvelfan.data.network.DataResourceStatus
import com.toters.marvelfan.databinding.FragmentCharacterDetailBinding
import com.toters.marvelfan.databinding.ItemNetworkStateBinding
import com.toters.marvelfan.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(), View.OnClickListener {

    private val viewModel: CharacterDetailViewModel by viewModel()
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val comicsAdapter by lazy { ComicsRecyclerAdapter(resources.displayMetrics.widthPixels) }
    private val seriesAdapter by lazy { ComicsRecyclerAdapter(resources.displayMetrics.widthPixels) }
    private val eventsAdapter by lazy { ComicsRecyclerAdapter(resources.displayMetrics.widthPixels) }
    private val storiesAdapter by lazy { ComicsRecyclerAdapter(resources.displayMetrics.widthPixels) }

    private val itemDecorator by lazy {
        MarginDecoration(
            requireContext(),
            R.dimen.dp_16
        )
    }

    override fun layoutId(): Int = R.layout.fragment_character_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun initFragment() {
        bindInitialState()
        callInitialAPIs()
        initRecyclerViews()
        initObservers()
        setOnClickListeners()
    }

    private fun initRecyclerViews() {
        binding.comicsComponent.itemsRecyclerView.addItemDecoration(itemDecorator)
        with(comicsAdapter){
            binding.comicsComponent.itemsRecyclerView.adapter = this
            listener = { comicsModel ->
                Toast.makeText(context, comicsModel.title, Toast.LENGTH_SHORT).show()
            }
        }

        binding.eventsComponent.itemsRecyclerView.addItemDecoration(itemDecorator)
        with(eventsAdapter){
            binding.eventsComponent.itemsRecyclerView.adapter = this
            listener = { eventModel ->
                Toast.makeText(context, eventModel.title, Toast.LENGTH_SHORT).show()
            }
        }

        binding.seriesComponent.itemsRecyclerView.addItemDecoration(itemDecorator)
        with(seriesAdapter){
            binding.seriesComponent.itemsRecyclerView.adapter = this
            listener = { serieModel ->
                Toast.makeText(context, serieModel.title, Toast.LENGTH_SHORT).show()
            }
        }

        binding.storiesComponent.itemsRecyclerView.addItemDecoration(itemDecorator)
        with(storiesAdapter){
            binding.storiesComponent.itemsRecyclerView.adapter = this
            listener = { storyModel ->
                Toast.makeText(context, storyModel.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindInitialState(){
        ViewCompat.setTransitionName(binding.characterCard.characterImageView, "image_${args.character.id}")
        ViewCompat.setTransitionName(binding.characterCard.nameCard, "name_${args.character.id}")
        bindSuccessCharacterDetails(args.character)
        binding.comicsComponent.title = getString(R.string.comics)
        binding.eventsComponent.title = getString(R.string.events)
        binding.seriesComponent.title = getString(R.string.series)
        binding.storiesComponent.title = getString(R.string.stories)
    }

    private fun callInitialAPIs() {
        viewModel.getCharacter(id = args.character.id)
        viewModel.getCharacterComics(id = args.character.id)
        viewModel.getCharacterEvents(id = args.character.id)
        viewModel.getCharacterSeries(id = args.character.id)
        viewModel.getCharacterStories(id = args.character.id)
    }

    private fun initObservers() {
        launchOnLifecycleScope {
            with(viewModel) {
                characterDetailsDataResFlow.collectLatest {
                    when(it.status){
                        DataResourceStatus.LOADING -> bindLoadingCharacterDetails()
                        DataResourceStatus.SUCCESS -> bindSuccessCharacterDetails(it.data!!)
                        DataResourceStatus.FAILURE -> {
                            // TODO: implement the state of failure API's call
                        }
                        else -> {}
                    }
                }
                comicsDataResFlow.collectLatest {
                    when(it.status){
                        DataResourceStatus.LOADING -> bindLoadingState(binding.comicsComponent.networkStateLayout)
                        DataResourceStatus.SUCCESS -> bindSuccessState(binding.comicsComponent.networkStateLayout, it.data!!, comicsAdapter)
                        DataResourceStatus.NO_RESULTS -> bindNoResultsState(binding.comicsComponent.networkStateLayout, R.string.no_comics_message)
                        DataResourceStatus.FAILURE -> bindFailureState(binding.comicsComponent.networkStateLayout, it.message!!)
                        else -> {}
                    }
                }
                eventsDataResFlow.collectLatest {
                    when(it.status){
                        DataResourceStatus.LOADING -> bindLoadingState(binding.eventsComponent.networkStateLayout)
                        DataResourceStatus.SUCCESS -> bindSuccessState(binding.eventsComponent.networkStateLayout, it.data!!, eventsAdapter)
                        DataResourceStatus.NO_RESULTS -> bindNoResultsState(binding.eventsComponent.networkStateLayout, R.string.no_events_message)
                        DataResourceStatus.FAILURE -> bindFailureState(binding.eventsComponent.networkStateLayout, it.message!!)
                        else -> {}
                    }
                }
                seriesDataResFlow.collectLatest {
                    when(it.status){
                        DataResourceStatus.LOADING -> bindLoadingState(binding.seriesComponent.networkStateLayout)
                        DataResourceStatus.SUCCESS -> bindSuccessState(binding.seriesComponent.networkStateLayout, it.data!!, seriesAdapter)
                        DataResourceStatus.NO_RESULTS -> bindNoResultsState(binding.seriesComponent.networkStateLayout, R.string.no_series_message)
                        DataResourceStatus.FAILURE -> bindFailureState(binding.seriesComponent.networkStateLayout, it.message!!)
                        else -> {}
                    }
                }
                storiesDataResFlow.collectLatest {
                    when(it.status){
                        DataResourceStatus.LOADING -> bindLoadingState(binding.storiesComponent.networkStateLayout)
                        DataResourceStatus.SUCCESS -> bindSuccessState(binding.storiesComponent.networkStateLayout, it.data!!, storiesAdapter)
                        DataResourceStatus.NO_RESULTS -> bindNoResultsState(binding.storiesComponent.networkStateLayout, R.string.no_stories_message)
                        DataResourceStatus.FAILURE -> bindFailureState(binding.storiesComponent.networkStateLayout, it.message!!)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.backBtn.setOnClickListener(this)
    }

    private fun bindLoadingCharacterDetails() {
        binding.characterNetworkStateLayout.root.visibility = View.VISIBLE
        binding.characterNetworkStateLayout.progressBar.visibility = View.VISIBLE
    }

    private fun bindSuccessCharacterDetails(characterModel: CharacterModel) {
        binding.characterNetworkStateLayout.root.visibility = View.GONE
        binding.character = characterModel
        binding.characterCard.character = characterModel
        binding.descriptionTitleTv.visibility = if (characterModel.description.isNotEmpty()) View.VISIBLE else View.GONE
        binding.descriptionTv.visibility = if (characterModel.description.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun bindLoadingState(networkStateLayout: ItemNetworkStateBinding) {
        with(networkStateLayout){
            root.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            errorMsg.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    private fun bindSuccessState(networkStateLayout: ItemNetworkStateBinding, items: List<BaseModel>, adapter: ComicsRecyclerAdapter) {
        networkStateLayout.root.visibility = View.GONE
        adapter.setList(items)
    }

    private fun bindNoResultsState(networkStateLayout: ItemNetworkStateBinding, messageResId: Int) {
        with(networkStateLayout) {
            root.visibility = View.VISIBLE
            errorMsg.visibility = View.VISIBLE
            retryButton.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorMsg.text = getString(messageResId)
        }
    }

    private fun bindFailureState(networkStateLayout: ItemNetworkStateBinding, message: String) {
        with(networkStateLayout) {
            root.visibility = android.view.View.VISIBLE
            errorMsg.visibility = android.view.View.VISIBLE
            retryButton.visibility = android.view.View.VISIBLE
            progressBar.visibility = android.view.View.GONE
            errorMsg.text = message
            retryButton.setOnClickListener {
                // ToDO: implement retry behavior
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.back_btn -> activity?.onBackPressed()
        }
    }
}