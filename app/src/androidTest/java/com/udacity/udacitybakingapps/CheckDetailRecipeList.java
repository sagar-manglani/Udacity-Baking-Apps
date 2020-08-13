package com.udacity.udacitybakingapps;

import com.udacity.udacitybakingapps.Data.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CheckDetailRecipeList {

    @Rule
    public ActivityTestRule<RecipeDetail> detail=new ActivityTestRule<>(RecipeDetail.class);

    @Test
    public void testRecipeDetailFragment(){
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));

    }

    @Test
    public void testRecipeStepDescriptionField(){
        Espresso.onView(withId(R.id.recipedetails)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Espresso.onView((withId(R.id.step_description))).check(matches(isDisplayed()));

    }



}
