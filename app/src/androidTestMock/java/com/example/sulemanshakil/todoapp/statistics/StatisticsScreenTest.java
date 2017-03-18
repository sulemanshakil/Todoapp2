package com.example.sulemanshakil.todoapp.statistics;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.sulemanshakil.todoapp.R;
import com.example.sulemanshakil.todoapp.data.FakeTasksRemoteDataSource;
import com.example.sulemanshakil.todoapp.data.Task;
import com.example.sulemanshakil.todoapp.data.source.TasksRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

public class StatisticsScreenTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<StatisticsActivity> mStatisticsActivityTestRule =
            new ActivityTestRule<>(StatisticsActivity.class, true, false);

    /**
     * Setup your test fixture with a fake task id. The {@link TaskDetailActivity} is started with
     * a particular task id, which is then loaded from the service API.
     *
     * <p>
     * Note that this test runs hermetically and is fully isolated using a fake implementation of
     * the service API. This is a great way to make your tests more reliable and faster at the same
     * time, since they are isolated from any outside dependencies.
     */
    @Before
    public void intentWithStubbedTaskId() {
        // Given some tasks
        TasksRepository.destroyInstance();
        FakeTasksRemoteDataSource.getInstance().addTasks(new Task("Title1", "", false));
        FakeTasksRemoteDataSource.getInstance().addTasks(new Task("Title2", "", true));

        // Lazily start the Activity from the ActivityTestRule
        Intent startIntent = new Intent();
        mStatisticsActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void Tasks_ShowsNonEmptyMessage() throws Exception {
        // Check that the active and completed tasks text is displayed
        String expectedActiveTaskText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.statistics_active_tasks);
        onView(withText(containsString(expectedActiveTaskText))).check(matches(isDisplayed()));
        String expectedCompletedTaskText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.statistics_completed_tasks);
        onView(withText(containsString(expectedCompletedTaskText))).check(matches(isDisplayed()));
    }

}
