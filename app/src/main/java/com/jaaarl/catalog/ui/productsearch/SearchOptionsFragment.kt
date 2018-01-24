package com.jaaarl.catalog.ui.productsearch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SwitchCompat
import android.view.*
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.jaaarl.catalog.R
import com.jaaarl.catalog.model.SearchData
import com.jaaarl.catalog.model.SearchData.OrderType.*
import com.jaaarl.catalog.model.SearchData.SortOptionType.WHERE
import com.jaaarl.catalog.model.SearchData.SortOptionType.WHERE_NOT
import com.jaaarl.catalog.model.SearchData.SortOptions.*
import com.jaaarl.catalog.utils.MyPreferences
import java.util.*


class SearchOptionsFragment : DialogFragment(), View.OnTouchListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.order_group) lateinit var orderGroup: RadioGroup

    @BindView(R.id.isCosherCheckBox) lateinit var isKosherRadioButton: RadioButton
    @BindView(R.id.isNotCosherCheckBox) lateinit var isNotKosherRadioButton: RadioButton
    @BindView(R.id.isDead) lateinit var isDeadRadioButton: RadioButton
    @BindView(R.id.isNotDead) lateinit var isNotDeadRadioButton: RadioButton
    @BindView(R.id.is_seasonal) lateinit var isSeasonalRadioButton: RadioButton
    @BindView(R.id.is_not_seasonal) lateinit var isNotSeasonalRadioButton: RadioButton
    @BindView(R.id.has_bonus_reward_miles) lateinit var hasBonusMilesRadioButton: RadioButton
    @BindView(R.id.has_not_bonus_reward_miles) lateinit var hasNoBonusMilesRadioButton: RadioButton

