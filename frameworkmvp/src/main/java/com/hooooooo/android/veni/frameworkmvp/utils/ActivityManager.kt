package com.hooooooo.android.veni.frameworkmvp.utils

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by yann on 2022/10/26
 * <p>
 * Describe:
 */
object ActivityManager {
    private const val TAG: String = "ActivityManager"
    private val activityStack: ArrayList<AppCompatActivity> = ArrayList()

    fun addActivity(activity: AppCompatActivity) {
        if (!activityStack.contains(activity)) {
            activityStack.add(activity)
        } else {
            Log.d(TAG, "$activity existent!")
        }
    }

    fun removeActivity(activity: AppCompatActivity) {
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

    fun getTaskTop(): AppCompatActivity? = if (activityStack.size > 0) activityStack[activityStack.size - 1] else null
}