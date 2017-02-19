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

package in.andres.kandroid;

import android.content.Context;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

import in.andres.kandroid.kanboard.KanboardDashboard;
import in.andres.kandroid.kanboard.KanboardProject;
import in.andres.kandroid.kanboard.KanboardSwimlane;
import in.andres.kandroid.kanboard.KanboardTask;

public class ProjectTaskAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private KanboardProject mProject;
    private Dictionary<Integer, List<KanboardTask>> mData;

    public ProjectTaskAdapter(Context context, KanboardDashboard values) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProject = null;
    }

    public ProjectTaskAdapter(Context context, KanboardProject values, Dictionary<Integer, List<KanboardTask>> data) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProject = values;
        mData = data;
    }

    @Override
    public int getGroupCount() {
        return mProject.getSwimlanes().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(mProject.getSwimlanes().get(groupPosition).getId()).size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mProject.getSwimlanes().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(mProject.getSwimlanes().get(groupPosition).getId()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        KanboardSwimlane swimlane = (KanboardSwimlane) getGroup(groupPosition);

        if (convertView == null)
            convertView = mInflater.inflate(R.layout.listitem_dash_project_header, parent, false);

        TextView projectName = (TextView) convertView.findViewById(R.id.project_name);
        TextView projectDescription = (TextView) convertView.findViewById(R.id.project_description);
        TextView projectColumns = (TextView) convertView.findViewById(R.id.project_columns);
        TextView projectNbTasks = (TextView) convertView.findViewById(R.id.project_nb_own_tasks);
        TextView sidebar = (TextView) convertView.findViewById(R.id.sidebar);

        projectName.setText(swimlane.getName());
        int taskCount = mData.get(swimlane.getId()).size();
        projectNbTasks.setText(mContext.getResources().getQuantityString(R.plurals.format_nb_tasks, taskCount, taskCount));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sidebar.getLayoutParams();
        lp.removeRule(RelativeLayout.ALIGN_BOTTOM);
        projectDescription.setText(swimlane.getDescription());
        lp.addRule(RelativeLayout.ALIGN_BOTTOM, projectDescription.getId());
        sidebar.setLayoutParams(lp);
        projectColumns.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (childPosition < getChildrenCount(groupPosition) - 1) {
            final KanboardTask child = (KanboardTask) getChild(groupPosition, childPosition);
            convertView = mInflater.inflate(R.layout.listitem_project_task, parent, false);

            ((TextView) convertView.findViewById(R.id.task_name)).setText(Utils.fromHtml(String.format(Locale.getDefault(), "<big><b>#%d</b></big><br />%s", child.getId(), child.getTitle())));

            if (mProject.getProjectUsers().get(child.getOwnerId()) != null)
                ((TextView) convertView.findViewById(R.id.task_owner)).setText(Utils.fromHtml(String.format(Locale.getDefault(), "<small>%s</small>", mProject.getProjectUsers().get(child.getOwnerId()))));
            else
                convertView.findViewById(R.id.task_owner).setVisibility(View.INVISIBLE);

            if (child.getCategoryId() > 0)
                ((TextView) convertView.findViewById(R.id.task_category)).setText(mProject.getCategoryHashtable().get(child.getCategoryId()).getName());
            else
                convertView.findViewById(R.id.task_category).setVisibility(View.INVISIBLE);

            if (child.getColorBackground() != null)
                convertView.findViewById(R.id.list_card).setBackgroundColor(child.getColorBackground());
        } else {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText("+");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
