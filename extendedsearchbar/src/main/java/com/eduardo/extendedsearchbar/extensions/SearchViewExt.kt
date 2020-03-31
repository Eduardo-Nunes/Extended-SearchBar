package com.eduardo.extendedsearchbar.extensions

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource

fun SearchView.setSearchIcon(@DrawableRes searchIcon: Int) {
    getSearchIcon()?.imageResource = searchIcon
}

fun SearchView.setCloseIcon(@DrawableRes closeIcon: Int) {
    getCloseIcon()?.imageResource = closeIcon
}

fun SearchView.setHintTextColor(@ColorRes color: Int) {
    getSearchText()?.setHintTextColor(
        ResourcesCompat.getColor(resources, color, null)
    )
}

fun SearchView.setSearchTextColor(@ColorRes color: Int) {
    getSearchText()?.setTextColor(
        ResourcesCompat.getColor(resources, color, null)
    )
}

fun SearchView.setQueryBackground(@DrawableRes backgroundRes: Int) {
    findViewById<View>(androidx.appcompat.R.id.search_plate)?.backgroundResource = backgroundRes
}

fun SearchView.getSearchContainer(): LinearLayout? {
    return findViewById(androidx.appcompat.R.id.search_edit_frame)
}

fun SearchView.getSearchIcon(): AppCompatImageView? {
    return findViewById(androidx.appcompat.R.id.search_mag_icon)
}

fun SearchView.getSearchText(): SearchView.SearchAutoComplete? {
    return findViewById(androidx.appcompat.R.id.search_src_text)
}

fun SearchView.getCloseIcon(): AppCompatImageView? {
    return findViewById(androidx.appcompat.R.id.search_close_btn)
}

typealias FuzzySearchCallback = (query: String, isTyping: Boolean) -> Unit

fun SearchView.onQueryTextObserver(
    debounceTime: Long,
    onFuzzySearchCallback: FuzzySearchCallback
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        var job: Job? = null
        override fun onQueryTextSubmit(query: String?): Boolean {
            cancelJob()
            onFuzzySearchCallback.invoke(query.toString().normalize(), false)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            cancelJob()
            job = CoroutineScope(Dispatchers.Default).launch {
                delay(debounceTime)
                onFuzzySearchCallback.invoke(newText.toString().normalize(), true)
            }
            return false
        }

        private fun cancelJob() {
            job?.cancel()
            job = null
        }
    })
}