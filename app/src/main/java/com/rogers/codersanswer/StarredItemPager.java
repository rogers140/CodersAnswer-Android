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

import java.util.List;

/**
 * Created by rogers on 3/5/14.
 */
public class StarredItemPager extends Fragment {
    private WebViewPager mPager;
    private String[] mStarredProblemNames;
    private PagerAdapter mPagerAdapter;
    private int mPosition;
    private ShareActionProvider mShareActionProvider;
    private boolean mStarred;
    private StarredFileHandler mStarredFileHandler = StarredFileHandler.getInstance(getActivity());

    public StarredItemPager() {

    }

    public int getCurrent() {
        return mPosition;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt("problemIndex");
        List<String> starredList = mStarredFileHandler.getStarredList();
        mStarredProblemNames = new String[starredList.size()];
        starredList.toArray(mStarredProblemNames);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.starred_code_pager, container, false);
        mPager = (WebViewPager)view.findViewById(R.id.starred_pager);
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
            return new StarredItemFragment(mStarredProblemNames[position]);
        }

        @Override
        public int getCount() {
            return mStarredProblemNames.length;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.starred_list_item_action, menu);
        MenuItem item = menu.findItem(R.id.starred_list_item_share);
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
        String problemName = mStarredProblemNames[problemIndex];
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_text1)
                + problemName + getString(R.string.share_text2) + getString(R.string.app_link));

        mShareActionProvider.setShareIntent(shareIntent);
        if(mStarredFileHandler == null) {
            mStarredFileHandler = StarredFileHandler.getInstance(getActivity());
        }
        mStarred = mStarredFileHandler.isStarred(mStarredProblemNames[problemIndex]);
        MenuItem starItem = menu.findItem(R.id.starred_list_item_star);
        if(mStarred) {
            starItem.setIcon(R.drawable.ic_action_important);
        }
        else {
            starItem.setIcon(R.drawable.ic_action_not_important);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.starred_list_item_star) {
            if(mStarred) {
                item.setIcon(R.drawable.ic_action_not_important);
                mStarred = false;
                mStarredFileHandler.deleteFromStarred(mStarredProblemNames[mPager.getCurrentItem()]);
            }
            else {
                item.setIcon(R.drawable.ic_action_important);
                mStarred = true;
                mStarredFileHandler.addToStarred(mStarredProblemNames[mPager.getCurrentItem()]);
            }
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
