package com.jaaarl.catalog.utils

import android.content.Context
import android.content.SharedPreferences
import com.jaaarl.catalog.model.SearchData

/**
 * Created by Bohdan on 23.12.2017.
 */
object MyPreferences {
    private val APP_PREFERENCES_SETTING = "Setting"

    private val OPTIONS_WHERE_KEY = "where_options_"
    private val OPTIONS_WHERE_NOT_KEY = "where_not_options_"
    private val SORT_ENABLE_KEY = "sort_enable"
    private val SORT_ORDER_KEY = "sort_order"
    private val SORT_ORDER_TEMP_KEY = "sort_order_temp"

    private lateinit var preferences: SharedPreferences


    fun init(c: Context) {
        preferences = c.getSharedPreferences(APP_PREFERENCES_SETTING, Context.MODE_PRIVATE)
    }

    fun getOptionsArray(type: SearchData.SortOptionType): Array<SearchData.SortOptions?>? {
        val key: String = if (type == SearchData.SortOptionType.WHERE) {
            OPTIONS_WHERE_KEY
        } else OPTIONS_WHERE_NOT_KEY

        val size: Int = preferences.getInt(key, 0)
        val options: MutableList<SearchData.SortOptions> = ArrayList()

        (0 until size).forEach {
            val ordinal = preferences.getInt(key + it, -1)
            if (ordinal != -1)
                options.add(SearchData.SortOptions.getItem(ordinal))
        }

        return options.toTypedArray()
    }

    fun setOptionsArray(type: SearchData.SortOptionType, options: Array<SearchData.SortOptions?>?): Boolean {
        val key: String = if (type == SearchData.SortOptionType.WHERE) {
            OPTIONS_WHERE_KEY
        } else OPTIONS_WHERE_NOT_KEY

        val edit = preferences.edit()

        if (options != null) {
            edit.putInt(key, options.size)

            for (i in 0 until options.size) {
                edit.remove(key + i)

                if (options[i] == null)
                    edit.putInt(key + i, -1)

                options[i]?.let { edit.putInt(key + i, it.ordinal) }
            }
        }

        return edit.commit()
    }

    fun isSortEnable(): Boolean {
        return preferences.getBoolean(SORT_ENABLE_KEY, false)
    }

    fun setSortEnable(isEnable: Boolean): Boolean {
        val key: String = SORT_ENABLE_KEY
        val edit = preferences.edit()
        edit.putBoolean(key, isEnable)
        return edit.commit()
    }

    fun getSortOrder(): SearchData.OrderType? {
        val ordinal = preferences.getInt(SORT_ORDER_KEY, -1)
        if (ordinal != -1)
            return SearchData.OrderType.getItem(ordinal)
        else return null
    }


    fun setSortOrder(orderId: Int?): Boolean {
        val key: String = SORT_ORDER_KEY
        val edit = preferences.edit()

        if (orderId != null)
            edit.putInt(key, orderId)
        else edit.putInt(key, -1)

        return edit.commit()
    }

    fun setSortOrderTemp(orderId: Int?): Boolean {
        val key: String = SORT_ORDER_TEMP_KEY
        val edit = preferences.edit()

        if (orderId != null)
            edit.putInt(key, orderId)
        else edit.putInt(key, -1)

        return edit.commit()
    }

    fun getSortOrderTemp(): SearchData.OrderType? {
        val ordinal = preferences.getInt(SORT_ORDER_TEMP_KEY, -1)
        if (ordinal != -1)
            return SearchData.OrderType.getItem(ordinal)
        else return null
    }
}

