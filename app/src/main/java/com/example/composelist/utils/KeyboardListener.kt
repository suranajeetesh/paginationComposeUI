package com.example.composelist.utils

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.composelist.R
import com.example.composelist.ui.theme.Transparent20
import kotlinx.coroutines.delay

/**
 * Created by Jeetesh Surana.
 */

@Composable
fun KeyboardObserverComposable(onKeyboardVisible: (isKeyboardVisible: Boolean?) -> Unit) {
    val view = LocalView.current
    DisposableEffect(key1 = Unit) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val insets = ViewCompat.getRootWindowInsets(view)
            val isKeyboardVisible = insets?.isVisible(WindowInsetsCompat.Type.ime())
            onKeyboardVisible(isKeyboardVisible)
            Log.e("TAG", "KeyboardObserverComposable() isKeyboardVisible --> $isKeyboardVisible")
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}

@Composable
fun Loader() {
    val degree = produceState(initialValue = 0) {
        while (true) {
            delay(16)
            value = (value + 20) % 360
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxSize(1f)
        .background(Transparent20), content = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .rotate(degree.value.toFloat())
            )
            Text(text = stringResource(R.string.loading))
        }
    })
}

@Composable
fun DerivedState() {
    val tableOf = remember { mutableStateOf(5) }
    val index = produceState(initialValue = 1) {
        repeat(9) {
            delay(1000)
            value += 1
        }
    }

    val message = remember {
        derivedStateOf {
            "${tableOf.value} * ${index.value} = ${tableOf.value * index.value}"
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize(1f)) {
        Text(text = message.value, style = MaterialTheme.typography.headlineLarge)
    }
}