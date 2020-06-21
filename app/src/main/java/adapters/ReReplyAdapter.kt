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

class ReReplyAdapter(val mContext:Context, val resId:Int, val mList:List<TopicReply>):

    ArrayAdapter <TopicReply>(mContext,resId,mList) {

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

        if (data.isMyLike){
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        }

        else if (data.isMyDislike){
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

        return row
    }


}