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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.books.app.data.model.Banner
import com.books.app.data.model.Category
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
import com.books.app.data.model.BookId
import com.books.app.data.model.ImageUrl
import com.books.app.ui.theme.BackgroundBlack
import com.books.app.ui.theme.AccentPink
import com.books.app.ui.theme.InactiveGrey
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay


@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HandleNavigationEvent(
        state.navigationEvent,
        navController,
        onClearNavigation = { viewModel.handleIntent(LibraryIntent.ClearNavigation) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LibraryTopAppBar()
        }
    ) { innerPadding ->
        LibraryContent(
            modifier = modifier.padding(innerPadding),
            state = state,
            onBookClicked = { bookId ->
                viewModel.handleIntent(LibraryIntent.BookClicked(bookId))
            }
        )
    }
}

@Composable
private fun HandleNavigationEvent(
    navigationEvent: LibraryState.NavigationEvent?,
    navController: NavController,
    onClearNavigation: () -> Unit
) {
    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            if (event is LibraryState.NavigationEvent.NavigateToDetails) {
                navController.navigate("detailScreen/${event.bookId}")
                onClearNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Library",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        actions = { },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = BackgroundBlack,
            titleContentColor = AccentPink,
        )
    )
}

@Composable
fun LibraryContent(
    modifier: Modifier = Modifier,
    state: LibraryState,
    onBookClicked: (BookId) -> Unit
) {
    Surface(
        color = BackgroundBlack,
        modifier = modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            LoadingContent(modifier)
        } else {
            LoadedContent(state, onBookClicked)
        }
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadedContent(
    state: LibraryState,
    onBookClicked: (BookId) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        BannerWithIndicator(
            banners = state.banners,
            onBookClicked = onBookClicked
        )
        state.categories.forEach { category ->
            CategoryRow(
                category = category,
                onBookClicked = onBookClicked
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun BannerWithIndicator(
    banners: List<Banner>,
    onBookClicked: (BookId) -> Unit
) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onBookClicked(banners[page].bookId) },
                image = banners[page].cover
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = banners.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            activeColor = AccentPink,
            inactiveColor = InactiveGrey
        )
    }
}

@Composable
fun BannerImage(
    modifier: Modifier = Modifier,
    image: ImageUrl
) {
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
fun CategoryRow(
    category: Category,
    onBookClicked: (BookId) -> Unit,
    headerColor: Color = Color.White,
    titlesColor: Color = Color.White.copy(alpha = 0.7f)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = category.name,
            color = headerColor,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.books.size) { index ->
                val book = category.books[index]
                BookItem(book, titlesColor) {
                    onBookClicked(book.id)
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    titlesColor: Color,
    onBookClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { onBookClicked() }
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
            text = book.name,
            color = titlesColor,
            fontWeight = FontWeight.Medium,
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




