package so.codex.hawk.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.btn

import so.codex.hawk.R
import so.codex.hawk.notification.domain.NotificationContainer
import so.codex.hawk.notification.model.NotificationModel
import so.codex.hawk.notification.model.NotificationType

/**
 * Main application class.
 * Will be used only after successful authorization.
 */
class MainActivity : AppCompatActivity() {

    /**
     * The method is designed to initialize the activity (setting the root view element
     * and other ui elements).
     *
     * @param savedInstanceState This argument is supplied by the system.
     *                           An instance of the Bundle class is used to restore the required
     *                           values when re-creating an activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener{
            notificationContainer.show(NotificationModel("error",NotificationType.ERROR))
        }
    }




    /**
     * @property notificationContainer A container that delegates logic for processing and displaying
     * notifications
     */
    private val notificationContainer = NotificationContainer()

    override fun onStart() {
        super.onStart()
        notificationContainer.attach(this)
    }

    override fun onStop() {
        super.onStop()
        notificationContainer.detach()
    }
}
