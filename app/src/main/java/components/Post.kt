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
    //TODO add your code here
    Card(shape = MaterialTheme.shapes.large){
        Column(
            modifier = Modifier.padding(
                top = 8.dp, bottom = 8.dp
            )
        ){
            Header(post)
            Spacer(modifier = Modifier.height(4.dp))
            content.invoke()
            Spacer(modifier = Modifier.height(8.dp))
            PostActions(post)
        }
    }
}

@Composable
fun Header(post: PostModel) {
    //TODO add your code here
    Row(modifier = Modifier.padding(start = 16.dp)){
        Image(
            ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = stringResource(id = R.string.subreddits),
            Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)){
            Text(
                text = stringResource(
                    R.string.subreddit_header,
                    post.subreddit
                ),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = stringResource(
                    R.string.post_header,
                    post.username, post.postedTime
                ),
                color = Color.Gray
            )
        }
        MoreActionsMenu()
    }
    Title(text = post.title)
}

@Composable
fun MoreActionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.DarkGray,
                contentDescription = stringResource(id = R.string.more_actions)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            CustomDropdownMenuItem(
                vectorResourceId = R.drawable.ic_baseline_bookmark_24,
                text = stringResource(id = R.string.save)
            )
        }
    }
}

@Composable
fun CustomDropdownMenuItem(
    @DrawableRes vectorResourceId: Int,
    color: Color = Color.Black,
    text: String,
    onClickAction: () -> Unit = {}
) {
    DropdownMenuItem(onClick = onClickAction) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = vectorResourceId),
                tint = color,
                contentDescription = stringResource(id = R.string.save)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = color)
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        maxLines = 3,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun TextContent(text: String) {
    Text(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp
        ),
        text = text,
        color = Color.Gray,
        fontSize = 12.sp,
        maxLines = 3
    )
}

@Composable
fun ImageContent(image: Int) {
    val imageAsset = ImageBitmap.imageResource(id = image)
    Image(
        bitmap = imageAsset,
        contentDescription = stringResource(id = R.string.post_header_description),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAsset.width.toFloat() / imageAsset.height),
        contentScale = ContentScale.Crop
    )
}
