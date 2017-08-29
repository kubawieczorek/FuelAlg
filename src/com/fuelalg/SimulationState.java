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

    public void verify()
    {
        if(fuelVolume < 0) throw new IllegalArgumentException("fuelVolume < 0");
        if(fuelTemperature < -273.15) throw new IllegalArgumentException("fuelTemperature < -273.15");
        if(ambientTemperature < -273.15) throw new IllegalArgumentException("ambientTemperature < -273.15");
    }
}
