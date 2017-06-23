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


    // Wspolczynnik rozszerzalnosci cieplnej
    private double thermalExpansionCoefficient;
    // Cieplo wlasciwe
    private double molarHeatCapacity;
    // Pole pojemnika na ciecz
    private double containerArea;

    public FuelAlg(double _startFluidTemp,
                   double _startFluidVolume,
                   ArrayList<Double> _ambientTempList,
                   double _thermalExpansionCoefficient,
                   double _molarHeatCapacity,
                   double _containerArea){
        startFluidTemp = _startFluidTemp;
        startFluidVolume = _startFluidVolume;
        ambientTempList = _ambientTempList;
        thermalExpansionCoefficient = _thermalExpansionCoefficient;
        molarHeatCapacity = _molarHeatCapacity;
        containerArea = _containerArea;
    }

    private void run(){
        int iteration = 0;
        newFluidVolume = startFluidVolume;
        newFluidTemp = startFluidTemp;
        newAmbientTemp = ambientTempList.get(iteration);

        while(true){
            iteration++;

            // Zaktualizowanie danych wyjsciowych
            outputFluidTemps.add(newFluidTemp);
            outputFluidTemps.add(newFluidVolume);

            // Zaktualizowanie temperatury otoczenia
            oldAmbientTemp = newAmbientTemp;
            newAmbientTemp = ambientTempList.get(iteration);

            // Obliczenie nowej temperatury cieczy
            oldFluidTemp = newFluidTemp;
            newFluidTemp = calculateFluidTemp(newAmbientTemp, newFluidTemp);

            //Obliczenie nowej objetosci cieczy
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
