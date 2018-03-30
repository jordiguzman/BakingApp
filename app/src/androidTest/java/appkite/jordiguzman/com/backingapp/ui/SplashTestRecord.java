package appkite.jordiguzman.com.backingapp.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import appkite.jordiguzman.com.backingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashTestRecord {

    @Rule
    public ActivityTestRule<Splash> mActivityTestRule = new ActivityTestRule<>(Splash.class);

    @Test
    public void splashTestRecord() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_name), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_main),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_item_detail), withText("Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Starting prep")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.fb_detail), withText("Ingredients"),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("Ingredients")));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fb_detail), withText("Ingredients"),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tv_item_ingredient), withText("Graham Cracker crumbs"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout),
                                        1),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("Graham Cracker crumbs")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
