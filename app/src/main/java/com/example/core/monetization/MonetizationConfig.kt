package com.example.core.monetization

/**
 * Monetization identifiers for Matnnevis.
 *
 * Tapsell zone IDs are supplied by the publisher dashboard.
 * Store product IDs and public verification keys must be configured
 * after creating products in each store's developer console.
 */
object MonetizationConfig {
    object Tapsell {
        const val REWARDED_ZONE_ID = "6ae5e61b187ee67e6b59be13"
        const val INTERSTITIAL_ZONE_ID = "6ae5e679187ee67e6b59be14"
        const val STANDARD_BANNER_ZONE_ID = "6ae5e6f117758066a4da87bd"
    }

    object Vip {
        // Replace these IDs with the exact subscription SKUs created in each store.
        const val MONTHLY_PRODUCT_ID = "matnnevis_vip_monthly"
        const val YEARLY_PRODUCT_ID = "matnnevis_vip_yearly"

        // Never put private signing keys or passwords in source control.
        const val BAZAAR_RSA_PUBLIC_KEY_CONFIGURED = false
        const val MYKET_RSA_PUBLIC_KEY_CONFIGURED = false
    }
}
