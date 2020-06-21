package my.shin.apipractice_20200613

import adapters.ReReplyAdapter
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import datas.TopicReply
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import my.shin.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseAcitivity() {

    var mReplyId = -1
    lateinit var mReply: TopicReply

    //    답글 목록을 담고있게 될 배열열
    val reReplyList = ArrayList<TopicReply>()
    lateinit var mReReplyAdapter : ReReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupevents()
        setvalues()
    }

    override fun setupevents() {

//        답글 삭제 이벤트 (리스트뷰 아이템 롱클릭)

        reReplyListview.setOnItemClickListener { parent, view, position, id ->

            val clickedReReply = reReplyList[position]

//            if (clickedReReply.userId ==)

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("답글 삭제")
            alert.setMessage("정말 답글을 삭제하시겠습니까? 받은 받은 좋아요 이력이 모두 삭제됩니다.")
            alert.setPositiveButton("확인",DialogInterface,DialogInterface.OnClickListener{dialog, which ->

                ServerUtil.deleteRequestReply(mContext,clickedReReply, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {

//                        서버의 메세지를 토스트로 출력
                        val message = json.getString("message")
                        runOnUiThread {Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()
                        }

//                        code :200 -> 성공
                        val code = json.getInt("code")

                        if (code ==200) {
//                            실제 삭제 ㅣ 목록 변화 필요함 = > 서버에서 다시 불러오기
                            getReplyDetailFromSever()
                        }

                    }

                })

            })
            alert.setNegativeButton("취소",null)
            alert.show()


            return@setOnItemClickListener true
        }

        postReplyBtn.setOnClickListener {
            val content = reReplyContentEdt.text.toString()

//            답글 등록 API 찾아보기 활용법 숙지

            ServerUtil.postRequestReReply(mContext,mReplyId, content, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
//                    서버에서 다시 의견에 대한 상세 현황 가져오기
                    getReplyDetailFromSever()
                }
            })





//            답글 등록 성공시 => 리스트뷰의 내용 새로 고침
//            서버에서 다시 답글 목록을 받아와서 추가
        }

    }

    override fun setvalues() {
        mReplyId = intent.getIntExtra("reply_id", -1)

//        어댑터를 먼저 생성 => 답글 목록을 뿌려준다고 명시

        mReReplyAdapter = ReReplyAdapter(mContext, R.layout.topic_re_reply_list_item, reReplyList)

//        어댑터와 리스트뷰 연결
        reReplyListview.adapter = mReReplyAdapter

//        서버에서 의견 상세 현황 가져오기
        getReplyDetailFromSever()

//        답글 등록시 성공 관련 UI 처리

        runOnUiThread {
            reReplyContentEdt.setText("")

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(reReplyContentEdt.windowToken, 0)
        }
    }

    fun getReplyDetailFromSever() {
        ServerUtil.getRequestReplyDetail(
            mContext,
            mReplyId,
            object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val data = json.getJSONObject("data")
                    val reply = data.getJSONObject("reply")
                    mReply = TopicReply.getTopicReplyFromJson(reply)

//                화면에 뿌려질 데이터를 받아보자
                    val reReplies = reply.getJSONArray("replies")

//                    기존에 담겨있던 답글 목록을 날리고

                    reReplyList.clear()

//                    다시 답글들을 추가해보자자

                   for (i in 0..reReplies.length() - 1) {
//                    jsonarry내부의 객체를 => TopicReply로 변환 -> re ReplyList에 추가
                        reReplyList.add(TopicReply.getTopicReplyFromJson(reReplies.getJSONObject(i)))
                    }

                    runOnUiThread {
                        sideTitleTxt.text = mReply.selectedSide.title
                        writerNickNameTxt.text = mReply.user.nickName
                        contentTxt.text = mReply.content
//                        서버에서 받아온 대댓글을 리스트뷰에 반영 -><
                        mReReplyAdapter.notifyDataSetChanged()

//                        내가 단 답글을 보기 편하도록 스크롤 처리
//                        답글 10개 => ArrayList는 0~9번까지.
//                        10개답글 => 9번을 보러 가는게 마지막으로 이동하는 행위임
                        reReplyListview.smoothScrollToPosition(reReplyList.size-1)

//                        서

//                    notifydatasetchage 필요함
                    }

                }
            })

    }


}
