package com.eduardo.extendedsearchbar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import com.eduardo.extendedsearchbar.R
import com.eduardo.extendedsearchbar.extensions.convertDpToPx
import com.eduardo.extendedsearchbar.extensions.convertDpToRawPx

private const val DEFAULT_ICON_SIZE = 16
private const val DEFAULT_TEXT_SIZE = 16
private const val DEFAULT_PADDING = 4
private const val DEFAULT_DEBOUNCE = 300
private const val DEFAULT_SEARCH_MAX_LENGTH = 100

/**
 * TODO: document your custom view class.
 */
class ExtendedSearchBarView : FrameLayout {

    private var _iconSize: Float? = null
    private var _textSize: Float? = null
    private var _textPadding: Float? = null
    private var _debounceTime: Long? = null
    private var _searchMaxLength: Int? = null
    private var _searchHint: String? = null
    private var _searchView: SearchView? = null

    var searchHint: String?
        get() = _searchHint
        set(value) {
            _searchHint = value
            _searchView?.queryHint = value
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.ExtendedSearchBarView, defStyle, 0
        )

        _searchHint = typedArray.getString(
            R.styleable.ExtendedSearchBarView_searchHint
        ) ?: String()

        _searchMaxLength = typedArray.getInteger(
            R.styleable.ExtendedSearchBarView_searchMaxLength,
            DEFAULT_SEARCH_MAX_LENGTH
        )

        _debounceTime = typedArray.getInteger(
            R.styleable.ExtendedSearchBarView_searchDebounceTime,
            DEFAULT_DEBOUNCE
        ).toLong()

        _textPadding = typedArray.getDimension(
            R.styleable.ExtendedSearchBarView_searchTextPadding,
            convertDpToRawPx(DEFAULT_PADDING)
        )

        _textSize = typedArray.getDimension(
            R.styleable.ExtendedSearchBarView_searchTextSize,
            convertDpToRawPx(DEFAULT_TEXT_SIZE)
        )

        _iconSize = typedArray.getDimension(
            R.styleable.ExtendedSearchBarView_searchIconSize,
            convertDpToRawPx(DEFAULT_ICON_SIZE)
        )

        typedArray.recycle()

    }
}