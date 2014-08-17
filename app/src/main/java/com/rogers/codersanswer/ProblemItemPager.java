package com.rogers.codersanswer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rogers.codersanswer.Models.Problem;

import java.util.ArrayList;

/**
 * Created by rogers on 2/24/14.
 * Pager of problem item that enables slide change between problems.
 */
public class ProblemItemPager extends Fragment {
    private WebViewPager mPager;
    private ArrayList<Problem> mProblemList;
    private PagerAdapter mPagerAdapter;
    private int mPosition;
    private ShareActionProvider mShareActionProvider;
    private boolean mStarred;
    private StarredFileHandler mStarredFileHandler = StarredFileHandler.getInstance(getActivity());;
    public ProblemItemPager(){

    }


    public int getCurrent() {
        return mPosition;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProblemList = ((MainActivity)getActivity()).getProblemList();
        mPosition = getArguments().getInt("problemIndex");
        mStarredFileHandler = StarredFileHandler.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_pager, container, false);
        mPager = (WebViewPager)view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());//define slide animation
        mPager.setCurrentItem(getCurrent());
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {});
        setHasOptionsMenu(true);//call onCreateOptionMenu
        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ProblemItemFragment(mProblemList.get(position).mProblemName);
        }

        @Override
        public int getCount() {
            return mProblemList.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.problem_list_item_action, menu);
        MenuItem item = menu.findItem(R.id.problem_list_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        //define share message
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        int problemIndex = 0;
        if(mPager == null) {
            problemIndex = mPosition;
        }
        else {
            problemIndex = mPager.getCurrentItem();
        }
        String problemName = mProblemList.get(problemIndex).mProblemName;
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_text1)
                + problemName + getString(R.string.share_text2) + getString(R.string.app_link));

        mShareActionProvider.setShareIntent(shareIntent);
        mStarred = mStarredFileHandler.isStarred(mProblemList.get(problemIndex).mProblemName);
        MenuItem starItem = menu.findItem(R.id.problem_list_item_star);

        if(mStarred) {

            starItem.setIcon(R.drawable.ic_action_important);
        }
        else {
            starItem.setIcon(R.drawable.ic_action_not_important);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.problem_list_item_star) {
            if(mStarred) {
                item.setIcon(R.drawable.ic_action_not_important);
                mStarred = false;
                mStarredFileHandler.deleteFromStarred(mProblemList.get(mPager.getCurrentItem()).mProblemName);
            }
            else {
                item.setIcon(R.drawable.ic_action_important);
                mStarred = true;
                mStarredFileHandler.addToStarred(mProblemList.get(mPager.getCurrentItem()).mProblemName);
            }
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
