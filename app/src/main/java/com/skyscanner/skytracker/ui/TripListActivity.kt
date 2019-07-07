package com.skyscanner.skytracker.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skyscanner.domain.model.Trip
import com.skyscanner.presentation.ext.formatDateMonth
import com.skyscanner.presentation.viewmodel.FlightListViewModel
import com.skyscanner.presentation.viewmodel.LoadingStatus
import com.skyscanner.skytracker.R
import com.skyscanner.skytracker.ext.px
import kotlinx.android.synthetic.main.activity_trip_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val flightListViewModel: FlightListViewModel by viewModel()

    private lateinit var flightListAdapter: FlightListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)

        setupUi()
        setupListener()
        flightListViewModel.loadData()
    }

    private fun setupUi() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        flightListViewModel.getInput().apply {
            supportActionBar?.title = "$placeOfOriginCode - $placeOfDestinationCode"
            supportActionBar?.subtitle =
                "${formatDateMonth(outboundDate)} - ${formatDateMonth(inboundDate)}, " +
                        "$adults ${resources.getQuantityString(
                            R.plurals.plural_adult,
                            adults
                        )}, $cabinClassType"
        }

        btnReload.setOnClickListener {
            flightListViewModel.loadData(true)
        }

        setupRecyclerView()
    }

    /**
     * observe the livedata changes from ViewModel
     */
    private fun setupListener() {
        flightListViewModel.getLoadingStatus()
            .observe(this, Observer(::changeLoadingStatus))

        flightListViewModel.getTripListDispatcher()
            .observe(this, Observer(::updateTripList))
    }

    private fun setupRecyclerView() {
        pullToRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        pullToRefresh.setOnRefreshListener(this)
        flightListAdapter = FlightListAdapter { _, selectedTrip: Trip ->
            Toast.makeText(
                this,
                selectedTrip.totalPrice.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        rvTripList.adapter = flightListAdapter
        rvTripList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                when (parent.getChildAdapterPosition(view)) {
                    RecyclerView.NO_POSITION -> return
                    0 -> outRect.set(0, 10.px, 0, 10.px)
                    else -> outRect.set(0, 0, 0, 10.px)
                }
            }
        })
    }

    override fun onRefresh() {
        pullToRefresh.isRefreshing = false
        flightListViewModel.loadData()
    }

    /**
     * to react on LoadingStatus update from ViewModel
     * update the ui accordingly
     */
    private fun changeLoadingStatus(loadingStatus: LoadingStatus) {
        tvCountTotalResult.text = loadingStatus.message
        progressBar.visibility = if (loadingStatus.isLoading) View.VISIBLE else View.GONE
        pullToRefresh.isEnabled = !loadingStatus.isLoading
        btnReload.visibility =
            if (!loadingStatus.isLoading && loadingStatus.message.isNullOrBlank()) {
                rvTripList.visibility = View.GONE
                View.VISIBLE
            } else View.GONE
    }

    /**
     * called when there is update available for the trip list
     */
    private fun updateTripList(tripList: List<Trip>) {
        if (rvTripList.visibility != View.VISIBLE) {
            rvTripList.visibility = View.VISIBLE
        }
        flightListAdapter.submitList(tripList)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_flight_list, menu)
        return true
    }
}