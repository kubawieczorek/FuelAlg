package com.fuelalg;

import java.util.ArrayList;

/**
 * Created by Kuba on 22.06.2017.
 */
public class FuelAlg {


    public ArrayList<Double> outputFluidVolumes;
    public ArrayList<Double> outputFluidTemps;

    private ArrayList<Double> ambientTempList;
    private double startFluidTemp;
    private double startFluidVolume;
    private double newFluidVolume;
    private double newFluidTemp;
    private double newAmbientTemp;
    private double oldAmbientTemp;
    private double oldFluidTemp;

    private double thermalExpansionCoefficient;

    public FuelAlg(double _startFluidTemp, double _startFluidVolume,ArrayList<Double> _ambientTempList, double _thermalExpansionCoefficient ){
        startFluidTemp = _startFluidTemp;
        startFluidVolume = _startFluidVolume;
        ambientTempList = _ambientTempList;
        thermalExpansionCoefficient = _thermalExpansionCoefficient;
    }

    private void run(){
        int iteration = 0;
        newFluidVolume = startFluidVolume;
        newFluidTemp = startFluidTemp;
        newAmbientTemp = ambientTempList.get(iteration);

        while(true){
            iteration++;
            outputFluidTemps.add(newFluidTemp);
            outputFluidTemps.add(newFluidVolume);

            oldAmbientTemp = newAmbientTemp;
            newAmbientTemp = ambientTempList.get(iteration);

            oldFluidTemp = newFluidTemp;
            newFluidTemp = calculateFluidTemp(newAmbientTemp, newFluidTemp);

            newFluidVolume = calculateFluidVolume(newFluidVolume, newFluidTemp, oldFluidTemp, thermalExpansionCoefficient);
        }

    }

    private double calculateFluidTemp(double _ambientTemp, double _currentFluidTemp){
        // TO DO...
        return 0;
    }

    private double calculateFluidVolume(double _fluidVolume, double _newFluidTemp, double _oldFluidTemp, double _coefficient ){
        return _fluidVolume * (1 + _coefficient * (_newFluidTemp - _oldFluidTemp));
    }
}
