package com.example.github.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.github.model.Repo
import com.example.github.viewmodel.RepoViewModel

@Composable
fun HomeScreen(viewModel: RepoViewModel, onRepoClick: (Repo) -> Unit) {
    val query = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.loadOfflineRepos() // Call to load offline repos
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        TextField(
            value = query.value,
            onValueChange = { query.value = it },
            label = { Text("Search Repos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )

        // Search button
        Button(
            onClick = { viewModel.searchRepos(query.value, 1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Search")
        }

        // Repositories list
        val repos = viewModel.repos.observeAsState(emptyList())
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repos.value) { repo ->
                RepoItem(repo = repo, onClick = { onRepoClick(repo) })
            }
        }
    }
}

@Composable
fun RepoItem(repo: Repo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar image with rounded corners
            Image(
                painter = rememberAsyncImagePainter(repo.owner.avatar_url),
                contentDescription = "Owner Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            // Repo information
            Column {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = repo.description ?: "No description",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}


