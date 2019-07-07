package com.skyscanner.domain.model

/**
 * Representing an inbound journey
 */
data class Inbound(
    val inboundId: String,
    val departureTime: String,
    val arrivalTime: String,
    val from: String,
    val to: String,
    val carrierName: String,
    val carrierLogoUrl: String?,
    val duration: String
)