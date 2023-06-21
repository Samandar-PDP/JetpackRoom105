package com.sdk.roomcomposemvvm.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sdk.roomcomposemvvm.MainActivity
import com.sdk.roomcomposemvvm.database.User
import com.sdk.roomcomposemvvm.viewmodel.MainViewModel
import com.sdk.roomcomposemvvm.viewmodel.NoteEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(
    id: Int,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    val snackBar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var user by remember { mutableStateOf<User?>(null) }
    val context = LocalContext.current as MainActivity

    if (id != 0) {
        LaunchedEffect(key1 = Unit) {
            viewModel.getUserById(id)
                .observe(context) {
                    user = it
                }
        }
    }
    user?.let {
        fullName = it.fullName
        age = it.age.toString()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBar)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (id == 0) "Add New User" else "Update User"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            if (fullName.isNotBlank() && age.isNotBlank()) {
                                viewModel.onEvent(NoteEvent.OnSaveUser(User(fullName, age.toInt())))
                                scope.launch {
                                    snackBar.showSnackbar("Saved!")
                                }
                            } else {
                                scope.launch {
                                    snackBar.showSnackbar("Enter data correctly")
                                }
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "")
                    }
                }
            )
        }
    ) { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                label = {
                    Text(text = "Full Name")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = {
                    Text(text = "Age")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}