package com.example.simulator.tools;


public class TrainingTypes {
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;
    public static final int TYPE3 = 3;

    public static String getTrainingName(int trainingType) {
        String result;
        switch (trainingType) {
            case TrainingTypes.TYPE1: {
                result = "Left-Right 90";
                break;
            }
            case TrainingTypes.TYPE2: {
                result = "Left-Right 90 intensive";
                break;
            }
            case TrainingTypes.TYPE3: {
                result = "Up-Down 90";
                break;
            }
            default: {
                result = "Undefined";
            }
        }
        return result;
    }

    public static int getTrainingIntensity(int trainingType) {
        int result;
        switch (trainingType) {
            case TrainingTypes.TYPE1: {
                result = 2000;
                break;
            }
            case TrainingTypes.TYPE2: {
                result = 1000;
                break;
            }
            case TrainingTypes.TYPE3: {
                result = 2000;
                break;
            }
            default: {
                result = -1;
            }
        }
        return result;
    }

    public static String[] getAllTrainingNames() {
        return new String[] {getTrainingName(TYPE1), getTrainingName(TYPE2), getTrainingName(TYPE3)};
    }

    public static int getFullIterationsCount(int trainingType) {
        int result;
        switch (trainingType) {
            case TrainingTypes.TYPE1: {
                result = 20;
                break;
            }
            case TrainingTypes.TYPE2: {
                result = 20;
                break;
            }
            case TrainingTypes.TYPE3: {
                result = 10;
                break;
            }
            default: {
                result = 1;
            }
        }
        return result;
    }
}
