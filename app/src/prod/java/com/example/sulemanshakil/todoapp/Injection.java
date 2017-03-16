package com.example.sulemanshakil.todoapp;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.sulemanshakil.todoapp.data.source.TasksRepository;
import com.example.sulemanshakil.todoapp.data.source.local.TasksLocalDataSource;
import com.example.sulemanshakil.todoapp.data.source.remote.TasksRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;




/*
 * Enables injection of production implementations for
 * {@link TasksDataSource} at compile time.
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return TasksRepository.getInstance(TasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(context));
    }
}