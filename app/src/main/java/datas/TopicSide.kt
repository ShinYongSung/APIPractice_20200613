package datas

import org.json.JSONObject

// 주제의 하위 개념 : 선택 가능 진영 정보

class TopicSide {

//    JSON => TopicSide로 변환 가능
    companion object{

    fun getTopicSideFromJson(json : JSONObject) : TopicSide{
        val ts = TopicSide()

        ts.id = json.getInt("id")
        ts.topicId = json.getInt("topic_id")
        ts.title= json.getString("title")
        ts.voteCount = json.getInt("vote_count")

//        주제 파싱중 => 선택진영들 JSONArray을 가져오기
        val sides = json.getJSONArray("sides")

//        JSONArray 내부를 돌면서 파싱

        for (i in 0..sides.length()-1) {

//            선택진영 하나의 정보가 담긴 JSONObject 추출
            val sideJson = sides.getJSONObject(i)

//            이 JSON을 선택진영으로 변환해주는 기능 사용
            val  side = TopicSide.getTopicSideFromJson(sideJson)

//            이 주제의 진영 목록으로 추가

        }

        return ts

    }
}

    var id = 0
    var topicId = 0
    var title = ""
    var voteCount = 0


}