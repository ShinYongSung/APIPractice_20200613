package my.shin.utils

import android.content.Context

class ContextUtill {

    companion object {
//        메모장의 파일 이름에 대응되는 개념으로 만든 변수
        private val prefName = "APIPracticePreference"

//        private는 상속된것을 다른곳에서 막아주는역할.

//        저장될 데이터 항목 이름들을 변수로 설정(안해도 되지만, 글자 실수로 못불러오는 현상 발생할 수 도 있어)

        private val USER_TOKEN = "USER_TOKEN"
        private val AUTO_LOGIN = "AUTO_LOGIN"

//        자동로그인 getter / setter

        fun setAutoLogin(context: Context, autoLogin:Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun isAutoLogin(context: Context) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }

//        토큰 저장 기능
        fun setUserToken(context: Context, token:String) {
//         저장할 때 사용할 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//            열어준 메모장의 user_token항목에 받아온 token에 든 값을 저장.
            pref.edit().putString(USER_TOKEN, token).apply()

        }

//        저장된 토큰 불러오기
        fun getUserToken(context: Context) : String{
//         저장할때 사용한 메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//          열어준 메모장의 USER_TOKEN 항목에 저장된 token값을 꺼내서 리턴
            return pref.getString(USER_TOKEN, "")!!
        }

    }

}