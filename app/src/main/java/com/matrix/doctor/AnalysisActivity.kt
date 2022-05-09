package com.matrix.doctor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.matrix.doctor.utilities.APIs
import com.matrix.doctor.utilities.APIs.Companion.HEART_BEAT
import com.matrix.doctor.utilities.APIs.Companion.PRESSURE
import com.matrix.doctor.utilities.APIs.Companion.TEMPRATURE
import com.svu.epetrol.session.SessionManager
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_analysis.*
import org.json.JSONArray
import org.json.JSONObject

class AnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        initPage()

        setupSaveButton()

    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupSaveButton() {
        imageButtonSave.setOnClickListener(View.OnClickListener {
            if(!TextUtils.isEmpty(edtHeartBeat.text)&&
                    !TextUtils.isEmpty(edtPressure.text)&&
                    !TextUtils.isEmpty(edtTemprature.text))
                saveData()
            else
                Toast.makeText(this,"أدخل البيانات أولا",Toast.LENGTH_SHORT).show()
        })
    }

    private fun saveData(){
        val client=AsyncHttpClient()

        val params=RequestParams()
        params.add(PRESSURE,edtPressure.text.toString().trim())
        params.add(TEMPRATURE,edtTemprature.text.toString().trim())
        params.add(HEART_BEAT,edtHeartBeat.text.toString().trim())
        val LINK="http://"+SessionManager.getInstance(this).host+"/HospitalHost/home/Create"

        client.post(this, LINK,params,object:AsyncHttpResponseHandler(){

            override fun onStart() {
                progressBarLoading.visibility=View.VISIBLE
                super.onStart()
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                //PDialog.hideProgressDialog()
                progressBarLoading.visibility=View.GONE
                // Warnings.show(SearchActivity.this,new String(responseBody));
                if(responseBody!=null) {
                    try {
                        val res = JSONObject(String(responseBody))
                        extractAndView(res)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                //PDialog.hideProgressDialog()
                // Warnings.show(SearchActivity.this,"falure "+error.getMessage());
                progressBarLoading.visibility=View.GONE
                Toast.makeText(this@AnalysisActivity,"حدثت مشكلة , لم يتم الحفظ",Toast.LENGTH_SHORT).show()
                Log.e("Search Activity", error?.message)
            }
        })
    }

    fun initPage(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Examination")
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun extractAndView(res: JSONObject) {

        try {
            val success = res.getBoolean(APIs.SUCCESS)
            if(success){
                Toast.makeText(this,"تم الحفظ بنجاح",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"حدثت مشكلة , لم يتم الحفظ",Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
