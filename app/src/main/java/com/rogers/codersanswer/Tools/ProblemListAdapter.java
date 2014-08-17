package com.rogers.codersanswer.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rogers.codersanswer.Models.Problem;
import com.rogers.codersanswer.R;

import java.util.ArrayList;
import java.util.logging.Filter;

/**
 * Created by rogers on 3/16/14.
 */
public class ProblemListAdapter extends ArrayAdapter<String> implements Filterable{
    private ArrayList<String> mProblemNames;
    private ArrayList<Integer> mProblemIcons;
    private ArrayList<String> mTags;
    private int mResource;
    private LayoutInflater mInflater;

    public ProblemListAdapter(Context context, int layout, ArrayList<String> problemNames, ArrayList<Integer> problemIcons, ArrayList<String> tags) {
        super(context, layout, problemNames);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = layout;
        mProblemNames = problemNames;
        mProblemIcons = problemIcons;
        mTags = tags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mResource, null);
        ImageView icon = (ImageView)convertView.findViewById(R.id.problem_icon);
        icon.setImageResource(mProblemIcons.get(position));
        TextView textView = (TextView)convertView.findViewById(R.id.problem_list_item_textView);
        textView.setText(mProblemNames.get(position));
        TextView tagView = (TextView)convertView.findViewById(R.id.problem_list_item_tags);
        tagView.setText(mTags.get(position));

        return convertView;
    }
}
