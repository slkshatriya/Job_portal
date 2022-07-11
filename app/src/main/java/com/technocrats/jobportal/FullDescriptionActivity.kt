package com.technocrats.jobportal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_description.*
import javax.mail.*
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

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
        job_btn.setOnClickListener {
            job_btn.setText("Applied")
            job_btn.setBackgroundColor(R.drawable.btn_color)
            sendMail()
        }
    }

    private fun sendMail() {
        try {
            val stringSenderEmail = "technocrats.developer@gmail.com"
            val stringReceiverEmail: String = jobEmail
            val stringPasswordSenderEmail = "fltlijamsdsdjniz"
            val stringHost = "smtp.gmail.com"
            val properties = System.getProperties()
            properties["mail.smtp.host"] = stringHost
            properties["mail.smtp.port"] = "465"
            properties["mail.smtp.ssl.enable"] = "true"
            properties["mail.smtp.auth"] = "true"
            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail)
                }
            })
            Log.i("msg", "success")
            val mimeMessage = MimeMessage(session)
            mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(stringReceiverEmail))
            mimeMessage.subject = "Job Update"
            mimeMessage.setText(emailtext.text.toString()+ " have applied to your posted job. \n\n Cheers!\nTeam Technocrats")
            val thread = Thread {
                try {
                    Transport.send(mimeMessage)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }
            thread.start()
        } catch (e: AddressException) {
            e.printStackTrace()
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
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