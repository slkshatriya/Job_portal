package com.technocrats.jobportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_description.*

class FullDescriptionActivity : AppCompatActivity() {
    private lateinit var imageUrl: String
    private lateinit var jobTitle: String
    private lateinit var jobDescription: String
    private lateinit var jobEmail: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_description)
        getIntentFromActivity()
        setJobProperties()
        job_btn.setOnClickListener({
            job_btn.setText("Applied")
            job_btn.setBackgroundColor(R.drawable.btn_color)
        }
        )
    }

    private fun setJobProperties() {
        job_title.setText(jobTitle)
        job_email.setText(jobEmail)
        job_description.setText(jobDescription)
        Glide.with(this).load(imageUrl).into(job_image)
    }

    private fun getIntentFromActivity() {
        intent?.apply {
            imageUrl = getStringExtra(JOB_PHOTO)
            jobTitle = getStringExtra(JOB_TITLE)
            jobDescription = getStringExtra(JOB_DESCRIPTION)
            jobEmail = getStringExtra(JOB_EMAIL)
        }
    }

}