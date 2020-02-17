/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.naman14.timber.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.naman14.timber.R;
import com.naman14.timber.fragments.GistDetailFragment;
import com.naman14.timber.fragments.GistFragment;
import com.naman14.timber.lastfmapi.LastFmClient;
import com.naman14.timber.lastfmapi.callbacks.ArtistInfoListener;
import com.naman14.timber.lastfmapi.models.ArtistQuery;
import com.naman14.timber.lastfmapi.models.LastfmArtist;
import com.naman14.timber.models.Artist;
import com.naman14.timber.models.Gist;
import com.naman14.timber.utils.Helpers;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.utils.PreferencesUtility;
import com.naman14.timber.utils.TimberUtils;
import com.naman14.timber.widgets.BubbleTextGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class GistAdapter extends RecyclerView.Adapter<GistAdapter.ItemHolder> {

    private List<Gist> gistlist;
    private Activity mContext;
    private boolean isGrid;

    public GistAdapter(Activity context, List<Gist> gistlist) {
        this.gistlist = gistlist;
        this.mContext = context;
        this.isGrid = PreferencesUtility.getInstance(mContext).isArtistsInGrid();
    }

    public static int getOpaqueColor(@ColorInt int paramInt) {
        return 0xFF000000 | paramInt;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gist, null);
            ItemHolder ml = new ItemHolder(v);
            return ml;
    }

    @Override
    public void onBindViewHolder(final ItemHolder itemHolder, int i) {
        Gist localGist = gistlist.get(i);
        if(localGist.imagepath.isEmpty()){
            Picasso.with(mContext).load(R.drawable.ic_empty_music2).into(itemHolder.thumbnail);
        }else{
            Picasso.with(mContext).load(localGist.imagepath).into(itemHolder.thumbnail);
        }
        itemHolder.title.setText(localGist.title);
        itemHolder.desc.setText(localGist.desc);
        itemHolder.date.setText(localGist.date);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return gistlist.size();
    }

    public Gist getItem(int position) {
        return gistlist.get(position);
    }

    public void updateDataSet(List<Gist> arrayList) {
        this.gistlist = arrayList;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView date, title,desc;
        protected ImageView thumbnail;
        protected View footer;
        protected RelativeLayout relativeLayout;

        public ItemHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.gistTitle);
            this.date = (TextView) view.findViewById(R.id.gistDate);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.desc = view.findViewById(R.id.gistDesc);
            this.relativeLayout = (RelativeLayout)view.findViewById(R.id.gistLayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Gist gist = gistlist.get(getAdapterPosition());
            Intent intent = new Intent(mContext, GistDetailFragment.class);
            intent.putExtra("news_item", gist);
            mContext.startActivity(intent);

        }

    }
}




