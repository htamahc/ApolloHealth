package com.example.apollohealth;

public class MetricGenerator {
    private final float WALKING_MET = 3f;
    private final float ASCENDING_MET = 8.5f;
    private final float DESCENDING_MET = 3.5f;

    private final float WALKING_SPEED = 5;
    private final float WALKING_SPEED_UP = 1.8f;
    private final float WALKING_SPEED_DOWN = 2.5f;

    private final float STEPS_PER_FLIGHT = 15;

    private final int STEP_THRESHOLD = 5000;
    private final int FLIGHT_THRESHOLD = 8;

    private final int SCREEN_TIME_THRESHOLD = 14400;
    private final int UNLOCKS_THRESHOLD = 110;


    private float height;
    private float weight;

    public MetricGenerator(float height, float weight) {
        this.height = height;
        this.weight = weight;
    }

    public float stepsToKm(float steps) {
        float kms = steps * 0.74f * 0.001f;

        return kms;
    }

    public float kmsToSteps(float kms) {
        float steps = kms / (0.74f * 0.001f);

        return steps;
    }

    public float caloriesBurned(int steps, int flights) {
        float meanFlights = (float) flights / 2;

        float cbWalking = ((WALKING_MET * weight * 3.5f) / 200) * (stepsToKm(steps) / WALKING_SPEED) * 60;

        float cbAscending = ((ASCENDING_MET * weight * 3.5f) / 200) * (stepsToKm(meanFlights * STEPS_PER_FLIGHT) / WALKING_SPEED_UP) * 60;

        float cbDescending = ((DESCENDING_MET * weight * 3.5f) / 200) * (stepsToKm(meanFlights * STEPS_PER_FLIGHT) / WALKING_SPEED_DOWN) * 60;

        float cbTotal = cbWalking + cbAscending + cbDescending;

        return cbTotal;
    }

    public float calculateBMI(float height, float weight) {
        float heightInMeters = height / 100;
        float bmi = weight / (heightInMeters * heightInMeters);

        return bmi;
    }

    public int getPhysicalStatus(int steps, int flights, int duration) {
        if (steps < STEP_THRESHOLD * duration && flights < FLIGHT_THRESHOLD * duration) {
            return 0;
        } else if (steps >= STEP_THRESHOLD * duration && flights < FLIGHT_THRESHOLD * duration) {
            return 1;
        } else if (steps < STEP_THRESHOLD * duration && flights >= FLIGHT_THRESHOLD * duration) {
            return 1;
        } else if (steps >= STEP_THRESHOLD * duration && flights >= FLIGHT_THRESHOLD * duration) {
            return 2;
        }

        return -1;
    }

    public int getEmotionalStatus(int screenTime, int unlocks, int duration) {
        if (screenTime < SCREEN_TIME_THRESHOLD * duration && unlocks < UNLOCKS_THRESHOLD * duration) {
            return 0;
        } else if (screenTime >= SCREEN_TIME_THRESHOLD * duration && unlocks < UNLOCKS_THRESHOLD * duration) {
            return 1;
        } else if (screenTime < SCREEN_TIME_THRESHOLD * duration && unlocks >= UNLOCKS_THRESHOLD * duration) {
            return 1;
        } else if (screenTime >= SCREEN_TIME_THRESHOLD * duration && unlocks >= UNLOCKS_THRESHOLD * duration) {
            return 2;
        }

        return -1;
    }
}
