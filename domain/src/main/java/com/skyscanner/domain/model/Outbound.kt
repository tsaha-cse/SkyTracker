package com.skyscanner.domain.model

/**
 * Representing an outbound journey
 */
data class Outbound(
    val outboundId: String,
    val departureTime: String,
    val arrivalTime: String,
    val from: String,
    val to: String,
    val carrierName: String,
    val carrierLogoUrl: String?,
    val duration: String
)