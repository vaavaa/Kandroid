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

package vaa.technowize.kandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.annotation.AcraCore;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.data.StringFormat;


@AcraCore(buildConfigClass = BuildConfig.class)
public class KandroidApplication extends Application {

    public ApplicationComponent appComponent = DaggerApplicationComponent.create();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Constants.TAG, "Start ACRA");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this)
                .setBuildConfigClass(BuildConfig.class)
                .setReportFormat(StringFormat.JSON);
//        builder.getPluginConfigurationBuilder(HttpSenderConfigurationBuilder.class)
//                .setUri("https://yourdomain.com/acra/report")
//                .setHttpMethod(HttpSender.Method.POST)
//                .setBasicAuthLogin("*****")
//                .setBasicAuthPassword("*****")
//                .setEnabled(true);
//        builder.getPluginConfigurationBuilder(SchedulerConfigurationBuilder.class)
//                .setRequiresNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setRequiresBatteryNotLow(true)
//                .setEnabled(true);
//        builder.getPluginConfigurationBuilder(LimiterConfigurationBuilder.class)
//                .setEnabled(true);
        ACRA.init(this, builder);
    }

}
