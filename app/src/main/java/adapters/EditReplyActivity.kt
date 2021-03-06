package adapters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_reply.*
import my.shin.apipractice_20200613.BaseAcitivity
import my.shin.apipractice_20200613.R
import my.shin.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseAcitivity() {

    //    토론중인 주제의 id 저장할 변수
//    -1 : 잘못된 데이터로 초기값 세팅. =>계속 -1로 남아있다면 오류 처리 진행.
    var mTopicId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupevents()
        setvalues()

    }

    override fun setupevents() {

        postReplyBtn.setOnClickListener {

//           의견은 최소 10글자 이상이어야 하도록 제한 처리
            val inputContent = contentEdt.text.toString()

            if (inputContent.length < 10) {
                Toast.makeText(mContext, "의견은 최소 10글자 이상이어야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            한번 등록하면 내용 수정할 수 없다고 안내/

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("내용 변경 불가 안내")
            alert.setMessage("한번 등록한 의견은 내용을 수정할 수 없습니다. 의견을 등록한 뒤에는 재투표가 불가능합니다.")
            alert.setPositiveButton("확인", { dialog, which ->
                ServerUtil.postRequestReply(
                    mContext,
                    mTopicId,
                    inputContent,
                    object : ServerUtil.JsonResponseHandler {
                        override fun onResponse(json: JSONObject) {
                            val code = json.getInt("code")
                            if (code == 200) {
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        "의견 등록에 성공했습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                finish()
                            } else {
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        "의견 등록에 실패했습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                    })
            })



            alert.setNegativeButton("취소", null)
            alert.show()
//            서버에 요청해서 의견 등록 처리. -> 추가 데이터를 받아와야함


        }

    }

    override fun setvalues() {

        topicTitleTxt.text = intent.getStringExtra("topicTitle")
        selectedSideTitleTxt.text = intent.getStringExtra("selectedSideTitleTxt")
        mTopicId = intent.getIntExtra("topicid", -1)

//        MTopicId가 계속 -1이면 =>주제 id가 첨부 안된 상황
//        에러를 대응하기 위한 코드

        if (mTopicId == -1) {
            Toast.makeText(mContext, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}
