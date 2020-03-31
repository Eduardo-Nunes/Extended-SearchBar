package com.eduardo.extendedsearchbar.view

import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.eduardo.extendedsearchbar.R
import com.eduardo.extendedsearchbar.extensions.*
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.rightPadding

private const val DEFAULT_BAR_SIZE = 48
private const val DEFAULT_ICON_SIZE = 16
private const val DEFAULT_TEXT_SIZE = 16f
private const val DEFAULT_PADDING = 4
private const val DEFAULT_DEBOUNCE = 300
private const val DEFAULT_SEARCH_MAX_LENGTH = 100

/**
 * TODO: document your custom view class.
 */

class ExtendedSearchBarView : FrameLayout {

    private var _textSize: Float = DEFAULT_TEXT_SIZE
    private var _debounceTime: Long = DEFAULT_DEBOUNCE.toLong()
    private var _iconSize: Int = convertDpToPx(DEFAULT_ICON_SIZE)
    private var _searchMaxLength: Int = DEFAULT_SEARCH_MAX_LENGTH
    private var _textPadding: Int = convertDpToPx(DEFAULT_PADDING)
    private var _searchBarHeight: Int = convertDpToPx(DEFAULT_BAR_SIZE)
    private val _searchView: SearchView by lazy {
        setupInnerSearchView()
    }

    var searchValue: String
        get() = _searchView.query.toString()
        set(value) {
            _searchView.setQuery(value, false)
        }

    var hintValue: String
        get() = _searchView.queryHint.toString()
        set(value) {
            _searchView.queryHint = value
        }

    fun changeSearchBackground(@DrawableRes background: Int) {
        _searchView.setBackgroundResource(background)
    }

    fun changeHintColor(@ColorRes hintColor: Int) {
        _searchView.setHintTextColor(hintColor)
    }

    fun changeSearchColor(@ColorRes hintColor: Int) {
        _searchView.setSearchTextColor(hintColor)
    }

    fun setupInputCallback(
        onFuzzyCallback: FuzzySearchCallback
    ) {
        _searchView.onQueryTextObserver(_debounceTime, onFuzzyCallback)
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

        hintValue = typedArray.getString(
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

        _textPadding = typedArray.getDimensionPixelSize(
            R.styleable.ExtendedSearchBarView_searchTextPadding,
            convertDpToPx(DEFAULT_PADDING)
        )

        _textSize = typedArray.getDimension(
            R.styleable.ExtendedSearchBarView_searchTextSize,
            _textSize
        )

        _iconSize = typedArray.getDimensionPixelSize(
            R.styleable.ExtendedSearchBarView_searchIconSize,
            _iconSize
        )

        _searchBarHeight = typedArray.getDimensionPixelSize(
            R.styleable.ExtendedSearchBarView_searchBarHeight,
            _searchBarHeight
        )

        typedArray.recycle()
    }

    private fun setupInnerSearchView(): SearchView {

        fun SearchView.setupSearchBackground(): SearchView {
            setBackgroundResource(R.drawable.round_t_white_16)
            setQueryBackground(android.R.color.transparent)
            return this
        }

        fun SearchView.setupSearchFocusMode(): SearchView {
            isFocusable = false
            isFocusableInTouchMode = true
            return this
        }

        fun SearchView.setupSearchDimensions(): SearchView {
            layoutParams = LayoutParams(MATCH_PARENT, convertDpToPx(DEFAULT_BAR_SIZE))
            setMargins(start = 0, end = 0)
            getSearchContainer()?.setMargins(0, 0, 0, 0)
            return this
        }

        fun SearchView.setupSearchText(): SearchView {
            setHintTextColor(R.color.grey_light)
            setSearchTextColor(android.R.color.white)
            getSearchText()?.also {
                it.textSize = _textSize
                it.setLineSpacing(0f, 1f)
                it.rightPadding = 0
                it.leftPadding = _textPadding
                it.addFilter(InputFilter.LengthFilter(_searchMaxLength))
            }
            return this
        }

        fun SearchView.setupSearchIcons(): SearchView {
            setSearchIcon(R.drawable.ic_search)
            getSearchIcon()?.setMargins(0, 0, 0, 0)
            getSearchIcon()?.setDimensions(x = _iconSize, y = _iconSize)
            setCloseIcon(R.drawable.ic_close)
            setIconifiedByDefault(false)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                getSearchText()?.textCursorDrawable =
                    ContextCompat.getDrawable(context, R.drawable.ic_cursor)
            }

            return this
        }

        return SearchView(context, null, R.style.autoCompleteCustomCursor)
            .setupSearchBackground()
            .setupSearchFocusMode()
            .setupSearchDimensions()
            .setupSearchText()
            .setupSearchIcons()
    }

    init {
        addView(_searchView)
    }
}