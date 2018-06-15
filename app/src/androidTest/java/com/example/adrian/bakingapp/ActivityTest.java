package com.example.adrian.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.adrian.bakingapp.assertions.RecyclerViewItemCountAssertion;
import com.example.adrian.bakingapp.ui.MainActivity;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerview_displaysAllItems() {
        onView(withId(R.id.rv_recipies)).check(new RecyclerViewItemCountAssertion(4));
    }

    @Test
    public void recyclerview_checkSecondElement() {
        onView(ViewMatchers.withId(R.id.rv_recipies)).
                perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerview_clickItem() {
        onView(withId(R.id.rv_recipies)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));
        onView(withId(R.id.rv_step_list)).check(matches(isDisplayed()));
    }

    @Test
    public void ingredients_displayedProperly() {
        onView(withId(R.id.rv_recipies)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.ingredients_list)).check(
                new RecyclerViewItemCountAssertion(10));
    }

    @Test
    public void player_displayedOnFirstStep() {
        onView(withId(R.id.rv_recipies)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));
        onView(withId(R.id.rv_step_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    @Test
    public void player_displayedOnlyOnStepsWithVideo() {
        onView(withId(R.id.rv_recipies)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));
        onView(withId(R.id.rv_step_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));
        try {
            onView(withId(R.id.player_view)).check(matches(isDisplayed()));
            // View is displayed but shouldn't be
            assert false;
        } catch (AssertionFailedError e) {
            assert true;
        }
    }
}
