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

package vaa.technowize.kandroid.kanboard;

import org.json.JSONObject;

import java.io.Serializable;

@SuppressWarnings("unused")
public class KanboardCategory implements Serializable {
    private int Id;
    private String Name;
    private int ProjectId;

    public KanboardCategory(JSONObject category) {
        Id = category.optInt("id");
        Name = category.optString("name");
        ProjectId = category.optInt("project_id");
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getProjectId() {
        return ProjectId;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
