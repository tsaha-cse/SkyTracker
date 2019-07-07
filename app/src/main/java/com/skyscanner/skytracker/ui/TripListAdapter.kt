package com.skyscanner.skytracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.skyscanner.domain.model.Trip
import com.skyscanner.skytracker.R
import com.skyscanner.skytracker.base.BaseViewHolder
import kotlinx.android.synthetic.main.layout_row_trip.view.*
import kotlinx.android.synthetic.main.layout_single_trip.view.*

class FlightListAdapter(private val onTripOnClick: (position: Int, selectedTrip: Trip) -> Unit) :
    ListAdapter<Trip, BaseViewHolder<Trip>>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Trip> =
        FlightViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_row_trip,
                parent,
                false
            ), onTripOnClick
        )

    override fun onBindViewHolder(holder: BaseViewHolder<Trip>, position: Int) {
        holder.onBind(getItem(position))
    }
}

class DiffUtil : DiffUtil.ItemCallback<Trip>() {
    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean =
        oldItem.outbound?.outboundId == newItem.outbound?.outboundId &&
                oldItem.inbound?.inboundId == newItem.inbound?.inboundId

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean = oldItem == newItem
}

class FlightViewHolder(
    itemView: View,
    private val onTripOnClick: (position: Int, selectedTrip: Trip) -> Unit
) : BaseViewHolder<Trip>(itemView) {

    private val layoutOutbound: View? = itemView.layoutOutBound
    private val layoutInbound: View? = itemView.layoutInbound

    override fun onBind(item: Trip) {
        item.outbound?.let { outbound ->
            layoutOutbound?.apply {
                tvDuration.text = outbound.duration
                tvSchedule.text = itemView.context.getString(
                    R.string.format_schedule,
                    outbound.departureTime,
                    outbound.arrivalTime
                )
                tvPlaceAgentDetails.text = itemView.context.getString(
                    R.string.format_from_to,
                    outbound.from,
                    outbound.to,
                    outbound.carrierName
                )

                Glide
                    .with(itemView.context)
                    .load(outbound.carrierLogoUrl)
                    .fitCenter()
                    .into(ivCarrierLogo)
            }
        }

        item.inbound?.let { inbound ->
            layoutInbound?.apply {
                tvDuration.text = inbound.duration
                tvSchedule.text = itemView.context.getString(
                    R.string.format_schedule,
                    inbound.departureTime,
                    inbound.arrivalTime
                )
                tvPlaceAgentDetails.text = itemView.context.getString(
                    R.string.format_from_to,
                    inbound.from,
                    inbound.to,
                    inbound.carrierName
                )

                Glide
                    .with(itemView.context)
                    .load(inbound.carrierLogoUrl)
                    .fitCenter()
                    .into(ivCarrierLogo)
            }
        }

        itemView.tvPrice.text =
            itemView.context.getString(
                R.string.format_price,
                item.currencySymbol,
                item.totalPrice
            )
        itemView.tvAgent.text = itemView.context.getString(R.string.format_agent, item.agentName)
        itemView.setOnClickListener {
            onTripOnClick.invoke(adapterPosition, item)
        }
    }
}