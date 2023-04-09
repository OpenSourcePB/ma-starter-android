package az.pashabank.presentation.flow.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import az.pashabank.starter.presentation.R
import az.pashabank.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel by viewModel<MainViewModel>()

    private val navController: NavController by lazy {
        findNavController(R.id.main_nav_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController.setGraph(R.navigation.main_graph)
    }
}