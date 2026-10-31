package com.example.core.monetization

/**
 * Monetization identifiers for Matnnevis.
 *
 * Zone IDs and the Tapsell application key are dashboard credentials.
 * Store product IDs are separate per store and must exactly match products
 * created in the corresponding developer console.
 */
object MonetizationConfig {
    object Tapsell {
        // Required for SDK initialization; obtain this app key from Tapsell dashboard.
        const val APP_KEY = ""

        const val REWARDED_ZONE_ID = "6ae5e61b187ee67e6b59be13"
        const val INTERSTITIAL_ZONE_ID = "6ae5e679187ee67e6b59be14"
        const val STANDARD_BANNER_ZONE_ID = "6ae5e6f117758066a4da87bd"

        val isConfigured: Boolean
            get() = APP_KEY.isNotBlank() &&
                REWARDED_ZONE_ID.isNotBlank() &&
                INTERSTITIAL_ZONE_ID.isNotBlank() &&
                STANDARD_BANNER_ZONE_ID.isNotBlank()
    }

    object BazaarVip {
        // Replace with exact subscription SKUs created in Cafe Bazaar console.
        const val MONTHLY_PRODUCT_ID = "matnnevis_bazaar_vip_monthly"
        const val YEARLY_PRODUCT_ID = "matnnevis_bazaar_vip_yearly"

        // Public verification key belongs to the store. Never commit private keys.
        const val RSA_PUBLIC_KEY = ""
    }

    object MyketVip {
        // Replace with exact subscription SKUs created in Myket console.
        const val MONTHLY_PRODUCT_ID = "matnnevis_myket_vip_monthly"
        const val YEARLY_PRODUCT_ID = "matnnevis_myket_vip_yearly"

        const val RSA_PUBLIC_KEY = ""
    }
}
