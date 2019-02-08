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

package com.naman14.timber.fragments;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.naman14.timber.R;
import com.naman14.timber.adapters.GistAdapter;
import com.naman14.timber.dataloaders.ArtistLoader;
import com.naman14.timber.models.Artist;
import com.naman14.timber.models.Gist;
import com.naman14.timber.utils.PreferencesUtility;
import com.naman14.timber.utils.SortOrder;
import com.naman14.timber.widgets.BaseRecyclerView;
import com.naman14.timber.widgets.DividerItemDecoration;
import com.naman14.timber.widgets.FastScroller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GistFragment extends Fragment {

    private GistAdapter mAdapter;
    private BaseRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private PreferencesUtility mPreferences;
    List<Gist> myGistList;
    private boolean isGrid;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance(getActivity());
        isGrid = mPreferences.isArtistsInGrid();
//        myGistList = new ArrayList<>();
        Log.i("mytag", "text:  ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_recyclerview, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerview);
//        FastScroller fastScroller = rootView.findViewById(R.id.fastscroller);
//        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setEmptyView(getActivity(), rootView.findViewById(R.id.list_empty), "No media found");

        if (getActivity() != null)
            loadUrlData();

        return rootView;
    }

    private void setLayoutManager() {
        if (isGrid) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 1);
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemDecoration() {
        if (isGrid) {
            int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen.spacing_card_album_grid);
            itemDecoration = new SpacesItemDecoration(spacingInPixels);
        } else {
            itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        }
        recyclerView.addItemDecoration(itemDecoration);
    }

//    private void updateLayoutManager(int column) {
//        recyclerView.removeItemDecoration(itemDecoration);
//        recyclerView.setAdapter(new GistAdapter(getActivity(), myGistList));
//        layoutManager.setSpanCount(column);
//        layoutManager.requestLayout();
//        setItemDecoration();
//    }

    String removeCdata(String data) {
        data = data.replace("<![CDATA[", "");
        data = data.replace("]]>", "");
        return data;
    }

    String removeComment(String data) {
        data = data.replace("Comment on", "");
        return data;
    }

    private void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                List<Artist> artistList = ArtistLoader.getAllArtists(getActivity());
                mAdapter.updateDataSet(myGistList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }

    private void loadUrlData() {
        myGistList = new ArrayList<Gist>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        try {
//            Document comment = Jsoup.connect("http://www.naijaloaded.com.ng/comments/feed").get();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            Toast.makeText(getContext(), "Ellol" + ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        StringRequest request = new StringRequest("http://www.naijaloaded.com.ng/feed", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Document document = Jsoup.parse(response);
                    Elements itemElements = document.getElementsByTag("item");
                    for (int i = 0; i < itemElements.size(); i++) {
                        Element item = itemElements.get(i);
                        String title = item.child(0).text();
                        String pubDate = item.child(3).text();
                        String guid = item.child(6).text();
                        String desc = item.getElementsByTag("content:encoded").text();
                        Document doc2 = Jsoup.parse(desc);
                        String imagelink = doc2.getElementsByTag("img").attr("src");
                        String texts = item.getElementsByTag("content:encoded").select("p").text();
                        Elements elements = doc2.getElementsByTag("p");
                        int elementsSize = elements.size();
                        StringBuilder builder = new StringBuilder();
                        for (int e = 0; e < elementsSize; e++) {
                            builder.append(elements.eq(e).text());
                            builder.append("\n\n");
                        }
//                        Elements titles = comment.select("item");
//                        builder.append("Comments" + titles + " " + titles.eq(1));


                        String text = texts;

                        Gist gist = new Gist();
                        gist.title = title;
                        gist.desc = builder.toString();
                        gist.link = guid;
                        gist.imagepath = imagelink;
                        gist.date = pubDate;
                        myGistList.add(gist);

                        Log.e("mytag", "title: " + title);
                        Log.e("mytag", "pubdate: " + pubDate);
                        Log.e("mytag", "guid:  " + guid);
                        Log.e("mytag", "description: " + desc);
                        Log.e("mytag", "image:  " + imagelink);
//                        Log.i("mytag", "text:  " + text);

                    }
                    Log.i("mytag", "items found: " + itemElements.size());
                    Log.i("mytag", "items in news List: " + myGistList.size());
                    mAdapter = new GistAdapter(getActivity(), myGistList);
                    recyclerView.setAdapter(mAdapter);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error" + e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error" + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(getContext(), "Request sent, Please Wait!!", Toast.LENGTH_SHORT).show();
        requestQueue.add(request);
    }


    //
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.artist_sort_by, menu);
//        inflater.inflate(R.menu.menu_show_as, menu);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_sort_by_az:
//                mPreferences.setArtistSortOrder(SortOrder.ArtistSortOrder.ARTIST_A_Z);
//                reloadAdapter();
//                return true;
//            case R.id.menu_sort_by_za:
//                mPreferences.setArtistSortOrder(SortOrder.ArtistSortOrder.ARTIST_Z_A);
//                reloadAdapter();
//                return true;
//            case R.id.menu_sort_by_number_of_songs:
//                mPreferences.setArtistSortOrder(SortOrder.ArtistSortOrder.ARTIST_NUMBER_OF_SONGS);
//                reloadAdapter();
//                return true;
//            case R.id.menu_sort_by_number_of_albums:
//                mPreferences.setArtistSortOrder(SortOrder.ArtistSortOrder.ARTIST_NUMBER_OF_ALBUMS);
//                reloadAdapter();
//                return true;
//            case R.id.menu_show_as_list:
//                mPreferences.setArtistsInGrid(false);
//                isGrid = false;
//                updateLayoutManager(1);
//                return true;
//            case R.id.menu_show_as_grid:
//                mPreferences.setArtistsInGrid(true);
//                isGrid = true;
//                updateLayoutManager(2);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
    private class loadArtists extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (getActivity() != null)
                mAdapter = new GistAdapter(getActivity(), myGistList);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (mAdapter != null) {
                mAdapter.setHasStableIds(true);
                recyclerView.setAdapter(mAdapter);
            }
            if (getActivity() != null) {
                setItemDecoration();
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;

        }


    }

//    public static void main(String args[]){
//       new GistFragment().loadUrlData();
//    }
}
