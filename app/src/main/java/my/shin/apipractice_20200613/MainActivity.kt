package my.shin.apipractice_20200613

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
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
