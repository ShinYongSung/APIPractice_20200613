package my.shin.apipractice_20200613

import adapters.EditReplyActivity
import adapters.ReplyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import datas.Topic
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import my.shin.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseAcitivity() {



//    화면에 넘겨준 주제 id값을 저장할 변수
    var mTopicId = -1

    lateinit var mTopic : Topic

    lateinit var mReplyAdapter : ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupevents()
        setvalues()
    }



    override fun setupevents() {

        replyBtn.setOnClickListener {

            mTopic.mySelectedSide?.let {
                val myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("TopicTitle", mTopic.title)
                myIntent.putExtra("selectedSideTitle", it.title)
                startActivity(myIntent)
            }.let {
                if (it == null) {
                    //                mySelectedSide가 null인 경우 = > 투표를 아직 안한 경우
                    Toast.makeText(mContext, "맘에 드는 진영을 선택해야 의견을 남길 수 있습니다.", Toast.LENGTH_SHORT).show()
                }

            }


        }

        voteToFirstBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext,mTopic.sides[0].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                }
            })
        }
        voteToSecondBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext,mTopic.sides[1].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            Toast.makeText(mContext, "참여해주셔서 감사합니다.", Toast.LENGTH_SHORT).show()
                       }
                    }

                    else {
                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }



                    }
                }
            })
        }


    }

    override fun setvalues() {
        mTopicId = intent.getIntExtra("topic_id",-1)

        if (mTopicId == -1) {
//            주제 id 가 -1로 남아있다 : topic_id 첨부가 제대로 되지 않았다.
            Toast.makeText(mContext,"잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
//            코드 추가 진행을 막자.(강제종료)
            return

        }

        Log.d("넘겨받은 주제 id", mTopicId.toString())
        
//        넘겨 받은 id값으로 서버에서 주제의 상세 진행 상황 받아오기

       fun getTopicdatFromSever(){
           ServerUtil.getRequestTopicDetail(mContext,mTopicId,object : ServerUtil.JsonResponseHandler{
               override fun onResponse(json: JSONObject) {

                   val code = json.getInt("code")

                   if (code == 200){
                       val data = json.getJSONObject("data")
                       val topic = data.getJSONObject("topic")

//                    멤버변수 mtopic에 서버에서 내려준 내용을 피싱
                       mTopic = Topic.getTopicFromJson(topic)

                       runOnUiThread {
//                    받아온 주제의 제목을 화면에 표시
                           topicTitleTxt.text = mTopic.title

                           Glide.with(mContext).load(mTopic.imageUrl).into(topicImg)

//                        선택 진영 정보도 출력
                           firstSideTxt.text = mTopic.sides[0].title
                           secondSideTxt.text = mTopic.sides[1].title

//                        투표 현황도 파싱 된 데이터를 같이 사용.

                           firstSideVoteCountTxt.text = "${mTopic.sides[0].voteCount}표"
                           SecondSideVoteCountTxt.text = "${mTopic.sides[1].voteCount}표"

                           mReplyAdapter = ReplyAdapter(mContext, R.layout.topic_list_item,mTopic.replies)
                           replyListView.adapter = mReplyAdapter
                       }

                   }

               }

           })


       }
    }


}
