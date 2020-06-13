package my.shin.apipractice_20200613

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sing_up.*
import my.shin.utils.SeverUtil
import org.json.JSONObject

class SingUpActivity : BaseAcitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
    }

    override fun setupevents() {

        emailCheckBtn.setOnClickListener {
//            입력한 이메일이 이미 회원으로 있는지 확인 => 서버에 요청

            val inputEmail = emailEdt.text.toString()

            SeverUtil.getRequestDuplicatedCheck(mcontext,"EMAIL", inputEmail, object : SeverUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")
                    if (code == 200) {
                       emailCheckResultTxt.text = "사용해도 좋습니다."
                    }
                    else{
                        runOnUiThread { emailCheckResultTxt.text = "이미 사용중입니다. 다른 이멩일로 다시 체크해주세요."
                        }


                    }

                }

            })



        }

    }

    override fun setvalues() {

    }


}
