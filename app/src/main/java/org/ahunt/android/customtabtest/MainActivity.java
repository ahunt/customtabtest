package org.ahunt.android.customtabtest;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    /**
     * A custom parcelable that other apps won't be able to unparcel. Trying to unparcel a bundle
     * containing this Parcelable will crash. Browser should be able to handle this (apps such as
     * Facebook accidentally include internal Parcelables in their intents).
     */
    @SuppressLint("ParcelCreator")
    private static class TrashParcelable implements Parcelable {
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    /**
     * Build a Custom Tab Intent containing toolbar colouring, custom buttons, and custom menu items.
     * The share item is not enabled, but can be added by the caller.
     * @return
     */
    private CustomTabsIntent.Builder buildCTIntent() {
        final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(Color.RED);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone);
        builder.setCloseButtonIcon(bitmap);

        final Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        final PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        builder.setActionButton(bitmap, "Hello there", pi);

        builder.addMenuItem("Hello1", pi);
        builder.addMenuItem("Hello2", pi);

        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = "https://www.mozilla.org";

        // TODO: make these configurable
//        builder.addDefaultShareMenuItem();
//        builder.enableUrlBarHiding();
//        customTabsIntent.intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_URLBAR_HIDING, false);

        findViewById(R.id.button_focus_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent.Builder builder = buildCTIntent();
                final CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.intent.setPackage("org.mozilla.focus.debug");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });


        findViewById(R.id.button_focus_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent.Builder builder = buildCTIntent();
                final CustomTabsIntent customTabsIntent = builder.build();

                final Bundle garbage = new Bundle();
                garbage.putParcelable("foobaf", new TrashParcelable());
                customTabsIntent.intent.putExtra(CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE, garbage);

                customTabsIntent.intent.setPackage("org.mozilla.focus.debug");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });

        findViewById(R.id.button_fennec_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent.Builder builder = buildCTIntent();
                final CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.intent.setPackage("org.mozilla.fennec");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });

        findViewById(R.id.button_fennec_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent.Builder builder = buildCTIntent();
                final CustomTabsIntent customTabsIntent = builder.build();

                final Bundle garbage = new Bundle();
                garbage.putParcelable("foobaf", new TrashParcelable());
                customTabsIntent.intent.putExtra(CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE, garbage);

                customTabsIntent.intent.setPackage("org.mozilla.fennec");
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });
    }
}
