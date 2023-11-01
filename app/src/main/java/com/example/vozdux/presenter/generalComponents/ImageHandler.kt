package com.example.vozdux.presenter.generalComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.R
import com.example.vozdux.domain.model.drone.UriImage


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesHandler(
    images: List<UriImage>,
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: ((uris: List<UriImage>) -> Unit)? = null
) {

    val pagerState = rememberPagerState(pageCount = {
        images.size
    })

    val alertDialogState = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            modifier = modifier
                .padding(vertical = 16.dp)
                .height(200.dp)
                .fillMaxWidth(),
            state = pagerState
        ) { page ->

            val currentUri = images[page].uri

            Box(contentAlignment = Alignment.TopEnd) {

                AsyncImage(
                    model = currentUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .clickable {
                            /*TODO*/
                        },
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )

                if (onDeleteClick != null) IconButton(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.60F)),
                    onClick = { alertDialogState.value = true }
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.delete_image),
                        tint = Color.White
                    )
                }
            }
        }

        if (images.size > 1) DotsIndicator(
            modifier = Modifier.padding(2.dp),
            totalDots = images.size,
            selectedIndex = pagerState.currentPage
        )
    }

    if (alertDialogState.value) AlertDialog(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Delete the image?",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { alertDialogState.value = false }) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                TextButton(onClick = {
                    // TODO ПРОВЕРИТЬ PAGE
                    onDeleteClick?.invoke(
                        images.mapNotNull { if (it.id != images[pagerState.currentPage].id) it else null }
                    )
                    alertDialogState.value = false
                }) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        onDismissRequest = {
            alertDialogState.value = false
        }
    )
}


