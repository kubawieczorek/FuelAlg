package com.fuelalg;

import java.util.ArrayList;

/**
 * Created by maciej on 18.08.17.
 */
public class SimulationState {
    public double fuelTemperature;
    public double fuelVolume;
    public double ambientTemperature;

    @Override
    public SimulationState clone()
    {
        SimulationState s = new SimulationState();
        s.fuelTemperature = fuelTemperature;
        s.fuelVolume = fuelVolume;
        s.ambientTemperature = ambientTemperature;
        return s;
    }
}
