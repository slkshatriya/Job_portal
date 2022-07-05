package com.technocrats.jobportal;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import androidx.appcompat.app.AppCompatActivity;

public class postJob3 extends AppCompatActivity {
    Button btnbrowse, btnupload;
    EditText txtdata;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    private static final String TAG = "uploadimg1";
    Intent isUserSelected;
    EditText txtData1;
    EditText txtData2;
    ArrayList<Uri> multipleFileUploads = new ArrayList<>();
    private List<String> imageList;
    private Uri fileUri;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job3);

        storageReference = FirebaseStorage.getInstance().getReference("Teach");
        databaseReference = FirebaseDatabase.getInstance().getReference("Teach");
        btnbrowse = (Button) findViewById(R.id.btnbrowse);
        btnupload = (Button) findViewById(R.id.btnupload);
        txtdata = (EditText) findViewById(R.id.txtdata);
        imgview = (ImageView) findViewById(R.id.image_view);
        txtData1 = findViewById(R.id.txtdata1);
        txtData2 = findViewById(R.id.txtdata2);
        imageList = new ArrayList<>();
        progressDialog = new ProgressDialog(postJob3.this);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                UploadImage();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK ) {
            isUserSelected = data;
            fileUri = data.getData();
            Log.d(TAG, "onActivityResult: "+ fileUri);
            Glide.with(this).load(fileUri).into(imgview);
            fileName = UUID.randomUUID().toString();
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImage() {
        progressDialog.setTitle("Image is uploading...");
        progressDialog.show();
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference("/jobs/"+fileName);
        firebaseStorage.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "onActivityResult: success" );
            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                Log.d(TAG, "onActivityResult: image link " + uri.toString());
                uploadinfo imageUploadInfo = new uploadinfo(txtdata.getText().toString(), txtData1.getText().toString(), txtData2.getText().toString(), uri.toString());
                String ImageUploadId = databaseReference.push().getKey();
                databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                sendMail();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_LONG).show();

            });
        });
    }

    private void sendMail() {

        try {
            String stringSenderEmail = "technocrats.developer@gmail.com";
            String stringReceiverEmail = txtData2.getText().toString();
            String stringPasswordSenderEmail = "fltlijamsdsdjniz";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });
            Log.i("msg", "success");

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Job Posting Update");
            mimeMessage.setText("Hello There, \n\nYour job post are live in the Teaching find job section. \n\n Cheers!\nTeam Technocrats");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}