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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Collections;

import kandroid.R;
import vaa.technowize.kandroid.CompactHtmlRenderer;
import vaa.technowize.kandroid.Utils;
import vaa.technowize.kandroid.kanboard.KanboardProject;

public class ProjectOverviewFragment extends Fragment {
    private Parser mParser = Parser.builder().build();
    private HtmlRenderer mRenderer = HtmlRenderer.builder().nodeRendererFactory(new HtmlNodeRendererFactory() {
        @Override
        public NodeRenderer create(HtmlNodeRendererContext context) {
            return new CompactHtmlRenderer(context);
        }
    }).build();

    public ProjectOverviewFragment() {}

    public static Fragment newInstance() {
        return new ProjectOverviewFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KanboardProject project = ((MainActivity) getActivity()).getProject();
        if (project != null) {
            assert getView() != null : "ProjectOverviewFragment: getView() returned null";
            TextView projectDescription = (TextView) getView().findViewById(R.id.project_description);
            projectDescription.setMovementMethod(LinkMovementMethod.getInstance());
            TextView projectNBActiveTasks = (TextView) getView().findViewById(R.id.project_active_tasks);
            TextView projectNBInactiveTasks = (TextView) getView().findViewById(R.id.project_inactive_tasks);
            TextView projectNBOverdueTasks = (TextView) getView().findViewById(R.id.project_overdue_tasks);
            TextView projectNBTotalTasks = (TextView) getView().findViewById(R.id.project_total_tasks);
            TextView projectModifyDate = (TextView) getView().findViewById(R.id.project_modify_date);
            TextView projectMembers = (TextView) getView().findViewById(R.id.project_members);
            TextView projectColumns = (TextView) getView().findViewById(R.id.project_columns);
            TextView projectSwimlanes = (TextView) getView().findViewById(R.id.project_swimlanes);

            if (!project.getDescription().contentEquals("")) {
                projectDescription.setText(Utils.fromHtml(mRenderer.render(mParser.parse(project.getDescription()))));
            } else {
                getView().findViewById(R.id.card_description).setVisibility(View.GONE);
            }
            projectMembers.setText(Utils.fromHtml(TextUtils.join(" <big><b>|</b></big> ", Collections.list(project.getProjectUsers().elements()))));
            projectColumns.setText(Utils.fromHtml(TextUtils.join(" <big><b>|</b></big> ", project.getColumns())));
            projectSwimlanes.setText(Utils.fromHtml(TextUtils.join(" <big><b>|</b></big> ", project.getSwimlanes())));
            projectNBActiveTasks.setText(getContext().getResources().getQuantityString(R.plurals.format_nb_active_tasks, project.getActiveTasks().size(), project.getActiveTasks().size()));
            projectNBInactiveTasks.setText(getContext().getResources().getQuantityString(R.plurals.format_nb_inactive_tasks, project.getInactiveTasks().size(), project.getInactiveTasks().size()));
            projectNBOverdueTasks.setText(getContext().getResources().getQuantityString(R.plurals.format_nb_overdue_tasks, project.getOverdueTasks().size(), project.getOverdueTasks().size()));
            projectNBTotalTasks.setText(getContext().getResources().getQuantityString(R.plurals.format_nb_total_tasks, project.getActiveTasks().size() + project.getInactiveTasks().size(), project.getActiveTasks().size() + project.getInactiveTasks().size()));
            if (project.getLastModified() != null)
                projectModifyDate.setText(DateFormat.getLongDateFormat(getContext()).format(project.getLastModified()) + " " + DateFormat.getTimeFormat(getContext()).format(project.getLastModified()));
            else
                projectModifyDate.setText("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_overview, container, false);
    }
}
