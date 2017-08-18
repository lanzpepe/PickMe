package com.elano.jessie.pickme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 1;
    private static final String IMAGE_SHARE = "key-image-share";
    private ImageView mIvImageHolder;
    private Button mBtnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews() {
        mIvImageHolder = (ImageView) findViewById(R.id.ivImageHolder);
        mBtnShare = (Button) findViewById(R.id.btnShare);
    }

    public void share(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, IMAGE_SHARE);
        startActivity(Intent.createChooser(intent, "Share this on"));
    }

    public void select(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image from"), REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY && data != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                mIvImageHolder.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mIvImageHolder.setOnClickListener(null);
            mBtnShare.setVisibility(View.VISIBLE);
        }
    }
}
