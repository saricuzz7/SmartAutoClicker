package com.smartclicker.pro

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class AutoClickService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Keyinchalik ekran hodisalarini tahlil qilish uchun ishlatiladi.
    }

    override fun onInterrupt() {}

    fun clickAt(x: Float, y: Float, callback: (Boolean) -> Unit = {}) {
        val path = Path().apply {
            moveTo(x, y)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(path, 0, 100)
            )
            .build()

        dispatchGesture(gesture, object :
            GestureResultCallback() {
            override fun onCompleted(
                gestureDescription: GestureDescription
            ) {
                callback(true)
            }

            override fun onCancelled(
                gestureDescription: GestureDescription
            ) {
                callback(false)
            }
        }, null)
    }
}
