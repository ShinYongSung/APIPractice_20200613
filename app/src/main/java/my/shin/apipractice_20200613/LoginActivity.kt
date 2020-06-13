package my.shin.apipractice_20200613

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseAcitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupevents()
        setvalues()
    }


    override fun setupevents() {

        loginBtn.setOnClickListener {
            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            실제로 서버에 두개의 변수를 전달해서 로그인 시도

        }

    }

    override fun setvalues() {

    }



}
