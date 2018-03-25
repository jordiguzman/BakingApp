package appkite.jordiguzman.com.backingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import appkite.jordiguzman.com.backingapp.ui.MainActivity;


@RunWith(AndroidJUnit4.class)
public class AppTest {

    private IdlingResource idlingResource;

     @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);




}