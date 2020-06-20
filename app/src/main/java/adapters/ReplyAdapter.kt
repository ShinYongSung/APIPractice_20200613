package adapters

import android.content.Context
import android.icu.text.Transliterator
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import datas.TopicReply
import my.shin.apipractice_20200613.R
import my.shin.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(val mContext:Context, val resId:Int, val mList:List<TopicReply>):

    ArrayAdapter <TopicReply>(mContext,resId,mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        temprow?.let {

        }.let {
            temprow = inf.inflate(R.layout.topic_reply_list_item, null)

        }

        val row = temprow!!

        val writerNicknameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentText = row.findViewById<TextView>(R.id.contentTxt)
        val replyBtn = row.findViewById<Button>(R.id.replyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)


        val data = mList[position]

        writerNicknameTxt.text = data .user.nickName
        contentText.text = data .content

        replyBtn.text = "답글 : ${data .replyCount}"
        likeBtn.text = "좋아요 : ${data .likeCount}"
        dislikeBtn.text = "실어요 : ${data .dislikeCount}"

        //    좋아요 / 싫어요 이벤트 처리
        likeBtn.setOnClickListener {
//        좋아요 API 호출 => 좋아요 누르기 / 취소 처리
//        화면에 변경된 좋아요 / 싫어요 갯수 반영 (응용)

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
//                    화면에 변경된 좋아요 / 싫어요 방법
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

//                    목록에서 꺼낸 data변수의 객체를 통째로 바꾸는건 불가능.
//                    var로 바꿔서 통쨰로 바꿔도 => 목록에는 반영되지 않음.
//                    data = TopicReply.getTopicReplyFromJson()
//                    목록에서 꺼낸 data 변수의 좋아요 갯수 / 싫어요 갯수를 직접 변경
                    data.likeCount = reply.getInt("like_count")
                    data.likeCount = reply.getInt("dislike_count")

//                    목록의 내용을 일부 변경 => 반영하려면
//                    어댑터.notifyDataSetChanged() 실행 필요함
//                    이미 어댑터 내부에 있는 상황 => 곧바로 notifyDataSetChanged() 실행 가능

//                    RunOnUiThread로 처리 필요 => 어댑터내부에선 사용 불가.
//                    대체재 : Handler(Looper.getMainLooper()).post (UI 쓰레드 접근하는 다른 방법)
                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }


                }

            })

        }

        dislikeBtn.setOnClickListener {
            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id, false, )

        }

        return row
    }


}