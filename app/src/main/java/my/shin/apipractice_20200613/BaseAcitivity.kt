package my.shin.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity

abstract class BaseAcitivity : AppCompatActivity() {

    val mContext =this

    abstract fun setupevents()
    abstract fun setvalues()


}