package com.hooooooo.android.veni.frameworkmvp.utils

import android.app.Activity
import android.util.Log

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
object ActivityManager {
    private const val TAG: String = "ActivityManager"
    private val activityStack: ArrayList<Activity> = ArrayList()

    fun addActivity(activity: Activity) {
        if (!activityStack.contains(activity)) {
            activityStack.add(activity)
        } else {
            Log.d(TAG, "$activity existent!")
        }
    }

    fun removeActivity(activity: Activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
        } else {
            Log.d(TAG, "$activity no existent!")
        }
    }

    fun finishAllActivities() {
        if (activityStack.isNotEmpty()) {
            for (activity: Activity in activityStack) {
                activity.finishAfterTransition()
            }
            activityStack.clear()
        }
    }

    fun getTaskTop(): Activity = activityStack[activityStack.size - 1]
}