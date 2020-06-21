package my.shin.apipractice_20200613

import adapters.ReReplyAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        postReplyBtn.setOnClickListener {
            val content = reReplyContentEdt.text.toString()

//            답글 등록 API 찾아보기 활용법 숙지

            ServerUtil.postRequestReReply(mContext,mReplyId, content, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
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

//                        서

//                    notifydatasetchage 필요함
                    }

                }
            })

    }


}