/*    val optionsCheckboxList: List<RadioButton> = listOf(
            isKosherRadioButton, isNotKosherRadioButton, isDeadRadioButton,
            isNotDeadRadioButton, isSeasonalRadioButton, isNotSeasonalRadioButton,
            hasBonusMilesRadioButton, hasNoBonusMilesRadioButton)*/

    @BindView(R.id.sort_switch) lateinit var sort_switch: SwitchCompat

    @BindView(R.id.applyButton) lateinit var applyButton: Button

    private var orderType: SearchData.OrderType? = null
    private var orderTypeTemp: SearchData.OrderType? = PRICE_IN_CENTS
    private var whereOptions: MutableList<SearchData.SortOptions?>? = ArrayList()
    private var whereNotOptions: MutableList<SearchData.SortOptions?>? = ArrayList()

    private var mBottomFragmentListener: BottomFragmentListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.bottom_fragment_layout, container, false)
        ButterKnife.bind(this, view)

        val window = dialog.window
        window!!.setGravity(Gravity.BOTTOM or Gravity.CENTER)

        initOptionsData()

        initOptionsUI()

        initListeners()

        initUI()

        return view
    }

    private fun initOptionsData() {
        MyPreferences.getOptionsArray(WHERE)?.forEach {
            whereOptions?.add(it)
        }
        MyPreferences.getOptionsArray(WHERE_NOT)?.forEach {
            whereNotOptions?.add(it)
        }
        orderType = MyPreferences.getSortOrder()
        orderTypeTemp = MyPreferences.getSortOrderTemp()
    }

    private fun initOptionsUI() {
        whereOptions?.forEach {
            when (it) {
                IS_DEAD -> isDeadRadioButton.isChecked = true
                HAS_BONUS_REWARD_MILES -> hasBonusMilesRadioButton.isChecked = true
                IS_SEASONAL -> isSeasonalRadioButton.isChecked = true
                IS_KOSHER -> isKosherRadioButton.isChecked = true
            }
        }
        whereNotOptions?.forEach {
            when (it) {
                IS_DEAD -> isNotDeadRadioButton.isChecked = true
                HAS_BONUS_REWARD_MILES -> hasNoBonusMilesRadioButton.isChecked = true
                IS_SEASONAL -> isNotSeasonalRadioButton.isChecked = true
                IS_KOSHER -> isNotKosherRadioButton.isChecked = true
            }
        }
    }

    private fun initListeners() {
        setTouchListeners()
        setCheckedChangeListeners()

        sort_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                for (i in 0 until orderGroup.childCount) {
                    (orderGroup.getChildAt(i) as RadioButton).isEnabled = isChecked
                }

                if (isChecked) {
                    orderType = orderTypeTemp
                } else {
                    orderType = null
                }
            }
        }
    }

    private fun initUI() {
        val sortOrderRadioButton = MyPreferences.getSortOrder()?.ordinal?.let { orderGroup.getChildAt(it) }
        if (sortOrderRadioButton is RadioButton) {
            sortOrderRadioButton.isChecked = true
        }



        sort_switch.isChecked = MyPreferences.isSortEnable()
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(
                    ContextCompat.getColor(context, R.color.white_aplha)))
        }
    }

    @OnClick(R.id.applyButton)
    internal fun applyOptions() {
        sendWhereOptionsChangeEvents()
        sendWhereNotOptionsChangeEvents()
        mBottomFragmentListener?.onSortOptionsChange(orderType)
        mBottomFragmentListener?.onOptionsApply()
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.price_radio_button -> orderType = PRICE_IN_CENTS
            R.id.price_radio_button_inventory -> orderType = INVENTORY_PRICE_IN_CENTS
            R.id.volume_in_milliliters -> orderType = VOLUME_IN_MILLILITERS
            R.id.volume_in_milliliters_inventory -> orderType = INVENTORY_VOLUME_IN_MILLILITERS
            R.id.bonus_reward_miles -> orderType = BONUS_REWARD_MILES
            R.id.bonus_reward_miles_ends_on -> orderType = BONUS_REWARD_MILES_ENDS_ON
            R.id.alcohol_content -> orderType = ALCOHOL_CONTENT
        }
        orderTypeTemp = orderType
        MyPreferences.setSortOrderTemp(orderTypeTemp?.ordinal)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v !is RadioButton)
            return false

        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            if (v.isChecked) {
                Handler().postDelayed({
                    val container = v.getParent() as RadioGroup
                    if (v.getId() == container.checkedRadioButtonId)
                        container.clearCheck()
                }, 100)
            }
        }

        return false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val parent = parentFragment
        mBottomFragmentListener = if (parent != null) {
            parent as BottomFragmentListener
        } else {
            context as BottomFragmentListener
        }
    }

    override fun onDetach() {
        mBottomFragmentListener = null
        super.onDetach()
    }

    private fun sendWhereOptionsChangeEvents() {
        mBottomFragmentListener?.onFilterOptionsChange(WHERE, whereOptions?.toTypedArray())
    }

    private fun sendWhereNotOptionsChangeEvents() {
        mBottomFragmentListener?.onFilterOptionsChange(WHERE_NOT, whereNotOptions?.toTypedArray())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListeners() {
        isKosherRadioButton.setOnTouchListener(this)
        isNotKosherRadioButton.setOnTouchListener(this)
        isDeadRadioButton.setOnTouchListener(this)
        isNotDeadRadioButton.setOnTouchListener(this)
        isSeasonalRadioButton.setOnTouchListener(this)
        isNotSeasonalRadioButton.setOnTouchListener(this)
        hasBonusMilesRadioButton.setOnTouchListener(this)
        hasNoBonusMilesRadioButton.setOnTouchListener(this)
    }

    private fun setCheckedChangeListeners() {
        isKosherRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereOptions?.add(IS_KOSHER)
            else
                whereOptions?.remove(IS_KOSHER)
        }
        isNotKosherRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereNotOptions?.add(IS_KOSHER)
            else
                whereNotOptions?.remove(IS_KOSHER)
        }
        isDeadRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereOptions?.add(IS_DEAD)
            else
                whereOptions?.remove(IS_DEAD)
        }
        isNotDeadRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereNotOptions?.add(IS_DEAD)
            else
                whereNotOptions?.remove(IS_DEAD)
        }
        isSeasonalRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereOptions?.add(IS_SEASONAL)
            else
                whereOptions?.remove(IS_SEASONAL)
        }
        isNotSeasonalRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereNotOptions?.add(IS_SEASONAL)
            else
                whereNotOptions?.remove(IS_SEASONAL)
        }
        hasBonusMilesRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereOptions?.add(HAS_BONUS_REWARD_MILES)
            else
                whereOptions?.remove(HAS_BONUS_REWARD_MILES)
        }
        hasNoBonusMilesRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                whereNotOptions?.add(HAS_BONUS_REWARD_MILES)
            else
                whereNotOptions?.remove(HAS_BONUS_REWARD_MILES)
        }

        orderGroup.setOnCheckedChangeListener(this)
    }


    companion object {
        fun newInstance(): SearchOptionsFragment {
            return SearchOptionsFragment()
        }
    }
}


interface BottomFragmentListener {

    fun onFilterOptionsChange(type: SearchData.SortOptionType?, options: Array<SearchData.SortOptions?>?)

    fun onSortOptionsChange(order: SearchData.OrderType?)

    fun onOptionsApply()
}