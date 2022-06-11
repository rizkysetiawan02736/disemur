package com.rizky_02736.desemar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JobActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST1 = 102;
    private static final int ALL_FILE_REQUEST2 = 103;
    private static final int ALL_FILE_REQUEST3 = 104;
    Button select_all_file1;
    Button select_all_file2;
    Button select_all_file3;



    TextView all_file_name1;
    TextView all_file_name2;
    TextView all_file_name3;

    EditText et_email;
    EditText et_address;
    EditText et_position;


    Button submit;
    ProgressBar progressBar;
    int method = 0;
    String all_file_path1, all_file_path2, all_file_path3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        EditText etjob1= findViewById(R.id.etjob1);

        Bundle a =getIntent().getExtras();
        String b = (String) a.get("send_id");

        etjob1.setText(b);

        et_email = (EditText) findViewById(R.id.etjob1);
        et_address = (EditText) findViewById(R.id.etjob2);
        et_position = (EditText) findViewById(R.id.etjob3);

        select_all_file1 = findViewById(R.id.select_from_all_files1);
        select_all_file2 = findViewById(R.id.select_from_all_files2);
        select_all_file3 = findViewById(R.id.select_from_all_files3);

        all_file_name1 = findViewById(R.id.all_file_name1);
        all_file_name2 = findViewById(R.id.all_file_name2);
        all_file_name3 = findViewById(R.id.all_file_name3);

        submit = findViewById(R.id.upload);
        progressBar = findViewById(R.id.progressbar);

        select_all_file1.setOnClickListener(JobActivity.this);
        select_all_file2.setOnClickListener(JobActivity.this);
        select_all_file3.setOnClickListener(JobActivity.this);
        submit.setOnClickListener(JobActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all_file_path1 == null) {
                    Toast.makeText(JobActivity.this, "ALl File File Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (all_file_path2 == null) {
                    Toast.makeText(JobActivity.this, "ALl File File Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (all_file_path3 == null) {
                    Toast.makeText(JobActivity.this, "ALl File File Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                UploadTask uploadTask = new UploadTask();
                uploadTask.execute(new String[]{all_file_path1, all_file_path2, all_file_path3});
            }
        });

}

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.select_from_all_files1) {
            method = 0;
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    filePicker(0);
                } else {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            } else {
                filePicker(0);
            }

        } else if (v.getId() == R.id.select_from_all_files2) {
            method = 1;
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    filePicker(1);
                } else {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            } else {
                filePicker(1);
            }

        } else if (v.getId() == R.id.select_from_all_files3) {
            method = 2;
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    filePicker(2);
                } else {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            } else {
                filePicker(2);
            }
        }





    }

    private void filePicker(int i) {
        if (i == 0) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), ALL_FILE_REQUEST1);
        }

        if (i == 1) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), ALL_FILE_REQUEST2);
        }
        if (i == 2) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Choose File to Upload"), ALL_FILE_REQUEST3);
        }
    }

    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(JobActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(JobActivity.this, permission)) {
            Toast.makeText(JobActivity.this, "Please Allow Permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(JobActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(JobActivity.this, "Permission Successfull", Toast.LENGTH_SHORT).show();
                    filePicker(method);
                } else {
                    Toast.makeText(JobActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ALL_FILE_REQUEST1) {
                if (data == null) {
                    return;
                }

                Uri uri = data.getData();
                String paths = FilePath.getFilePath(JobActivity.this, uri);
                Log.d("File Path : ", "" + paths);
                if (paths != null) {
                    all_file_name1.setText("" + new File(paths).getName());
                }
                all_file_path1 = paths;
            }if (requestCode == ALL_FILE_REQUEST2) {
                if (data == null) {
                    return;
                }

                Uri uri = data.getData();
                String paths = FilePath.getFilePath(JobActivity.this, uri);
                Log.d("File Path : ", "" + paths);
                if (paths != null) {
                    all_file_name2.setText("" + new File(paths).getName());
                }
                all_file_path2 = paths;
            }if (requestCode == ALL_FILE_REQUEST3) {
                if (data == null) {
                    return;
                }

                Uri uri = data.getData();
                String paths = FilePath.getFilePath(JobActivity.this, uri);
                Log.d("File Path : ", "" + paths);
                if (paths != null) {
                    all_file_name3.setText("" + new File(paths).getName());
                }
                all_file_path3 = paths;
            }
        }
    }

    //    Now Lets Upload it First Create a Server Code for hadnling file

    //Now uploading it

    public class UploadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                Toast.makeText(JobActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(JobActivity.this, "File Upload Failed", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {

            File file1 = new File(strings[0]);
            File file2 = new File(strings[1]);
            File file3 = new File(strings[2]);

            String email = et_email.getText().toString().trim();
            String address = et_address.getText().toString().trim();
            String position = et_position.getText().toString().trim();

            try {
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("files1", file1.getName(), RequestBody.create(MediaType.parse("*/*"), file1))
                        .addFormDataPart("files2", file2.getName(), RequestBody.create(MediaType.parse("*/*"), file2))
                        .addFormDataPart("files3", file3.getName(), RequestBody.create(MediaType.parse("*/*"), file3))
                        .addFormDataPart("email",email)
                        .addFormDataPart("address",address)
                        .addFormDataPart("position",position)
                        .addFormDataPart("some_key", "some_value")
                        .addFormDataPart("submit", "submit")
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.43.14/Desemar/android/upload2.php")
                        .post(requestBody)
                        .build();

                OkHttpClient okHttpClient = new OkHttpClient();
                //now progressbar not showing properly let's fixed it
                Response response = okHttpClient.newCall(request).execute();
                if (response != null && response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
