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
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class postJob5 extends AppCompatActivity {

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
        setContentView(R.layout.activity_post_job5);

        storageReference = FirebaseStorage.getInstance().getReference("Eng");
        databaseReference = FirebaseDatabase.getInstance().getReference("Eng");
        btnbrowse = (Button) findViewById(R.id.btnbrowse);
        btnupload = (Button) findViewById(R.id.btnupload);
        txtdata = (EditText) findViewById(R.id.txtdata);
        imgview = (ImageView) findViewById(R.id.image_view);
        txtData1 = findViewById(R.id.txtdata1);
        txtData2 = findViewById(R.id.txtdata2);
        imageList = new ArrayList<>();
        progressDialog = new ProgressDialog(postJob5.this);

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
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_LONG).show();

            });
        });
    }
}