package com.example.sulemanshakil.todoapp.taskdetail;


import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import com.example.sulemanshakil.todoapp.R;
import com.example.sulemanshakil.todoapp.TestUtils;
import com.example.sulemanshakil.todoapp.data.FakeTasksRemoteDataSource;
import com.example.sulemanshakil.todoapp.data.Task;
import com.example.sulemanshakil.todoapp.data.source.TasksRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests for the tasks screen, the main screen which contains a list of all tasks.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskDetailScreenTest {

    private static String TASK_TITLE = "ATSL";

    private static String TASK_DESCRIPTION = "Rocks";

    /**
     * {@link Task} stub that is added to the fake service API layer.
     */
    private static Task ACTIVE_TASK = new Task(TASK_TITLE, TASK_DESCRIPTION, false);

    /**
     * {@link Task} stub that is added to the fake service API layer.
     */
    private static Task COMPLETED_TASK = new Task(TASK_TITLE, TASK_DESCRIPTION, true);


    @Rule
    public ActivityTestRule<TaskDetailActivity> mTaskDetailActivityTestRule =
            new ActivityTestRule<>(TaskDetailActivity.class, true /* Initial touch mode  */,
                    false /* Lazily launch activity */);

    private void loadActiveTask() {
        startActivityWithWithStubbedTask(ACTIVE_TASK);
    }

    private void loadCompletedTask() {
        startActivityWithWithStubbedTask(COMPLETED_TASK);
    }

    /**
     * Setup your test fixture with a fake task id. The {@link TaskDetailActivity} is started with
     * a particular task id, which is then loaded from the service API.
     *
     * <p>
     * Note that this test runs hermetically and is fully isolated using a fake implementation of
     * the service API. This is a great way to make your tests more reliable and faster at the same
     * time, since they are isolated from any outside dependencies.
     */
    private void startActivityWithWithStubbedTask(Task task) {
        // Add a task stub to the fake service api layer.
        TasksRepository.destroyInstance();
        FakeTasksRemoteDataSource.getInstance().addTasks(task);

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getId());
        mTaskDetailActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void activeTaskDetails_DisplayedInUi() throws Exception {
        loadActiveTask();

        // Check that the task title and description are displayed
        onView(withId(R.id.task_detail_title)).check(matches(withText(TASK_TITLE)));
        onView(withId(R.id.task_detail_description)).check(matches(withText(TASK_DESCRIPTION)));
        onView(withId(R.id.task_detail_complete)).check(matches(not(isChecked())));
    }

    @Test
    public void completedTaskDetails_DisplayedInUi() throws Exception {
        loadCompletedTask();

        // Check that the task title and description are displayed
        onView(withId(R.id.task_detail_title)).check(matches(withText(TASK_TITLE)));
        onView(withId(R.id.task_detail_description)).check(matches(withText(TASK_DESCRIPTION)));
        onView(withId(R.id.task_detail_complete)).check(matches(isChecked()));
    }

    @Test
    public void orientationChange_menuAndTaskPersist() {
        loadActiveTask();

        // Check delete menu item is displayed and is unique
        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()));

        TestUtils.rotateOrientation(mTaskDetailActivityTestRule.getActivity());

        // Check that the task is shown
        onView(withId(R.id.task_detail_title)).check(matches(withText(TASK_TITLE)));
        onView(withId(R.id.task_detail_description)).check(matches(withText(TASK_DESCRIPTION)));

        // Check delete menu item is displayed and is unique
        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()));
    }
}
