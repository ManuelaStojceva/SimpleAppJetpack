package test.com.simpleappjetpack.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import org.json.JSONException
import org.json.JSONObject
import test.com.simpleappjetpack.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Engine {
    companion object {
        fun newInstance() = Engine()
    }

    private var alertDialogBuilder: AlertDialog.Builder? = null
    fun readRaw(ctx: Context, res_id: Int): String {

        val `is` = ctx.resources.openRawResource(res_id)
        val reader = BufferedReader(InputStreamReader(`is`))
        var locs = ""
        var line: String? = ""
        while (line != null) {
            try {
                line = reader.readLine()
                if (line != null) {
                    locs += line
                }
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
        try {
            `is`.close()
            reader.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return locs

    }

    fun showMsgDialog(title: String, message: String, context: Activity) {

        try {
            if (alertDialogBuilder == null) {
                alertDialogBuilder = AlertDialog.Builder(context)
                alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                            alertDialogBuilder = null
                            dialog.cancel()
                        })
                val alert = alertDialogBuilder!!.create()
                if (title != "") {
                    alert.setTitle(title)
                } else
                    alert.setTitle("Alert")
                alert.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun handleServerError(context: Activity, error: String) {
        var error = error
        try {
            val `object` = JSONObject(error)
            error = `object`.getString("message")

            if (error.isEmpty())
                showMsgDialog("", context.getString(R.string.ErrorMsg), context)
            else
                showMsgDialog("", error, context)
        } catch (e: JSONException) {
            e.printStackTrace()
            showMsgDialog("", context.getString(R.string.ErrorMsg), context)
        }

    }
}