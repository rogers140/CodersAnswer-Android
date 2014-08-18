package com.rogers.codersanswer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rogers on 2/14/14.
 * Show individual problem page.
 */
public class ProblemItemFragment extends Fragment {

    private ExtendedWebView mExtendedWebView;
    private String mProblemName;
    //private StarredFileHandler mStarredFileHandler = StarredFileHandler.getInstance(getActivity());

    public ProblemItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_page, container, false);
        mProblemName = getArguments().getString("problemName");
        //setHasOptionsMenu(true); //不需要这个
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mExtendedWebView = (ExtendedWebView)view.findViewById(R.id.codeView);
        mExtendedWebView.getSettings().setJavaScriptEnabled(true);
        String content = "";
        try {
            InputStream is = getActivity().getApplicationContext().getResources().getAssets()
                    .open("code/" + mProblemName + ".html");
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = is.read(buffer, 0, buffer.length)) > 0) {
                bs.write(buffer, 0, i);
            }
            content = new String(bs.toString());
        } catch (IOException e) {}
        mExtendedWebView.loadDataWithBaseURL("file:///android_asset/code/",
                content, "text/html", "utf-8", null);
    }
}
