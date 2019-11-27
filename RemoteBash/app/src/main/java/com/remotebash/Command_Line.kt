package com.remotebash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_command__line.*

class Command_Line : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command__line)

        tvRetorno.text = "a\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk\nl\nm\no\np\nq\nr\ns\nt\nu\nv\nw\nx\ny\nz\na\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk\nl\nm\no\np\nq\nr\ns\nt\nu\nv\nw\nx\ny\nz"
    }


}
