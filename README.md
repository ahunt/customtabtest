# customtabtest
A CustomTab test app for testing various custom tab parameters AND malformed bundles

The primary purpose of this app is to generate malformed CustomTab Intents that can cause browsers
to crash. You can read more about this in:
https://bugzilla.mozilla.org/show_bug.cgi?id=1090385

Custom Tabs add an extra layer to this: in the past we only had to read Intent.extras. Custom Tabs
involves adding sub-bundles, e.g. CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE is another bundle.
It's possible to stuff unsafe data into that bundle, which this app demonstrates.
