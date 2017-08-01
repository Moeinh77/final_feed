package com.hasani.moein.feedme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Details_activity extends AppCompatActivity {

    private TextView title,description,link;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activity);

        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.description);
        link=(TextView)findViewById(R.id.link);
        imageView=(ImageView)findViewById(R.id.imageView);

        Item item =(Item)getIntent().getSerializableExtra("My object");

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        link.setText(item.getURL_ADDRESS());
        ImageLoadTask imageLoadTask=new ImageLoadTask(item.getImageURL());
        if(imageView!=null){

            imageView.setImageBitmap(imageLoadTask.getBitmap());

        }

    }


}
