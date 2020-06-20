package adapters

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.widget.ArrayAdapter
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

        val data = mList[Transliterator.Position]

        contentTxt.text = data.content

        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content

        return row

}