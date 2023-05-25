package com.topic3.android.reddit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.topic3.android.reddit.R
import com.topic3.android.reddit.domain.model.PostModel
import com.topic3.android.reddit.routing.RedditRouter
import com.topic3.android.reddit.routing.Screen
import com.topic3.android.reddit.viewmodel.MainViewModel

@Composable
fun AddScreen(viewModel: MainViewModel) {

    val selectedCommunity: String by viewModel.selectedCommunity.observeAsState("")

    var post by remember { mutableStateOf(PostModel.EMPTY) }

    Column(modifier = Modifier.fillMaxSize()) {

        CommunityPicker(selectedCommunity)

        TitleTextField(post.title) { newTitle -> post = post.copy(title = newTitle) }

        BodyTextField(post.text) { newContent -> post = post.copy(text = newContent) }

        AddPostButton(selectedCommunity.isNotEmpty() && post.title.isNotEmpty()) {
            viewModel.savePost(post)
            RedditRouter.navigateTo(com.topic3.android.reddit.routing.Screen.Home)
        }
    }
}

/**
 * Input view for the post title
 */
