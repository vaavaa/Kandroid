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
 * along with Kandroid. If not, see <http://www.gnu.org/licenses/>.
 */

package vaa.technowize.kandroid.kanboard.events;

import java.util.List;

import vaa.technowize.kandroid.kanboard.KanboardTaskFile;

public interface OnGetAllTaskFilesListener {
    void onGetAllTaskFiles(boolean success, List<KanboardTaskFile> result);
}