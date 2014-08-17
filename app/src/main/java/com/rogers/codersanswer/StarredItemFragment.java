package com.rogers.codersanswer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rogers on 3/5/14.
 */
public class StarredItemFragment extends Fragment{
    private ExtendedWebView mExtendedWebView;
    private String mProblemName;

    public StarredItemFragment(String problem_name) {
        mProblemName = problem_name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.starred_code_page, container, false);
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mExtendedWebView = (ExtendedWebView)view.findViewById(R.id.starred_codeView);
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
