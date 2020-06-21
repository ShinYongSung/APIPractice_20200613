package adapters

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import my.shin.apipractice_20200613.R
import my.shin.apipractice_20200613.ViewReplyDetailActivity
import my.shin.utils.ServerUtil
import org.json.JSONObject

class ReReplyAdapter(val mContext: Context, val resId: Int, val mList: List<TopicReply>) :

    ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var temprow = convertView

        temprow?.let {

        }.let {
            temprow = inf.inflate(R.layout.topic_re_reply_list_item, null)

        }

        val row = temprow!!

        val writerNicknameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentText = row.findViewById<TextView>(R.id.contentTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

        val data = mList[position]

        writerNicknameTxt.text = data.user.nickName
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"

//        selectedSideTitleTxt.text = data.selectedSide.title -> 가로때매 넣은거임
        contentText.text = data.content

//        좋아요 / 싫어요 버튼 관련 표시
//        몇명이 좋아요 ? 숫자 표시
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        내 좋아요 여부에 따라 모양 변경

        if (data.isMyLike) {
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        } else if (data.isMyDislike) {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        } else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

        likeBtn.setOnClickListener {
//        좋아요 API 호출 => 좋아요 누르기 / 취소 처리
//        화면에 변경된 좋아요 / 싫어요 갯수 반영 (응용)

            ServerUtil.postRequestReplyLikeOrDislike(
                mContext,
                data.id,
                true,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
//                    화면에 변경된 좋아요 / 싫어요 방법
                        val dataObj = json.getJSONObject("data")
                        val reply = dataObj.getJSONObject("reply")

//                    목록에서 꺼낸 data변수의 객체를 통째로 바꾸는건 불가능.
//                    var로 바꿔서 통쨰로 바꿔도 => 목록에는 반영되지 않음.
//                    data = TopicReply.getTopicReplyFromJson()
//                    목록에서 꺼낸 data 변수의 좋아요 갯수 / 싫어요 갯수를 직접 변경
                        data.likeCount = reply.getInt("like_count")
                        data.dislikeCount = reply.getInt("dislike_count")

                        data.isMyLike = reply.getBoolean("my_like")
                        data.isMyDislike = reply.getBoolean("my_dislike")

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
            ServerUtil.postRequestReplyLikeOrDislike(
                mContext,
                data.id,
                false,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        val dataObj = json.getJSONObject("data")
                        val reply = dataObj.getJSONObject("reply")


                        data.likeCount = reply.getInt("like_count")
                        data.dislikeCount = reply.getInt("dislike_count")
                        data.isMyLike = reply.getBoolean("my_like")
                        data.isMyDislike = reply.getBoolean("my_dislike")

                        Handler(Looper.getMainLooper()).post {
                            notifyDataSetChanged()
                        }
                    }
                })
        }


        return row
    }


}