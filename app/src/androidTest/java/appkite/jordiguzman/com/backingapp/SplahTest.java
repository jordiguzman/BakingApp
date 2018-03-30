package appkite.jordiguzman.com.backingapp;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import appkite.jordiguzman.com.backingapp.ui.Splash;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static appkite.jordiguzman.com.backingapp.ImageViewHasDrawableMatcher.hasDrawable;

@RunWith(AndroidJUnit4.class)
public class SplahTest {

    /**
     * It's only test I can do. Other test in MainActivity or others always not pass. Maybe why the data is not loaded yet...
     * The Record Espresso Test it never works. I no understand why it happens. I Include an example of Record Test with the name SplashTestRecord.
     */

    @Rule
    public ActivityTestRule<Splash> splashActivityTestRule=
            new ActivityTestRule<>(Splash.class);

    @Test
    public void uiSplahTest(){
        onView(withId(R.id.textView)).check(matches(withText("Baking App")));
        onView(withId(R.id.iv_splash)).check(matches(hasDrawable()));
        Intent intent = new Intent();
        splashActivityTestRule.launchActivity(intent);
    }





}
