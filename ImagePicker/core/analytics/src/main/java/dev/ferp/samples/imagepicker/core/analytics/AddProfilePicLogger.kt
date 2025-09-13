package dev.ferp.samples.imagepicker.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

interface AddProfilePicLogger {
    fun logPicturePicked()
    fun logPicturePickCancelled()
    fun logPictureCleared()
    fun logSaveClick()
    fun logDismiss()
}

internal class AddProfilePicAnalyticsLogger @Inject constructor(
    private val analytics: FirebaseAnalytics
) : AddProfilePicLogger {

    override fun logPicturePicked() {
        analytics.logEvent(Events.PICTURE_PICKED, null)
    }

    override fun logPicturePickCancelled() {
        analytics.logEvent(Events.PICTURE_PICK_CANCELLED, null)
    }

    override fun logPictureCleared() {
        analytics.logEvent(Events.PICTURE_CLEARED, null)
    }

    override fun logSaveClick() {
        analytics.logEvent(Events.PICTURE_SAVE_CLICK, null)
    }

    override fun logDismiss() {
        analytics.logEvent(Events.PICTURE_DISMISS, null)
    }

    private object Events {
        const val PICTURE_PICKED = "profile_picture_picked"
        const val PICTURE_PICK_CANCELLED = "profile_picture_pick_cancelled"
        const val PICTURE_CLEARED = "profile_picture_cleared"
        const val PICTURE_SAVE_CLICK = "profile_picture_save_click"
        const val PICTURE_DISMISS = "profile_picture_dismiss"
    }
}