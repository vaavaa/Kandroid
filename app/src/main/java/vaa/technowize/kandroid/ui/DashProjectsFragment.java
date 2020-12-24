/*
 * Copyright 2017 Thomas Andres
 *
 * This file is part of Kandroid.
 *
 * Kandroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kandroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package vaa.technowize.kandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import kandroid.R;
import vaa.technowize.kandroid.Constants;
import vaa.technowize.kandroid.DashProjectsAdapter;
import vaa.technowize.kandroid.kanboard.KanboardDashboard;
import vaa.technowize.kandroid.kanboard.KanboardTask;

public class DashProjectsFragment extends Fragment {

    public DashProjectsFragment() {}

    public static DashProjectsFragment newInstance() {
//    public static DashProjectsFragment newInstance(List<KanboardProject> values) {
        return new DashProjectsFragment();
        // FIXME: Fragment does not accept arguments
//        DashProjectsFragment fragment = new DashProjectsFragment();
//        fragment.setProjects(values);
//        Bundle args = new Bundle();
//        args.putSerializable(DashProjectsFragment.ARG_VALUES, (Serializable) values);
//        fragment.setArguments(args);
//        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d("ProjectsFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_expandable_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (((MainActivity)getActivity()).getDashboard() != null) {
            assert getView() != null : "DashProjectsFragment: getView() returned null";
            getView().findViewById(R.id.fragment_dash_errortext).setVisibility(View.GONE);
            getView().findViewById(R.id.expandable_list).setVisibility(View.VISIBLE);
            DashProjectsAdapter listAdapter = new DashProjectsAdapter(getContext(), ((MainActivity)getActivity()).getDashboard(), ((MainActivity)getActivity()).getColors());
            ((ExpandableListView) getView().findViewById(R.id.expandable_list)).setAdapter(listAdapter);
            ((ExpandableListView) getView().findViewById(R.id.expandable_list)).setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.i(Constants.TAG, "Launching TaskDetailActivity from DashProjectsFragment.");
                    KanboardDashboard dashboard = ((MainActivity)getActivity()).getDashboard();
                    KanboardTask clickedTask = dashboard.getGroupedTasks().get(dashboard.getProjects().get(groupPosition).getId()).get(childPosition);
                    Intent taskIntent = new Intent(getContext(), TaskDetailActivity.class);
                    taskIntent.putExtra("task", clickedTask);
                    taskIntent.putExtra("me", ((MainActivity)getActivity()).getMe());
                    startActivityForResult(taskIntent, Constants.RequestEditTask);
                    return true;
                }
            });
        } else {
            assert getView() != null : "DashProjectsFragment: getView() returned null";
            getView().findViewById(R.id.fragment_dash_errortext).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.expandable_list).setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestEditTask && resultCode == Constants.ResultChanged)
            ((MainActivity) getActivity()).refresh();
    }
}
