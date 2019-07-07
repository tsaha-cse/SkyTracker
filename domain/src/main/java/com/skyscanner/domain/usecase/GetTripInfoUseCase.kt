package com.skyscanner.domain.usecase

import com.skyscanner.data.model.Agent
import com.skyscanner.data.model.Carrier
import com.skyscanner.data.model.Place
import com.skyscanner.data.repository.FlightInfoRepository
import com.skyscanner.data.util.Mockable
import com.skyscanner.domain.base.BaseUseCase
import com.skyscanner.domain.model.Inbound
import com.skyscanner.domain.model.Outbound
import com.skyscanner.domain.model.Trip
import com.skyscanner.domain.toInbound
import com.skyscanner.domain.toOutbound

@Mockable
class GetTripInfoUseCase(
    private val flightInfoRepository: FlightInfoRepository
) : BaseUseCase<String, List<Trip>>() {

    override suspend fun build(param: String): List<Trip> =
        with(flightInfoRepository.getFlightInfo(param)) {
            val tripList: MutableList<Trip> = mutableListOf()

            val carriersMap: Map<Int, Carrier> =
                mutableMapOf<Int, Carrier>().apply {
                    carriers.forEach { carrier ->
                        put(carrier.id, carrier)
                    }
                }


            val agentsMap: Map<Int, Agent> =
                mutableMapOf<Int, Agent>().apply {
                    agents.forEach { agent ->
                        put(agent.id, agent)
                    }
                }

            val placesMap: Map<Int, Place> =
                mutableMapOf<Int, Place>().apply {
                    places.forEach { place ->
                        put(place.id, place)
                    }
                }

            val outboundTripMap: Map<String, Outbound> =
                mutableMapOf<String, Outbound>().apply {
                    legs.filter { it.directionality == OUTBOUND }
                        .forEach { leg ->
                            put(leg.id, leg.toOutbound(placesMap, carriersMap))
                        }
                }

            val inboundTripMap: Map<String, Inbound> =
                mutableMapOf<String, Inbound>().apply {
                    legs.filter { it.directionality == INBOUND }
                        .forEach { leg ->
                            put(leg.id, leg.toInbound(placesMap, carriersMap))
                        }
                }

            val currencySymbol =
                currencies.filter { currency -> currency.code == query?.currency }[0].symbol
            itineraries.forEach {
                tripList.add(
                    Trip(
                        outboundTripMap[it.outboundLegId],
                        inboundTripMap[it.inboundLegId],
                        currencySymbol,
                        it.pricingOptions[0].price,
                        agentsMap[it.pricingOptions[0].agents[0]]?.name,
                        10.0
                    )
                )
            }

            return tripList
        }

    companion object {
        private const val OUTBOUND: String = "Outbound"
        private const val INBOUND: String = "Inbound"
    }
}