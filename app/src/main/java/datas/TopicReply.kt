package datas

import org.json.JSONObject
import datas.TopicSide.Companion as TopicSide1

class TopicReply {

    companion object {
        fun getTopicReplyFromJson(json : JSONObject) : TopicReply {
            val tr = TopicReply()
            
            tr.id = json.getInt("id")
            tr.content = json.getString("content")
            tr.topicId = json.getInt("topic_id")
            tr.sideId = json.getInt("side_id")
            tr.userId = json.getInt("user_id")

//            tr.user =??  // 의견 JSON의 항목중 user JSONOBJECT를 => ser 클래스에 전달
//            user 클래스가 Json을 받아서 => User로 변환해서 리턴. 그걸 tr.user에 대입

            val userJson = json.getJSONObject("user")
            tr.user = User.getUserFromJson(userJson)

//            이 댓글이 어느 진영인지 파싱
//            댓글 json => selected_side JSONObject 추출 => TopicSide로 변환 => tr. 선택진영 저장

            tr.selectedSide = TopicSide.getTopicSideFromJson(json.getJSONObject("selected_side"))


//            답글 / 좋아요 / 싫어요 갯수 피싱
            tr.replyCount = json.getInt("reply_count")
            tr.likeCount = json.getInt("like_count")
            tr.dislikeCount = json.getInt("dislike_count")

            tr.isMyLike = json.getBoolean("my_like")
            tr.isMyDislike = json.getBoolean("my_dislike")

            return tr
        }
    }

    var id = 0
    var content = ""
    var topicId = 0
    var sideId = 0
    var userId = 0
    lateinit var user : User
    lateinit var selectedSide = TopicSide1


//    답글 / 좋아요 / 실헝요 갯수 저장 변수
    var replyCount = 0
    var likeCount = 0
    var dislikeCount = 0

//    내가 좋아요 / 싫어요 누른 상태인지

    var isMyLike = false
    var isMyDislike = false





}