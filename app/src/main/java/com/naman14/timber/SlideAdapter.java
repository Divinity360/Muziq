package com.naman14.timber;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    public int[] slideImages = {
            R.drawable.search,
            R.drawable.mygroup,
            R.drawable.like,
            R.drawable.playlist,
            R.drawable.lyrics,
            R.drawable.down

    };

    public String[] slideText = {
            "Search for MUSIQ all over the world",
            "Listen to MUSIQ from over the world",
            "Like and comment to MUSIQ",
            "Playlist of different genres of MUSIQ",
            "View lyrics of different MUSIQ",
            "Download MUSIQ from over the world"
    };

    public String[] slideDescription = {
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb",
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb",
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb",
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb",
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb",
            "Lorem ipsum nbure beb ihiuuif lopui vrfeu ufuew\n" +
                    "bfdhbfdnb  e nb ejbjuwiuhiufw uvwjnhiwcv iuwc uw uwbhcw cwbcuewb"
    };

    @Override
    public int getCount() {
        return slideText.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == (RelativeLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.myicon);
        TextView heading = (TextView) view.findViewById(R.id.myHeading);
        TextView desc = (TextView) view.findViewById(R.id.myDesc);

        imageView.setImageResource(slideImages[position]);
        heading.setText(slideText[position]);
        desc.setText(slideDescription[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}