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
import my.shin.apipractice_20200613.R
import my.shin.apipractice_20200613.ViewReplyDetailActivity
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
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)


        val data = mList[position]

        writerNicknameTxt.text = data .user.nickName
        contentText.text = data .content

//        어떤 댓글을 옹호하는지? (진영 이름) 양식으로 표현
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"

        replyBtn.text = "답글 : ${data .replyCount}"
        likeBtn.text = "좋아요 : ${data .likeCount}"
        dislikeBtn.text = "실어요 : ${data .dislikeCount}"

//        내가 좋아요 / 싫어요 여부 표시
        if (data.isMyDislike){
//            내가 좋아요를 찍은 댓글일 경우
//            좋아요 빨간색 / 싫어요 회색
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
//            글씨 색 : 빨간색 => res => colors => red를 사용
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

        else if (data.isMyDislike) {
            //내가 싫어요를 찍은 댓글일 경우
//            좋아요 회색 / 싫어요 파란색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))

        }
        else {
//            아무것도 찍지 않은 경우
//            둘다 회색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))

        }
        
//        답글버튼 눌림 처리
        
        replyBtn.setOnClickListener { 
            val myIntent = Intent(mContext, ViewReplyDetailActivity ::class.java)
            myIntent.putExtra("reply_id", data.id)
            
//            Adapter에서 직접 start Activity 불가
//            mContext  도움 받아서 startActivity 실행
            mContext.startActivity(myIntent)
        }
        


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
            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id, false, object : ServerUtil.JsonResponseHandler{
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