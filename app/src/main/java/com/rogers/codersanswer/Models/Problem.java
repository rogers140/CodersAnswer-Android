package com.rogers.codersanswer.Models;

/**
 * Created by rogers on 3/16/14.
 */
public class Problem {
    public String mProblemName;
    public int mIconSource;
    public String mTags;
    public Problem(String problemName, int iconSource, String tags) {
        mProblemName = problemName;
        mIconSource = iconSource;
        mTags = tags;

    }
}
