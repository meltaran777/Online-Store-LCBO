package com.jaaarl.catalog.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.jaaarl.catalog.R
import com.jaaarl.catalog.ui.customviews.NonSwipeableViewPager
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

/**
 * Created by Bohdan on 22.10.2017.
 */

class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {
    private val TAG = javaClass.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: NonSwipeableViewPager

    @BindView(R.id.search_edit_text)
    lateinit internal var searchEditText: EditText

    private lateinit var drawer: Drawer
    private lateinit var navigationItems: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        searchEditText.visibility = View.GONE

        navigationItems = resources.getStringArray(R.array.navigation_drawer_item)

        initToolbar()
        initViewPager()
        createNavigationDrawer()
    }

    override fun onItemClick(view: View, position: Int, drawerItem: IDrawerItem<*>?): Boolean {
        if (drawerItem != null) {
            val fragment_index = drawerItem.identifier

            if (fragment_index >= 0 && position != -1) {
                val fragment = supportFragmentManager.findFragmentByTag(
                        "android:switcher:" + R.id.main_content_view_pager + ":" + fragment_index)

                when (fragment_index) {
                    0 -> {
                        searchEditText.visibility = View.GONE
                        supportActionBar!!.title = navigationItems[fragment_index]
                    }
                    1 -> {
                        searchEditText.visibility = View.GONE
                        supportActionBar!!.title = navigationItems[fragment_index]
                    }
                    2 -> {
                        searchEditText.visibility = View.GONE
                        supportActionBar!!.title = navigationItems[fragment_index]
                    }
                    3 -> {
                        searchEditText.visibility = View.VISIBLE
                        searchEditText.addTextChangedListener(fragment as TextWatcher)
                        supportActionBar!!.title = null
                    }
                    4 -> {
                        searchEditText.visibility = View.GONE
                        supportActionBar!!.title = navigationItems[fragment_index]
                    }
                }

                viewPager.currentItem = fragment_index

                if (fragment is IFragmentUiController) {
                    fragment.onFragmentOnScreen()
                }
            } else if (position == -1) {
                Toast.makeText(this, "About ", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    private fun createNavigationDrawer() {
        val accountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .build()

        val items = arrayOfNulls<PrimaryDrawerItem>(navigationItems.size)

        navigationItems
                .mapIndexed { i, name -> PrimaryDrawerItem().withIdentifier(i).withName(name) }
                .forEachIndexed { i, item1 -> items[i] = item1 }

        drawer = DrawerBuilder(this)
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withDisplayBelowStatusBar(true)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(*items)
                .withOnDrawerItemClickListener(this)
                .addStickyDrawerItems(
                        SecondaryDrawerItem()
                                .withName(R.string.about)
                                .withSelectable(false))
                .build()
    }

    private fun initViewPager() {
        val adapter = TabsAdapter(supportFragmentManager)
        viewPager = findViewById<View>(R.id.main_content_view_pager) as NonSwipeableViewPager
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = adapter
    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar_view) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = navigationItems[0]
    }
}
