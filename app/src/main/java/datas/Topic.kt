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

            for (i in 0..sides.length()-1) {

//            선택진영 하나의 정보가 담긴 JSONObject 추출
                val sideJson = sides.getJSONObject(i)

//            이 JSON을 선택진영으로 변환해주는 기능 사용
                val  side = TopicSide.getTopicSideFromJson(sideJson)

//            이 주제의 진영 목록으로 추가

            }

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