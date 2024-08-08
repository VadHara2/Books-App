package com.books.app.ui.screens.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.books.app.data.model.Banner
import com.books.app.data.model.Category
import com.books.app.ui.theme.MyApplicationTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.books.app.data.model.Book
import com.books.app.data.model.ImageUrl
import com.books.app.ui.theme.BackgroundBlack
import com.books.app.ui.theme.AccentPink
import com.books.app.ui.theme.InactiveGrey
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()



    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "Library",
                fontWeight = FontWeight(700),
                fontSize = 20.sp
            )
        }, actions = { }, colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = BackgroundBlack,
            titleContentColor = AccentPink,
        )
        )
    }) { innerPadding ->

        Surface(
            color = BackgroundBlack,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            if (state.isLoading) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    BannerWithIndicator(state.banners)
                    state.categories.forEach { category ->
                        CategoryRow(category)
                    }
                }
            }


        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun BannerWithIndicator(banners: List<Banner>) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % banners.size)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            BannerImage(
                modifier = Modifier.fillMaxSize(),
                image = banners[page].cover
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState, pageCount = banners.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            activeColor = AccentPink,
            inactiveColor = InactiveGrey
        )
    }
}

@Composable
fun BannerImage(modifier: Modifier = Modifier, image: ImageUrl) {
    Image(
        painter = rememberAsyncImagePainter(image),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun CategoryRow(category: Category) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = category.name,
            color = Color.White,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight(700),
            fontSize = 22.sp
        )
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.books.size) { index ->
                val book = category.books[index]
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(book.coverUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp, 150.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = book.name, color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight(600),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(top = 6.dp)
                            .width(120.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}



