package my.shin.apipractice_20200613

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import my.shin.utils.ContextUtill
import my.shin.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseAcitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupevents()
        setvalues()
    }

    override fun setupevents() {

    }

    override fun setvalues() {

//        3초 뒤에 다음 화면으로 이동

        val  myHandler = Handler()

        myHandler.postDelayed({
//            일정 시간이 지난 뒤에 실행할 내용
//            자동로그인? => 안한다면 무조건 로그인 호마ㅕㄴ으로
//            한다고하면? => 저장된 토큰이 있나? => 있다면 => 서버에서 사용자 정보를 가져오는가?
//            정보를 가져오기까지 성공하면 => 메인화면(토큰의 유효성을 검정)

            if (ContextUtill.isAutoLogin(mcontext)) {
                if (ContextUtill.getUserToken(mcontext) !=""){
                    ServerUtil.getRequestMyInfo(mcontext, object : ServerUtil.JsonResponseHandler {
                        override fun onResponse(json: JSONObject) {

                            val code = json.getInt("code")
                            if (code ==200) {
                                val  myIntent = Intent(mcontext, MainActivity::class.java)
                                startActivity(myIntent)
                                finish()
                            }
                            else {
                                val  myIntent = Intent(mcontext, LoginActivity::class.java)
                                startActivity(myIntent)
                                finish()
                            }
                        }
                    })
                }

                else {
                    val  myIntent = Intent(mcontext, LoginActivity::class.java)
                    startActivity(myIntent)
                    finish()
                }

            }

            else {
                val  myIntent = Intent(mcontext, LoginActivity::class.java)
                startActivity(myIntent)
                finish()
            }
        },3000)




    }


}
