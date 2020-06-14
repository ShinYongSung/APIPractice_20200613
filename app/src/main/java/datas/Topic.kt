package datas

import org.json.JSONObject

class Topic {

    //    Json을 넣으면 Topic 객체로 변환해주는 기능
    companion object {
        fun getTopicFromJson(json : JSONObject) : Topic {
            val topic = Topic()

//            빈 내용으로 만든 topic의 내용을
//            json에 담겨있는 내용을 가지고 채워준다.

            topic.id = json.getInt("id")
            topic.title = json.getString("title")
            topic.imageUrl = json.getString("img_url")

            val sideJson = sides.getJ

            val side = TopicSide.getTopicSideFromJson(sideJson)

            return topic
        }
    }

    var id = 0
    var title = ""
    var imageUrl = ""
//    선택 가능 진영 목록을 담는 배열
    val sides = ArrayList<TopicSide>()






}