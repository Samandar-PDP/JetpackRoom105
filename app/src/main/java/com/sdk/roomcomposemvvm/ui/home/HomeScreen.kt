package com.sdk.roomcomposemvvm.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdk.roomcomposemvvm.database.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userList: List<User>,
    onItemClick: (Long) -> Unit,
    onDelete: (User) -> Unit,
    onNavigate: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User List")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigate) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = userList,
                key = { _, u -> u.id }
            ) { i, u ->
                UserItem(
                    user = u,
                    index = i,
                    onItemClick = onItemClick,
                    onDelete = onDelete
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserItem(
    user: User,
    index: Int,
    onItemClick: (Long) -> Unit,
    onDelete: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = {
            onItemClick(user.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = index.plus(1).toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            IconButton(
                onClick = {
                    onDelete(user)
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}