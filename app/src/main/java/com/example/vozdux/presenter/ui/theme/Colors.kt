package com.example.vozdux.presenter.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val headerTextColor: Color,
    val primaryTextColor: Color,
    val primaryTintColor: Color,
    val secondaryTintColor: Color,
    val hintTextColor: Color,
    val alertColor: Color

    )

val lightPalette = Colors(
    primaryBackground =  Color(0xFFE5E5E5),
    secondaryBackground = Color.White,
    headerTextColor = Color(0xFF171D33),
    primaryTextColor = Color(0xFF757F8C),
    primaryTintColor = Color(0xFF613EEA),
    secondaryTintColor = Color(0xFFFF7D00),
    hintTextColor = Color(0xFFA6AAB4),
    alertColor = Color.Red
)

val blackPalette = Colors(
    primaryBackground =  Color(0xFF343a40),
    secondaryBackground = Color.Black,
    headerTextColor = Color(0xFFf8f9fa),
    primaryTextColor = Color(0xFFdee2e6),
    primaryTintColor = Color(0xFF613EEA),
    secondaryTintColor = Color(0xFFFF7D00),
    hintTextColor = Color(0xFFA6AAB4),
    alertColor = Color.Red
)