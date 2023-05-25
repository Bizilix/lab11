package com.topic3.android.reddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.topic3.android.reddit.components.ImagePost
import com.topic3.android.reddit.components.TextPost
import com.topic3.android.reddit.domain.model.PostModel
import com.topic3.android.reddit.domain.model.PostType
import com.topic3.android.reddit.viewmodel.MainViewModel


@Composable
fun HomeScreen(viewModel: MainViewModel) {

    val posts: List<PostModel> by viewModel.allPosts.observeAsState(listOf())

    LazyColumn(
        modifier = Modifier.background(color = MaterialTheme.colors.secondary)
    ){
        items(posts){
            if(it.type == PostType.TEXT){
                TextPost(it)
            }else{
                ImagePost(it)
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

}
