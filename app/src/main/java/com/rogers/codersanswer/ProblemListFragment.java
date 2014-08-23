package com.rogers.codersanswer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import com.rogers.codersanswer.Models.Problem;
import com.rogers.codersanswer.Tools.ProblemListAdapter;

import java.util.ArrayList;


/**
 * Created by rogers on 2/13/14.
 * Show "Problem List" page.
 */
public class ProblemListFragment extends Fragment {
    private ArrayList<Problem> mProblemList;
    private ListView mListView;
    ProblemListAdapter mArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProblemList = ((MainActivity)getActivity()).getProblemList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.problem_list, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mListView = (ListView)view.findViewById(R.id.problem_list_view);
        mListView.setTextFilterEnabled(true);

        mArrayAdapter = new ProblemListAdapter(getActivity().getApplicationContext(), R.layout.problem_list_item, mProblemList);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //fold virtual keyboard
                InputMethodManager im = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                ProblemItemPager item = new ProblemItemPager();
                Bundle args = new Bundle();
                Problem p = mArrayAdapter.getItem(position);
                String problemName = p.mProblemName;
                args.putString("problemName", problemName);
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
