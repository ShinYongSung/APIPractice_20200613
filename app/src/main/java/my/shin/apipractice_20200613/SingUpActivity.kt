package my.shin.apipractice_20200613

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sing_up.*
import kotlinx.android.synthetic.main.activity_sing_up.emailEdt
import my.shin.utils.SeverUtil
import org.json.JSONObject
import kotlin.math.log

class SingUpActivity : BaseAcitivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
    }

    override fun setupevents() {

        signUPBtn.

        emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//              글자가 변경된 시점에 실행되는 함수
//                Log.d("변경된 내용", s.toString())

//                이메일 중복검사를 하라고 안내
                emailCheckResultTxt.text = "중복검사를 해주세요."
            }

        })

        nickNameEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


        })

    }


        nickNameCheckBtn.setOnClickListener {
            val inputNick = nickNameEdt.text.toString()

            SeverUtil.getRequestDuplicatedCheck(mcontext, "NICKNAME", inputNick, object : SeverUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            nickNameCheckResultTxt.text = "사용해도 좋습니다."
                        }
                    }
                    else {
                        runOnUiThread {
                            nickNameCheckResultTxt.text = " 중복된 닉네임입니다. 철자 확인하세요."
                        }

                }

            }
            )


        }


        emailcheckBtn.setOnClickListener {
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
