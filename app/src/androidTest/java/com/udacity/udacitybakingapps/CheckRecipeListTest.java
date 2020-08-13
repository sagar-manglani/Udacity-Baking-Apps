package com.udacity.udacitybakingapps;

import android.view.View;

import com.udacity.udacitybakingapps.IdlingResource.EspressoIdlingResource;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CheckRecipeListTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.resource);
    }



    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.resource);
    }

    @Test
    public void checkForRecipeListTest(){
        Espresso.onView((withId(R.id.recipe_list))).check(matches(isDisplayed()));
    }
    @Test
    public void clickOnRecipeList() {


        Espresso.onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));


    }

    @Test
    public void backtoRecipeList(){
        Espresso.onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.onView((withId(R.id.recipe_list))).check(matches(isDisplayed()));
    }

    @Test
    public void testRecipeStepDescriptionField(){
        Espresso.onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.recipedetails)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Espresso.onView((withId(R.id.step_description))).check(matches(isDisplayed()));
    }

    @Test
    public void testStepBack(){
        Espresso.onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.recipedetails)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Espresso.onView((withId(R.id.step_description))).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientsRow(){
        Espresso.onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        Espresso.onView((withId(R.id.recipedetails))).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.recipedetails))
                .check(matches(atPosition(0, hasDescendant(withText("See all ingredients")))));
        Espresso.onView(withId(R.id.recipedetails)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }




}
