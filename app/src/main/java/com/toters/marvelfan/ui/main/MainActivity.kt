package com.toters.marvelfan.ui.main

import org.koin.android.viewmodel.ext.android.viewModel
import com.toters.marvelfan.R
import com.toters.marvelfan.databinding.ActivityMainBinding
import com.toters.marvelfan.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun layoutId(): Int = R.layout.activity_main
    override fun getVM(): MainViewModel = mainViewModel
    override fun bindVM(binding: ActivityMainBinding, vm: MainViewModel) = Unit
}