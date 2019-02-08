package com.naman14.timber.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.naman14.timber.R;

public class SignInFragment extends Fragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_sign_up, container, false);
        moveToFragment(SignInFragment.class);
        return rootView;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home_page, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_downloads:
                moveToFragment(DownloadFragment.class);
                return true;

            case R.id.menu_notification:
                moveToFragment(SignUpFragment.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToFragment(Class FragmentClass) {
        Intent intent = new Intent(getContext(), FragmentClass);
        getContext().startActivity(intent);
    }

}
