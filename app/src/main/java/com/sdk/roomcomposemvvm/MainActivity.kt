package com.sdk.roomcomposemvvm

import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sdk.roomcomposemvvm.database.User
import com.sdk.roomcomposemvvm.database.UserDao
import com.sdk.roomcomposemvvm.database.UserDatabase
import com.sdk.roomcomposemvvm.repository.NoteRepository
import com.sdk.roomcomposemvvm.ui.detail.DetailScreen
import com.sdk.roomcomposemvvm.ui.home.HomeScreen
import com.sdk.roomcomposemvvm.ui.theme.RoomComposeMVVMTheme
import com.sdk.roomcomposemvvm.viewmodel.MainViewModel
import com.sdk.roomcomposemvvm.viewmodel.MainViewModelFactory
import com.sdk.roomcomposemvvm.viewmodel.NoteEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = NoteRepository(UserDatabase(this).dao)
        val viewModel: MainViewModel by viewModels {
            MainViewModelFactory(repository)
        }
        setContent {
            RoomComposeMVVMTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = "home_screen"
                ) {
                    composable(route = "home_screen") {
                        val userList by viewModel.noteList.observeAsState()
                        HomeScreen(
                            userList = userList ?: emptyList(),
                            onItemClick = {
                                navHostController.navigate("detail_screen/$it")
                            },
                            onNavigate = {
                                navHostController.navigate("detail_screen/0")
                            },
                            onDelete = {
                                viewModel.onEvent(NoteEvent.OnDeleteUser(it))
                            }
                        )
                    }
                    composable(
                        route = "detail_screen/{id}",
                        arguments = listOf(
                            navArgument(
                                name = "id"
                            ) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val id = it.arguments?.getInt("id") ?: 0

                        DetailScreen(
                            id = id,
                            viewModel = viewModel,
                            onBack = {
                                navHostController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
