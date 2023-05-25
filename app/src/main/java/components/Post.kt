package com.topic3.android.reddit.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic3.android.reddit.R
import com.topic3.android.reddit.domain.model.PostModel
import com.topic3.android.reddit.domain.model.PostModel.Companion.DEFAULT_POST

@Composable
fun TextPost(post: PostModel) {
    Post(post) {
        TextContent(post.text)
    }
}

@Composable
fun ImagePost(post: PostModel) {
    Post(post) {
        ImageContent(post.image ?: R.drawable.compose_course)
    }
}

@Composable
fun Post(post: PostModel, content: @Composable () -> Unit = {}) {
    Card(shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(
                top = 8.dp, bottom = 8.dp
            )
        ) {
            Header(post)
            Spacer(modifier = Modifier.height(4.dp))
            content.invoke()
            Spacer(modifier = Modifier.height(8.dp))
            PostActions(post)
        }
    }

}

@Composable
fun Header(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {}
){
    Row(modifier = Modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = stringResource(id = R.string.subreddits),
            Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.subreddit_header, post.subreddit),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = stringResource(R.string.post_header, post.username, post.postedTime),
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        JoinButton(onJoinButtonClick)
        MoreActionsMenu()
    }
    Title(text = post.title)
}

