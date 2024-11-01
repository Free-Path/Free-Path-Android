package com.freepath.freepath.presentation.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.freepath.freepath.presentation.plan.PlanActivity
import com.freepath.freepath.presentation.travel.detail.TouristSpotDetailActivity

fun <T : Activity> navigateToActivity(
    context: Context,
    activity: Class<T>,
    setExtra: Intent.() -> Unit = {},
) {
    val intent = Intent(context, activity)
    intent.setExtra()
    context.startActivity(intent)
}

fun navigateToSpotDetailActivity(context: Context, spotId: Int) {
    val activity = TouristSpotDetailActivity::class.java
    navigateToActivity(context, activity) {
        putExtra("spotId", spotId)
    }
}

fun navigateToPlanActivity(context: Context, planId: Int) {
    val activity = PlanActivity::class.java
    navigateToActivity(context, activity) {
        putExtra(PlanActivity.PLAN_ID, planId)
    }
}