package com.example.padel.composables.newsPage

import androidx.compose.animation.core.snap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.padel.R

@Composable
fun newsCard(modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .width(250.dp)
        .padding(start = 20.dp, end = 20.dp)) {
        Box(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .zIndex(2f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF924974), Color(0xFFe38378)
                        )
                    ), shape = RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp)
                )
                .align(Alignment.BottomStart)
        ) {
            Text(
                "Court 1",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .matchParentSize()
                    .padding(start = 70.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .zIndex(2f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF924974), Color(0xFFe38378)
                        )
                    ), shape = RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp)
                )
                .align(Alignment.BottomStart)
        ) {
            Text(
                "12:00 - 13:00",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .matchParentSize()
                    .padding(start = 10.dp),
                fontWeight = FontWeight.Bold,
            )
        }

        Image(
            painter = painterResource(id = R.drawable.padel),
            contentDescription = "padel image",
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(10.dp))
                .zIndex(1f),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun LazyNewsRow() {
    var lazyListState = rememberLazyListState()
    var snapFlyingBehaviour = rememberSnapFlingBehavior(lazyListState = lazyListState )
    Box() {
        LazyRow(state = lazyListState, flingBehavior = snapFlyingBehaviour) {
            items(5) {
                newsCard()
            }

        }
    }
}


@Preview
@Composable
fun newsPreview() {
    LazyNewsRow()
}