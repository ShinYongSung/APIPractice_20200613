package adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_reply.*
import my.shin.apipractice_20200613.BaseAcitivity
import my.shin.apipractice_20200613.R

class EditReplyActivity : BaseAcitivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupevents()
        setvalues()

    }

    override fun setupevents() {

    }

    override fun setvalues() {

        topicTitleTxt.text = intent.getStringExtra("topicTitle")
    }


}