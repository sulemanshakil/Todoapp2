package com.example.sulemanshakil.todoapp.statistics;


import com.example.sulemanshakil.todoapp.BasePresenter;
import com.example.sulemanshakil.todoapp.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}
