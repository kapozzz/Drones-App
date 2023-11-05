package com.example.vozdux.presenter.new_drone.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertiesTabRow(
    pagerState: PagerState,
    categories: List<String>,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Text(modifier = Modifier.fillMaxWidth(),text = category)
            }
        }
    }

}