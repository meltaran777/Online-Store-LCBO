package com.jaaarl.catalog.model

import java.lang.IllegalArgumentException

/**
 * Created by Bohdan on 26.11.2017.
 */

class SearchData {

    enum class OrderType {
        PRICE_IN_CENTS, INVENTORY_PRICE_IN_CENTS,
        VOLUME_IN_MILLILITERS, INVENTORY_VOLUME_IN_MILLILITERS,
        BONUS_REWARD_MILES, BONUS_REWARD_MILES_ENDS_ON,
        ALCOHOL_CONTENT;

        companion object {
            fun getItem(ordinal: Int) : OrderType {
                for(type: OrderType in OrderType.values()) {
                    if (type.ordinal == ordinal)
                        return type
                }; throw IllegalArgumentException("Order type with this ordinal doest`t exist.")
            }
        }
    }

    enum class SortOptions {
        IS_DEAD, IS_DISCONTINUED, HAS_VALUE_ADDED_PROMOTION,
        HAS_LIMITED_TIME_OFFER, HAS_BONUS_REWARD_MILES,
        IS_SEASONAL, IS_VQA, IS_OCB, IS_KOSHER;

        companion object {
            fun getItem(ordinal: Int) : SortOptions {
                for(option: SortOptions in SortOptions.values()) {
                    if (option.ordinal == ordinal)
                        return option
                }; throw IllegalArgumentException("Sort Option with this ordinal doest`t exist.")
            }
        }
    }

    enum class SortOptionType {
        WHERE,
        WHERE_NOT
    }
}
