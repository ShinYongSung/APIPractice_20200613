package adapters

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import datas.TopicReply
import my.shin.apipractice_20200613.R
import my.shin.utils.ServerUtil

class ReplyAdapter(val mContext, val resId:Int, val mList:List<TopicReply>): ArrayAdapter <TopicReply>(mContext,resId,mList) {

    val inf = LayoutInflater.from(mContext)

    override fun onResponse(json : ServerUtil.Jsonobject)

        var temprow = convertView

        temprow?.Let{

        }.Let{
            temprow = inf.inflate(R.layout.topic_reply_list_item, null)
        }

        val row = temprow!!

        val writerNicknameTxt = row.findviewById<TextView>(R.id.writerNickNameTxt)
        val contentText = row.findViewById <TextView>(R.id.contentTxt)
        val replyBtn = row.findViewById <Button>(R.id.replyBtn)
        val likeBtn = row.findViewById <Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById <Button>(R.id.dislikeBtn)



        contentTxt.text = data.content


        val data = mList[Transliterator.Position]


        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content

        replyBtn.text = "답글 : ${data.replyCount}"
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "실어요 : ${data.dislikeCount}"

        return row

}