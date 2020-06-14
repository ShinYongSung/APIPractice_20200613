package my.shin.apipractice_20200613

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import my.shin.utils.ContextUtill
import my.shin.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseAcitivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupevents()
        setvalues()
    }

    override fun setupevents() {
        logoutBtn.setOnClickListener {
//            정말 로그아웃 할건지? 확인받자
            val alert = AlertDialog.Builder(mcontext)
//            alertdialog는 x로 시작하는 android로
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//                실제로 로그아웃 하는 방법 => 저장된 토크을 삭제 (빈칸으로 변경
                ContextUtill.setUserToken(mcontext, "")
            })
            alert.setNegativeButton("취소",null)
            alert.show()

            val myIntent = Intent(mcontext, LoginActivity::class.java)
            startActivity(myIntent)

            finish()
        }
    }

    override fun setvalues() {
//        서버에서 내 정보를 받아와서 화면에 출력
        ServerUtil.getRequestMyInfo(mcontext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val user = data.getJSONObject("user")
                val nickname = user.getString("nick_name")

                runOnUiThread {
                    loginUserNickNameTxt.text = "${nickname}님 환영합니다."

                }


            }
        })


    }


}
