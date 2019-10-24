package ua.ck.geekhub.lesson1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var nameEditText: EditText? = null
    private var emailMessageTextView: TextView? = null
    private var sendButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.name_edit_text)
        emailMessageTextView = findViewById(R.id.email_message_text_view)
        sendButton = findViewById(R.id.send_button)

        findViewById<Button>(R.id.next_button).setOnClickListener(this)
        sendButton?.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GET_MESSAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showMessage(data?.getStringExtra(MessageActivity.EXTRA_MESSAGE))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getString(SAVE_EXTRA_MESSAGE)?.let { showMessage(it) }
    }

    private fun showMessage(message: String?) {
        emailMessageTextView?.text = message
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(SAVE_EXTRA_MESSAGE, emailMessageTextView?.text?.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.next_button -> startMessageActivity()
            R.id.send_button -> startSendingEmail()
        }
    }

    private fun startMessageActivity() {
        val intent = Intent(this, MessageActivity::class.java)
        val name = nameEditText?.text?.toString()
        if (name.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.blank_name_alert), Toast.LENGTH_LONG).show()
            return
        }
        intent.putExtra(MessageActivity.EXTRA_NAME, name)
        startActivityForResult(intent, GET_MESSAGE_REQUEST_CODE)
    }

    private fun startSendingEmail() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
        intent.putExtra(Intent.EXTRA_TEXT, emailMessageTextView?.text?.toString())
        val chooser = Intent.createChooser(intent, getString(R.string.choose_email_app_title))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    companion object {
        private const val SAVE_EXTRA_MESSAGE = "SAVE_EXTRA_MESSAGE"
        private const val GET_MESSAGE_REQUEST_CODE = 100
    }
}
