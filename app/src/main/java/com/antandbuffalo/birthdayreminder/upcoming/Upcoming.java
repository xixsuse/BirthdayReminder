package com.antandbuffalo.birthdayreminder.upcoming;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.antandbuffalo.birthdayreminder.Constants;
import com.antandbuffalo.birthdayreminder.DataHolder;
import com.antandbuffalo.birthdayreminder.DateOfBirth;
import com.antandbuffalo.birthdayreminder.MainActivity;
import com.antandbuffalo.birthdayreminder.R;
import com.antandbuffalo.birthdayreminder.TabsAdapter;
import com.antandbuffalo.birthdayreminder.addnew.AddNew;
import com.antandbuffalo.birthdayreminder.database.DateOfBirthDBHelper;
import com.antandbuffalo.birthdayreminder.fragments.MyFragment;
import com.antandbuffalo.birthdayreminder.today.TodayListAdapter;
import com.antandbuffalo.birthdayreminder.update.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by i677567 on 28/8/15.
 */
public class Upcoming extends MyFragment {
    UpcomingListAdapter upcomingListAdapter;
    UpcomingViewModel upcomingViewModel;
    public static Upcoming newInstance() {
        Upcoming fragment = new Upcoming();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        upcomingViewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);

        View rootView = inflater.inflate(R.layout.upcoming, container, false);

        //ArrayAdapter<String> codeLearnArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, codeLearnChapters);
        upcomingListAdapter = new UpcomingListAdapter();
        //http://stackoverflow.com/questions/6495898/findviewbyid-in-fragment
        ListView upcomingListView = (ListView)rootView.findViewById(R.id.upcomingListView);
        upcomingListView.setAdapter(upcomingListAdapter);

        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateOfBirth dateOfBirth = upcomingListAdapter.getItem(position);

                Intent intent = new Intent(view.getContext(), Update.class);
                intent.putExtra("currentDOB", dateOfBirth);
                getActivity().startActivityForResult(intent, Constants.DELETE_MEMBER);
            }
        });

        EditText filter = (EditText)rootView.findViewById(R.id.filter1);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                upcomingViewModel.setFilter(cs.toString());
                upcomingListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
        return rootView;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.ADD_NEW_MEMBER) {
//            if (resultCode == RESULT_OK) {
//                if (data.getStringExtra(Constants.IS_USER_ADDED).equalsIgnoreCase(Constants.FLAG_SUCCESS)) {
//                    int index = mViewPager.getCurrentItem();
//                    TabsAdapter adapter = (TabsAdapter) mViewPager.getAdapter();
//                    MyFragment fragment = (MyFragment) adapter.getFragment(index);
//                    fragment.refreshData();
//                    //mTabsAdapter.notifyDataSetChanged();
//                    for (int i = 0; i < DataHolder.getInstance().refreshTracker.size(); i++) {
//                        if (i != index) {
//                            DataHolder.getInstance().refreshTracker.set(i, true);
//                        }
//                    }
//                }
//            }
//        }
//    }
    @Override
    public void refreshData() {
        upcomingListAdapter.refreshData();
        if(!upcomingViewModel.getFilter().equalsIgnoreCase("")) {
            upcomingListAdapter.getFilter().filter(upcomingViewModel.getFilter());
        }
    }
}
