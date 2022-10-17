import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogController {
    private lateinit var dialogAlert: AlertDialog

    private fun cantShowDialog(): Boolean =
        this::dialogAlert.isInitialized && dialogAlert.isShowing

    fun showDialogAlert(context: Context, title: String, msg: String) {
        if (cantShowDialog()) {
            return
        }

        dialogAlert = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                show()
            }
    }


}