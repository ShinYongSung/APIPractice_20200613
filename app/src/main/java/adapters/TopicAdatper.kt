package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import datas.Topic
import my.shin.apipractice_20200613.R

class TopicAdatper (val mContext :Context, val  resId : Int, val  mList:List<Topic>) : ArrayAdapter<Topic>(mContext,resId,mList){

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.topic_list_item, null)
        }

        val row = tempRow!!

//        리스트 아이템에 데이터를 반영할 뷰 가져오기기

       val topicImg = row.findViewById<ImageView>(R.id.topicImg)
        val topicTitleTxt = row.findViewById<TextView>(R.id.topicTitleTxt)

//        뿌려줄 근거 데이터 가져오기
        val data = mList[position]

//        실데이터 반영 작업
        topicTitleTxt.text = data.title


//        글라이드 라이브러리 사용
        Glide.with(mContext).load(data.imageUrl).into(topicImg)

        return row
    }
}