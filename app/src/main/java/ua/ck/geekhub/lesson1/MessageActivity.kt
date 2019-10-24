package ua.ck.geekhub.lesson1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
 
class MessageActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val name = intent.getStringExtra(EXTRA_NAME)

        findViewById<Button>(R.id.student_button).also {
            it.setOnClickListener(this)
            it.text = getString(R.string.student_message, name)
        }
        findViewById<Button>(R.id.teacher_button).also {
            it.setOnClickListener(this)
            it.text = getString(R.string.teacher_message, name)
        }
    }

    override fun onClick(view: View?) {
        val message = (view as? Button)?.text?.toString()
        val intent = Intent()
        intent.putExtra(EXTRA_MESSAGE, message)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_MESSAGE = "EXTRA_NAME"
    }
}