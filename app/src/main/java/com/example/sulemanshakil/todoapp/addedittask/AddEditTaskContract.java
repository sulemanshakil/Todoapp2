package com.example.sulemanshakil.todoapp.addedittask;


import com.example.sulemanshakil.todoapp.BasePresenter;
import com.example.sulemanshakil.todoapp.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }
}
