package com.example.github.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.github.model.Contributor
import com.example.github.model.Repo
import com.example.github.viewmodel.RepoViewModel

@Composable
fun RepoDetailScreen(repo: Repo, viewModel: RepoViewModel) {
    var showWebView by remember { mutableStateOf(false) }

    if (showWebView) {
        WebViewComponent(repo.html_url)
    } else {
        RepoDetailContent(repo, viewModel, onOpenWebClick = {
            showWebView = true
        })
    }
}

@Composable
fun RepoDetailContent(repo: Repo, viewModel: RepoViewModel, onOpenWebClick: () -> Unit) {
    LaunchedEffect(repo.contributors_url) { // Ensure correct spelling
        viewModel.loadContributors(repo.contributors_url)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Repository owner image (avatar)
        Image(
            painter = rememberAsyncImagePainter(repo.owner.avatar_url),
            contentDescription = "Repository Owner Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Repository name
        Text(
            text = repo.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Repository description
        Text(
            text = repo.description ?: "No description available",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Button to open the project link in WebView
        Button(
            onClick = onOpenWebClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Open in Browser",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("project link", fontSize = 16.sp)
        }

        // Observe contributors from the view model
        val contributors by viewModel.contributors.observeAsState(emptyList())
        Text(
            text = "Contributors",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display the contributors in a LazyColumn
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(contributors) { contributor ->
                ContributorItem(contributor = contributor)
            }
        }
    }
}

@Composable
fun ContributorItem(contributor: Contributor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contributor's avatar image with circular shape
            Image(
                painter = rememberAsyncImagePainter(contributor.avatar_url),
                contentDescription = "Contributor Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.small)
                    .padding(end = 16.dp)
            )

            // Contributor's name
            Text(
                text = contributor.login,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun WebViewComponent(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}



