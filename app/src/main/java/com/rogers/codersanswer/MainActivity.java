package com.rogers.codersanswer;

import android.app.Fragment;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;

import com.rogers.codersanswer.Models.Problem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by rogers on 2/13/14.
 * Main entrance of the application.
 */
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private ArrayList<Problem> problemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getString(R.string.app_name);

        //Set up starred file
        StarredFileHandler starredFileHandler = StarredFileHandler.getInstance(this);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        initProblemList();


    }

    private void initProblemList() {
        problemList = new ArrayList<Problem>();
        //1-10
        problemList.add(new Problem("Two Sum", R.drawable.level_2, "array, sort, hash"));
        problemList.add(new Problem("Median of Two Sorted Arrays",R.drawable.level_5, "array, binary search"));
        problemList.add(new Problem("Longest Substring Without Repeating Characters",R.drawable.level_3, "hash"));
        problemList.add(new Problem("Add Two Numbers",R.drawable.level_3, "linked list"));
        problemList.add(new Problem("Longest Palindromic Substring",R.drawable.level_4, "string"));
        problemList.add(new Problem("ZigZag Conversion",R.drawable.level_3, "string"));
        problemList.add(new Problem("Reverse Integer",R.drawable.level_2, "math"));
        problemList.add(new Problem("String to Integer",R.drawable.level_2, "string, math"));
        problemList.add(new Problem("Palindrome Number",R.drawable.level_2, "math"));
        problemList.add(new Problem("Regular Expression Matching",R.drawable.level_1, "string, recursion"));
        //11-20
        problemList.add(new Problem("Container With Most Water",R.drawable.level_3, "array"));
        problemList.add(new Problem("Integer to Roman",R.drawable.level_3, "math"));
        problemList.add(new Problem("Roman to Integer",R.drawable.level_2, "math"));
        problemList.add(new Problem("Longest Common Prefix",R.drawable.level_2, "string"));
        problemList.add(new Problem("3Sum",R.drawable.level_3, "array"));
        problemList.add(new Problem("3Sum Closest",R.drawable.level_3, "array"));
        problemList.add(new Problem("4Sum",R.drawable.level_3, "array"));
        problemList.add(new Problem("Letter Combinations of a Phone Number",R.drawable.level_3, "DFS"));
        problemList.add(new Problem("Remove Nth Node From End of List",R.drawable.level_2, "linked list"));
        problemList.add(new Problem("Longest Common Prefix",R.drawable.level_1, "string"));
        //21-30
        problemList.add(new Problem("Valid Parentheses",R.drawable.level_2, "stack"));
        problemList.add(new Problem("Generate Parentheses",R.drawable.level_4, "string, DFS"));
        problemList.add(new Problem("Merge k Sorted Lists",R.drawable.level_3, "linked list, heap, merge"));
        problemList.add(new Problem("Swap Nodes in Pairs",R.drawable.level_2, "linked list"));
        problemList.add(new Problem("Reverse Nodes in k-Group",R.drawable.level_4, "linked list"));
        problemList.add(new Problem("Remove Duplicates from Sorted Array",R.drawable.level_1, "array"));
        problemList.add(new Problem("Remove Element",R.drawable.level_1, "array"));
        problemList.add(new Problem("Implement strStr()",R.drawable.level_4, "string, KMP, hash"));
        problemList.add(new Problem("Divide Two Integers",R.drawable.level_4, "binary search"));
        problemList.add(new Problem("Substring with Concatenation of All Words",R.drawable.level_3, "string"));
        //31-40
        problemList.add(new Problem("Next Permutation",R.drawable.level_5, "array"));
        problemList.add(new Problem("Longest Valid Parentheses", R.drawable.level_4, "string, DP"));
        problemList.add(new Problem("Search in Rotated Sorted Array", R.drawable.level_4, "binary search"));
        problemList.add(new Problem("Search for a Range", R.drawable.level_4, "binary search"));
        problemList.add(new Problem("Search Insert Position", R.drawable.level_2, "array"));
        problemList.add(new Problem("Valid Sudoku", R.drawable.level_2, "array"));
        problemList.add(new Problem("Sudoku Solver", R.drawable.level_4, "recursive, DFS"));
        problemList.add(new Problem("Count and Say", R.drawable.level_2, "two pointer"));
        problemList.add(new Problem("Combination Sum", R.drawable.level_3, "combination"));
        problemList.add(new Problem("Combination Sum II", R.drawable.level_4, "combination"));
        //41-50
        problemList.add(new Problem("First Missing Positive",R.drawable.level_5, "math"));
        problemList.add(new Problem("Trapping Rain Water", R.drawable.level_5, ""));
        problemList.add(new Problem("Multiply Strings", R.drawable.level_4, "math"));
        problemList.add(new Problem("Wildcard Matching", R.drawable.level_5, "DP"));
        problemList.add(new Problem("Jump Game II", R.drawable.level_4, "array"));
        problemList.add(new Problem("Permutations", R.drawable.level_3, "DFS"));
        problemList.add(new Problem("Permutations II", R.drawable.level_4, "DFS"));
        problemList.add(new Problem("Rotate Image", R.drawable.level_4, "math, reverse"));
        problemList.add(new Problem("Anagrams", R.drawable.level_3, "hash"));
        problemList.add(new Problem("Pow(x, n)", R.drawable.level_3, "binary"));
        //51-60
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        onSectionAttached(position);
        switch (position) {
            case 0:
                ProblemListFragment plf = new ProblemListFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, plf, "problem_list").commit();
                break;
            case 1:
                PickOneFragment pof = new PickOneFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, pof, "pick_one").commit();
                break;
            case 2:
                StarredFragment sf = new StarredFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, sf, "starred").commit();
                break;
            case 3:
                AboutFragment af = new AboutFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, af, "about").commit();
                break;
            case 4:
                FeedbackFragment ff = new FeedbackFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, ff, "feedback").commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        invalidateOptionsMenu();//rebuild the actionbar
        switch (number) {
            case 0:
                //mTitle = getString(R.string.all);
                mTitle = getString(R.string.app_name);
                break;
            case 1:
                mTitle = getString(R.string.random);
                break;
            case 2:
                mTitle = getString(R.string.star);
                break;
            case 3:
                mTitle = getString(R.string.about);
                break;
            case 4:
                mTitle = getString(R.string.feedback);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        //handle the press of "Back" button.
        Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof ProblemItemPager) {
            ProblemListFragment plf = new ProblemListFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, plf).commit();
        }
        else if(fragment instanceof StarredItemPager) {
            StarredFragment sf = new StarredFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, sf).commit();
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        //handle the orientation change
        super.onConfigurationChanged(newConfiguration);
    }

    public ArrayList<Problem> getProblemList() {
        if (this.problemList == null) {
            initProblemList();
        }
        return this.problemList;
    }
}
