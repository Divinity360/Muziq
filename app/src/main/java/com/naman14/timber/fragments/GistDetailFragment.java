package com.naman14.timber.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman14.timber.R;
import com.naman14.timber.activities.BaseThemedActivity;
import com.naman14.timber.models.Gist;
import com.squareup.picasso.Picasso;

public class GistDetailFragment extends BaseThemedActivity {

    ImageView gistImage;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gist_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gistImage = (ImageView) findViewById(R.id.gistImage);
        tvTitle = (TextView) findViewById(R.id.textview_1);
        tvDescription = (TextView) findViewById(R.id.textview_2);


        final Gist gist = (Gist) getIntent().getSerializableExtra("news_item");
        if (gist.imagepath.isEmpty()) {
            Picasso.with(this).load(R.drawable.ic_empty_music2).placeholder(R.drawable.ic_empty_music2).into(gistImage);
            }else{
            Picasso.with(this).load(gist.imagepath).placeholder(R.drawable.ic_empty_music2).into(gistImage);
        }

        tvTitle.setText(gist.title);
        tvDescription.setText(gist.desc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(" ");

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(gist.title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(gist.title);
                    isShow = false;
                }
            }
        });

    }
}
