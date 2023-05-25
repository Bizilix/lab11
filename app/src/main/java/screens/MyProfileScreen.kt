package com.topic3.android.reddit.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.topic3.android.reddit.R
import com.topic3.android.reddit.appdrawer.ProfileInfo
import com.topic3.android.reddit.components.PostAction
import com.topic3.android.reddit.domain.model.PostModel
import com.topic3.android.reddit.routing.MyProfileRouter
import com.topic3.android.reddit.routing.MyProfileScreenType
import com.topic3.android.reddit.routing.RedditRouter

import com.topic3.android.reddit.viewmodel.MainViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

private val tabNames = listOf(R.string.posts, R.string.about)

@Composable
fun MyProfileScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (topAppBar, tabs, bodyContent) = createRefs()

        val colors = MaterialTheme.colors

        TopAppBar(
            title = {
                Text(
                    fontSize = 12.sp,
                    text = stringResource(R.string.default_username),
                    color = colors.primaryVariant
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { RedditRouter.goBack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = colors.primaryVariant,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            backgroundColor = colors.primary,
            elevation = 0.dp,
            modifier = modifier
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(48.dp)
                .background(Color.Blue)
        )

        MyProfileTabs(
            modifier = modifier.constrainAs(tabs) {
                top.linkTo(topAppBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Surface(
            modifier = modifier
                .constrainAs(bodyContent) {
                    top.linkTo(tabs.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 68.dp)
        ) {

            Crossfade(targetState = MyProfileRouter.currentScreen) { screen ->
                when (screen.value) {
                    MyProfileScreenType.Posts -> MyProfilePosts(modifier, viewModel)
                    MyProfileScreenType.About -> MyProfileAbout()
                }
            }
        }
    }
}

@Composable
fun MyProfileTabs(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    TabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        tabNames.forEachIndexed { index, nameResource ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    changeScreen(index)
                }
            ) {
                Text(
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 12.sp,
                    text = stringResource(nameResource),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

private fun changeScreen(index: Int) {
    return when (index) {
        0 -> MyProfileRouter.navigateTo(MyProfileScreenType.Posts)
        else -> MyProfileRouter.navigateTo(MyProfileScreenType.About)
    }
}

