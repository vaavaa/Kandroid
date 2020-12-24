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


import android.app.AppComponentFactory;
import android.app.Application;
import android.app.job.JobInfo;
import android.content.Context;
import android.util.Log;

import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.annotation.AcraCore;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.HttpSenderConfigurationBuilder;
import org.acra.config.LimiterConfigurationBuilder;
import org.acra.config.SchedulerConfigurationBuilder;
import org.acra.data.StringFormat;
import org.acra.sender.HttpSender;


//@ReportsCrashes(formUri = "https://crash.patrickkostjens.nl/report",
//        mode = ReportingInteractionMode.DIALOG,
//        formUriBasicAuthLogin = "UGosGwubLYytIPAM",
//        formUriBasicAuthPassword = "nDOMOkZd6zbaHNuI",
//        excludeMatchingSharedPreferencesKeys = {"username", "password", "serverurl"},
//        httpMethod = HttpSender.Method.POST,
//        reportType = HttpSender.Type.JSON,
//        logcatArguments = {"-t", "200", "-v", "time", "Kandroid:d", "InstantRun:s", "*:e"},
//        resDialogIcon = android.R.drawable.stat_notify_error,
//        resDialogTitle = R.string.acra_dialog_title,
//        resDialogText = R.string.acra_dialog_text_upload,
//        resDialogCommentPrompt = R.string.acra_dialog_comment_prompt)


@AcraCore(buildConfigClass = BuildConfig.class)
public class KandroidApplication extends Application {

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
