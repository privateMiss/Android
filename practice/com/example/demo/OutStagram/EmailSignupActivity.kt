package com.example.myapplication.OutStagram

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.*
import retrofit2.Call
import retrofit2.Response

class EmailSignupActivity : AppCompatActivity() {

    lateinit var usernameView : EditText
    lateinit var userPassword1View : EditText
    lateinit var userPassword2View : EditText
    lateinit var registerButton : TextView
    lateinit var loginButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if((application as MasterApplication).checkIsLogin()) {
            finish()
            startActivity(Intent(this, OutStagramPostListActivity::class.java))
        }else {
            setContentView(R.layout.activity_email_signup)

            initView(this@EmailSignupActivity)
            setupListener(this)
        }
    }


    fun setupListener(activity: Activity) {
        registerButton.setOnClickListener {
            register(this@EmailSignupActivity)
        }
        loginButton.setOnClickListener {
            startActivity(
                Intent(
                    this@EmailSignupActivity,
                    LoginActivity::class.java
                )
            )
        }
    }

    fun register(activity: Activity) {
        val username = getUserName()
        val password1 = getUserPassword1()
        val password2 = getUserPassword2()

        (application as MasterApplication).service.register(
            username, password1, password2
        ).enqueue(object :
            retrofit2.Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, "가입에 실패 하였습니다", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Toast.makeText(activity, "가입에 성공하였습니다", Toast.LENGTH_LONG).show()
                val user = response.body()
                val token = user!!.token!!
                saveUserToken(token, activity)
                (application as MasterApplication).createRetrofit()
                activity.startActivity(
                    Intent(activity, OutStagramPostListActivity::class.java)
                )
            }

            })
    }

    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.commit()
    }


    fun initView(activity: Activity) {
        usernameView = activity.findViewById(R.id.username_inputbox)
        userPassword1View = activity.findViewById(R.id.password1_inputbox)
        userPassword2View = activity.findViewById(R.id.password2_inputbox)
        registerButton = activity.findViewById(R.id.register)
        loginButton = activity.findViewById(R.id.login)
    }

    fun getUserName() : String {
        return usernameView.text.toString()
    }

    fun getUserPassword1() : String {
        return userPassword1View.text.toString()
    }

    fun getUserPassword2() : String {
        return userPassword2View.text.toString()
    }

}
