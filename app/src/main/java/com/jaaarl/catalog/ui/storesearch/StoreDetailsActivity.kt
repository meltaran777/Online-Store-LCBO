package com.jaaarl.catalog.ui.storesearch

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jaaarl.catalog.R
import com.jaaarl.catalog.model.Store
import com.jaaarl.catalog.ui.customviews.ScrollableMapFragment
import com.jaaarl.catalog.utils.showSnack
import kotlinx.android.synthetic.main.activity_store_details.*
import kotlinx.android.synthetic.main.content_store_details.*
import android.content.pm.PackageManager
import android.os.Build
import com.jaaarl.catalog.db.Db
import com.jaaarl.catalog.model.FavouriteStoreEntity
import com.jaaarl.catalog.model.Product
import com.jaaarl.catalog.rest.CatalogDataLoader
import com.jaaarl.catalog.rest.DataLoadListener
import com.jaaarl.catalog.rest.Request
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class StoreDetailsActivity : AppCompatActivity(), OnMapReadyCallback, DataLoadListener {
    private val TAG = javaClass.simpleName

    private val INITIAL_PERMS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    private val INITIAL_REQUEST = 1337
    private val LOCATION_REQUEST = INITIAL_REQUEST + 3

    private lateinit var store: Store

    @Inject
    lateinit internal var catalogDataLoader: CatalogDataLoader
    private val request = Request()

    private val storeProducts: MutableList<Product> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_details)

        if (intent.hasExtra("store")) {
            store = intent.getSerializableExtra("store") as Store

            Flowable.just(store)
                    .subscribeOn(Schedulers.io())
                    .map {
                        val s = Db.getFavouriteStoreDao().getFavouriteStore(it.id)
                        if (s == null)
                            return@map false
                        else return@map s.isFavourite
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        store.favorite = it
                        initFavourite(it)
                    })

            setDataToViews(store)
        } else {
            throw ExceptionInInitializerError("No data for display")
        }

        if (!canAccessLocation()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(INITIAL_PERMS, INITIAL_REQUEST)
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleTextView.text = store.name

        val mapFragment: ScrollableMapFragment = supportFragmentManager.findFragmentById(R.id.map) as ScrollableMapFragment
        mapFragment.setListener { content_scroll_view.requestDisallowInterceptTouchEvent(true) }
        mapFragment.getMapAsync(this)

        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", store.telephone, null))
            startActivity(intent)
        }

        arrowBackImage?.setOnClickListener { finish() }

        request.storeId = store.id
        catalogDataLoader.requestProducts(request, false, this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        val latlng = store.latitude?.let { store.longitude?.let { it1 -> LatLng(it, it1) } }

        val cameraPosition = CameraPosition.Builder()
                .target(latlng)      // Sets the center of the map to Mountain View
                .zoom(13f)                   // Sets the zoom
                .bearing(90f)                // Sets the orientation of the camera to east
                .tilt(30f)                   // Sets the tilt of the camera to 30 degrees
                .build()

        if (canAccessLocation())
            googleMap.isMyLocationEnabled = true

        with(googleMap) {
            addMarker(latlng?.let {
                MarkerOptions()
                        .position(it)
                        .title(store.name)
            })
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_for_map_purpul)))

            animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            setOnMyLocationButtonClickListener({
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                true
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favourite_action -> {
                store.favorite = !store.favorite

                val fStore = FavouriteStoreEntity(store.id, store.favorite)

                Observable.just(fStore)
                        .subscribeOn(Schedulers.io())
                        .doOnNext { Db.getFavouriteStoreDao().insert(fStore) }
                        .map { it.isFavourite }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            initFavourite(it)
                            if (it)
                                fab.showSnack("${store.name} Added to favourite")
                            else fab.showSnack("${store.name} Removed from favourite")
                        })
            }
            R.id.open_product_list_action -> {
                ProductListDialogFragment().apply { storeId = store.id }
                        .show(supportFragmentManager, "product_list")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setDataToViews(store: Store) {
        var stringBuilder = StringBuilder()

        stringBuilder.append(address_text.text).append(" ").append(store.addressLine1)
        address_text.text = stringBuilder.toString()

        stringBuilder = StringBuilder()
        stringBuilder.append(city_text.text).append(" ").append(store.city)
        city_text.text = stringBuilder.toString()

        stringBuilder = StringBuilder()
        stringBuilder.append(phone_text.text).append(" ").append(store.telephone)
        phone_text.text = stringBuilder.toString()

        name_text.text = store.name

        initFavourite(store.favorite)
    }

    private fun initFavourite(isFavourite: Boolean) {
        if (isFavourite)
            favourite_image.visibility = View.VISIBLE
        else favourite_image.visibility = View.GONE
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // DataLoadListener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onDataLoadingStart(showProgress: Boolean) {

    }

    override fun <T> onDataLoadingComplete(data: List<T>?) {
        val list = data?.filterIsInstance<Product>()

        runOnUiThread({
            list?.forEach { storeProducts.add(it) }
        })
    }

    override fun onError(message: String?) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.store_details_menu, menu)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST -> if (canAccessLocation()) {
                doLocationThing()
            } else {
                fab.showSnack("Need Location Permission for displaying map.")
            }
        }
    }

    private fun doLocationThing() {

    }

    private fun canAccessLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun hasPermission(perm: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm)
        } else {
            true
        }
    }
}




