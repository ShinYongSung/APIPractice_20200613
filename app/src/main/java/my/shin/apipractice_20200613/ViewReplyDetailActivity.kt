package my.shin.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import datas.TopicReply
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import my.shin.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseAcitivity() {

    var mReplyId = -1
    lateinit var mReply : TopicReply

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupevents()
        setvalues()
    }

    override fun setupevents() {

    }

    override fun setvalues() {
        mReplyId = intent.getIntExtra("reply_id",-1)

//        서버에서 의견 상세 현황 가져오기
        getReplyDetailFromSever()
    }

    fun getReplyDetailFromSever() {
        ServerUtil.getRequestReplyDetail(mContext,mReplyId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

                runOnUiThread {
                    sideTitleTxt.text = mReply.selectedSide.title
                    writerNickNameTxt.text = mReply.user.nickName
                    contentTxt.text = mReply.content
                }

            }
        })

    }


}
