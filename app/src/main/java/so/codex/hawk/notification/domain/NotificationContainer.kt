package so.codex.hawk.notification.domain

import android.app.Activity
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.Disposable
import so.codex.hawk.R
import so.codex.hawk.custom.views.NotificationContainerView
import so.codex.hawk.notification.model.NotificationModel
import so.codex.hawk.notification.presenter.NotificationPresenterImpl
import so.codex.hawk.notification.presenter.NotificationView
import timber.log.Timber

/**
 * The container to delegate responsibility for the processing to display notifications on the host
 */
class NotificationContainer : NotificationView {
    /**
     * @property presenter Provide stream of notification to display
     */
    private val presenter = NotificationPresenterImpl(this, NotificationManager)

    /**
     * @property container View of the container where the notification is placed
     */
    private var container: NotificationContainerView? = null

    /**
     * @property disposable For dispose the resource of subscription from [presenter]
     */
    private var disposable: Disposable? = null

    /**
     * Try to get container that should be placed notification with content
     * @param activity Host of container
     */
    fun attach(activity: Activity) {
        container = activity.findViewById(R.id.notification_container)
        onAttach()
    }

    /**
     * Try to get container that should be placed notification with content
     *
     * @param fragment Host of container
     */
    fun attach(fragment: Fragment) {
        container = fragment.view?.findViewById(R.id.notification_container)
        onAttach()
    }

    /**
     * Invoke after getting container for notification, if we can find container and subscribe on
     * stream of notification.
     */
    private fun onAttach() {
        if (container != null)
            disposable = presenter.subscribe()
    }

    /**
     * Detach of container and free resources, need call if host start to destroy
     */
    fun detach() {
        container = null
        disposable?.dispose()
    }

    /**
     * Show notification in container by [NotificationModel]
     *
     * @param model data that contains information for showing notification
     */
    override fun show(model: NotificationModel) {
        if (container == null) {
        } else {
            container?.updateNotification(model)
        }
    }
}