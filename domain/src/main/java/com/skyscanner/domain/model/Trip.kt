package com.skyscanner.domain.model

/**
 * class containing all the information
 * to represent a trip with return. Represents
 * all information to display according to
 * the business logic (UI requirement)
 */
data class Trip(
    val outbound: Outbound?,
    val inbound: Inbound?,
    val currencySymbol: String?,
    val totalPrice: Double,
    val agentName: String?,
    val rating: Double
)