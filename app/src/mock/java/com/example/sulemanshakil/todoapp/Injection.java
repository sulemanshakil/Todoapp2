package com.example.sulemanshakil.todoapp;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.sulemanshakil.todoapp.data.FakeTasksRemoteDataSource;
import com.example.sulemanshakil.todoapp.data.source.TasksRepository;
import com.example.sulemanshakil.todoapp.data.source.local.TasksLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;



public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(context));
    }
}