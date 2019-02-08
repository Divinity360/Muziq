package com.naman14.timber.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.naman14.timber.R;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    Button register, fb_login, sign_in;
    Context mcontext;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_sign_up, container, false);
        instButtons(rootView);
        return rootView;
    }

    private void instButtons(View rootView) {
        register = rootView.findViewById(R.id.btn_register);
        fb_login = rootView.findViewById(R.id.btn_fb);
        sign_in = rootView.findViewById(R.id.btn_sign_in);

        register.setOnClickListener(this);
        fb_login.setOnClickListener(this);
        sign_in.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                BackgroundWorker.execute();
                break;
            case R.id.btn_fb:
                break;
            case R.id.btn_sign_in:
                moveToFragment(SignInFragment.class);
                break;
        }

    }

    private class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;
        BackgroundWorker(Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://allmuziq.com/muziqlogin.php";

            if (type.equals("signup")) {
                try{
                    String user_login = params[1];
                    String user_password = params[2];
                    String user_email = params[3];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("user_login", "UTF-8") + "=" + URLEncoder.encode(user_login, "UTF-8")
                            + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(user_password, "UTF-8")
                            + URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                }catch (Exception e){
                    Toast.makeText(context,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }

    }
}

