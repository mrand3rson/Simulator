package com.example.simulator.tools;


public class TrainingTypes {
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;

    public static String parseTrainingTypes(int trainingType) {
        String result;
        switch (trainingType) {
            case TrainingTypes.TYPE1: {
                result = "Left-Right 90";
                break;
            }
            case TrainingTypes.TYPE2: {
                result = "";
                break;
            }
            default: {
                result = "Undefined";
            }
        }
        return result;
    }
}