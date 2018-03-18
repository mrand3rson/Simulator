package com.example.simulator.presenters;


public interface ITraining {
    int EXERCISE_FAILED = -1;
    int EXERCISE_COMPLETED = -2;

    int checkExercise1(float[] orientations);
}
