package my.shin.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseAcitivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupevents()
        setvalues()
    }

    override fun setupevents() {
    }

    override fun setvalues() {
//        서버에서 내 정보를 받아와서 화면에 출력
    }


}
