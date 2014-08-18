package com.rogers.codersanswer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rogers.codersanswer.Models.Problem;
import com.rogers.codersanswer.Tools.ProblemListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogers on 3/5/14.
 */
public class StarredFragment extends Fragment {
    private ArrayList<Problem> mProblemList;
    private List<String> starredList;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private StarredFileHandler mStarredFileHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStarredFileHandler = StarredFileHandler.getInstance(getActivity());
        starredList = mStarredFileHandler.getStarredList();
        mProblemList = ((MainActivity)getActivity()).getProblemList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.starred_list, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mListView = (ListView)view.findViewById(R.id.starred_list_view);
        mListView.setTextFilterEnabled(true);
        ArrayList<String> problemNames = new ArrayList<String>();
        ArrayList<Integer> problemIcons = new ArrayList<Integer>();
        ArrayList<String> tags = new ArrayList<String>();
        for(String name : starredList) { //should put starredlist out of problemList to keep the order
            for(Problem p : mProblemList) {
                if(name.equals(p.mProblemName)) {
                    problemNames.add(p.mProblemName);
                    problemIcons.add(p.mIconSource);
                    tags.add(p.mTags);
                    break;
                }
            }
        }

        mArrayAdapter = new ProblemListAdapter(getActivity().getApplicationContext(), R.layout.problem_list_item, problemNames,problemIcons,tags);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                StarredItemPager item = new StarredItemPager();
                Bundle args = new Bundle();
                args.putInt("problemIndex", position);
                item.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, item);
                transaction.commit();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        //define search
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) {
                    mArrayAdapter.getFilter().filter("");
                } else {
                    mArrayAdapter.getFilter().filter(s);
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
