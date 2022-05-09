package com.matrix.doctor

import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.text.TextUtils
import android.transition.Slide
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.svu.epetrol.session.SessionManager
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initTransition()

        window.allowEnterTransitionOverlap=false
    }

    public fun btnExamination(view: View){

        val activityOption= ActivityOptions.makeSceneTransitionAnimation(this,
            android.util.Pair(imgExamination,"img_examination_shared"),
            android.util.Pair(txtExamination,"txt_examination_shared"),
            android.util.Pair(txtGrid,"smarthed_shared"))

        val intent= Intent(this,AnalysisActivity::class.java)
        startActivity(intent,activityOption.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.item_host -> {
                showAlertDialog();
            }
        }
        return true;
    }

    fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this);
        alertDialog.setTitle("Ip Address");
        alertDialog.setMessage("Enter IP");

        val txtHost = EditText(this);
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        txtHost.layoutParams = lp
        alertDialog.setView(txtHost)

        alertDialog.setPositiveButton("Ok",
            object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if(! TextUtils.isEmpty(txtHost.text)) {
                        SessionManager.getInstance(this@MenuActivity)
                            .setUrlHost(txtHost.text.toString().trim());
                        Toast.makeText(this@MenuActivity,"Ip:${txtHost.text.trim()} saved",Toast.LENGTH_LONG).show();
                    }
                }
            });

        alertDialog.setNegativeButton("Cancel",
            object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.cancel();
                }
            });

        alertDialog.show();
    }

    fun initTransition(){

        val enterTransition= Slide()
        enterTransition.slideEdge= Gravity.LEFT
        enterTransition.duration=resources.getInteger(R.integer.anim_duration_long).toLong()
        enterTransition.interpolator= LinearInterpolator()
        window.enterTransition=enterTransition

        }

}
