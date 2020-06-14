package my.shin.apipractice_20200613

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : BaseAcitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun setupevents() {

    }

    override fun setvalues() {

//        3초 뒤에 다음 화면으로 이동

        val  myHandler = Handler()

        myHandler.postDelayed({
            val  myIntent = Intent(mcontext, LoginActivity::class.java)
            startActivity(myIntent)

            finish()
//            일정 시간이 지난 뒤에 실행할 내용
        }, 3000)




    }


}
