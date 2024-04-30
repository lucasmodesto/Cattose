package br.com.cattose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.cattose.app.core.ui.theme.CattoTheme
import br.com.cattose.app.feature.detail.navigation.detailScreen
import br.com.cattose.app.feature.detail.navigation.navigateToDetail
import br.com.cattose.app.feature.list.navigation.LIST_SCREEN_ROUTE
import br.com.cattose.app.feature.list.navigation.listScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CattoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = LIST_SCREEN_ROUTE,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            listScreen(
                                navGraphBuilder = this@NavHost,
                                onItemClick = {
                                    navController.navigateToDetail(it.id, it.imageUrl)
                                })
                            detailScreen(
                                navGraphBuilder = this@NavHost,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
