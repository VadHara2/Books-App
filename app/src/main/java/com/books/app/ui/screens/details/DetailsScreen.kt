package com.books.app.ui.screens.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.books.app.R
import com.books.app.data.model.Book
import com.books.app.data.model.BookId
import com.books.app.data.model.Category
import com.books.app.data.model.ImageUrl
import com.books.app.ui.screens.library.CategoryRow
import kotlin.math.abs


private const val TAG = "DetailsScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel(),
    bookId: BookId
) {
    val state by viewModel.state.collectAsState()
    viewModel.handleIntent(DetailsIntent.BookClicked(bookId))

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Image(
            painter = painterResource(id = R.drawable.details_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Surface(
            color = Color.Transparent,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            if (state.isLoading) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val initialPage = state.allBooks.indexOfFirst { it.id == bookId }
                val pagerState = rememberPagerState(
                    pageCount = { state.allBooks.size },
                    initialPage = initialPage
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    val pageSize = 200.dp

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            start = (screenWidth - pageSize) / 2,
                            end = (screenWidth - pageSize) / 2
                        ),
                        pageSize = PageSize.Fixed(220.dp)

                    ) { page ->
                        val pageOffset = calculatePageOffset(page, pagerState)
                        val scale = lerp(0.8f, 1f, 1f - abs(pageOffset))


                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .scale(scale),
                            contentAlignment = Alignment.Center
                        ) {
                            BookImage(url = state.allBooks[page].coverUrl)
                        }


                    }

                    BookDetailContent(book = state.chosenBook, recommended = state.recommendedBooks)

                }

                LaunchedEffect(pagerState.currentPage) {
                    viewModel.handleIntent(DetailsIntent.BookClicked(state.allBooks[pagerState.currentPage].id))
                }
            }


        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
private fun calculatePageOffset(page: Int, pagerState: PagerState): Float {
    val pageOffset = (pagerState.currentPage - page).toFloat()
    return pageOffset.coerceIn(-1f, 1f)
}

@Composable
fun BookImage(modifier: Modifier = Modifier, url: ImageUrl) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = modifier
            .size(240.dp, 300.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BookDetailContent(book: Book, recommended: List<Book>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = book.name,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight(700),
            fontSize = 22.sp
        )

        Text(
            text = book.author, color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight(600),
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp, 20.dp))
                .background(Color.White)
                .padding(vertical = 16.dp, horizontal = 32.dp)
        ) {
            ChipBox(info = book.views, title = "Readers")
            ChipBox(info = book.likes, title = "Likes")
            ChipBox(info = book.quotes, title = "Quotes")
            ChipBox(info = book.genre, title = "Genre")
        }

        SummaryBox(text = book.summary)

        YouWillAlsoLike(books = recommended)

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 32.dp, vertical = 16.dp),

        ) {
            Text(text = "Read Now",
                fontWeight = FontWeight(800),
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ChipBox(modifier: Modifier = Modifier, info: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = info,
            color = Color(0xFF0B080F),
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
        )
        Text(
            text = title,
            color = Color(0xFFD9D5D6),
            fontWeight = FontWeight(600),
            fontSize = 12.sp
        )
    }
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFD9D5D6))
                .fillMaxWidth()
                .height(1.dp)
        )
    }

}


@Composable
fun SummaryBox(modifier: Modifier = Modifier, text: String) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)

    ) {

        Box(
            modifier = Modifier
                .background(Color(0xFFD9D5D6))
                .fillMaxWidth()
                .height(1.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Summary",
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            fontWeight = FontWeight(600),
            fontSize = 14.sp,
            textAlign = TextAlign.Left,
            color = Color(0xFF393637)
        )

        Box(
            modifier = Modifier
                .background(Color(0xFFD9D5D6))
                .fillMaxWidth()
                .height(1.dp)
        )
    }

}


@Composable
fun YouWillAlsoLike(modifier: Modifier = Modifier, books: List<Book>) {
    CategoryRow(
        category = Category(
            name = "You will also like",
            books = books
        ),
        headerColor = Color(0xFF0B080F),
        titlesColor = Color(0xFF393637)
    )
}


