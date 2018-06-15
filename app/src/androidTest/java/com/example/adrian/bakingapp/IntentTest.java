package com.example.adrian.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.adrian.bakingapp.ui.IngredientsActivity;
import com.example.adrian.bakingapp.ui.MainActivity;
import com.example.adrian.bakingapp.ui.StepDetailActivity;
import com.example.adrian.bakingapp.ui.StepListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IntentTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void verifyIntent_StepList() {
        onView(withId(R.id.rv_recipies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(StepListActivity.class.getName()));

    }

    @Test
    public void verifyIntent_Ingrediets() {
        onView(withId(R.id.rv_recipies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fab)).perform(click());
        intended(hasComponent(IngredientsActivity.class.getName()));
    }

    @Test
    public void verifyIntent_StepDetails() {
        onView(withId(R.id.rv_recipies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(StepDetailActivity.class.getName()));
    }

}
