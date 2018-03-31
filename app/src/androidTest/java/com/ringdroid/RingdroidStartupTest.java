package com.ringdroid;


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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
/**
 * Simple test to check whether Ringdroid starts correctly:
 *   * When the app is started, the Ringdroid
 */
public class RingdroidStartupTest {

    @Rule
    public ActivityTestRule<RingdroidSelectActivity> mActivityTestRule = new ActivityTestRule<>(RingdroidSelectActivity.class);

    @Test
    public void ringdroidStartupTest() {
        // App title
        ViewInteraction textView = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.TextView.class), withText("Ringdroid"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Ringdroid")));

        // Search button
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.action_search_filter), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        // Record button
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.action_record), withContentDescription("Record New"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        // Triple-dot options button
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                2),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        // Action: click the more options button
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Link to 'about'
        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.title), withText("About"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("About")));

        // Link to 'show all audio'
        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.title), withText("Show All Audio"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Show All Audio")));

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
