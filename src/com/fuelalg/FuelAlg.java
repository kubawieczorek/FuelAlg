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
    private double containerTemp;


    // Wspolczynnik rozszerzalnosci cieplnej
    private double thermalExpansionCoefficient;
    // Cieplo wlasciwe
    private double molarHeatCapacity;
    // Pole pojemnika na ciecz
    private double containerArea;
    // Współczynnik wnikania ciepla cieczy
    private double fluidNusseltNumber;


    public FuelAlg(double _startFluidTemp,
                   double _startFluidVolume,
                   ArrayList<Double> _ambientTempList,
                   double _thermalExpansionCoefficient,
                   double _molarHeatCapacity,
                   double _containerArea,
                   double _fluidNusseltNumber){
        startFluidTemp = _startFluidTemp;
        startFluidVolume = _startFluidVolume;
        ambientTempList = _ambientTempList;
        thermalExpansionCoefficient = _thermalExpansionCoefficient;
        molarHeatCapacity = _molarHeatCapacity;
        containerArea = _containerArea;
        fluidNusseltNumber = _fluidNusseltNumber;
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

            //Zaktualizowanie temperatury pojemnika
            containerTemp = calculateContainerTemp(newAmbientTemp, containerTemp);

            // Obliczenie nowej temperatury cieczy
            oldFluidTemp = newFluidTemp;
            double currentFluidMass = calculateFluidMass();
            newFluidTemp = calculateFluidTemp(containerTemp, newFluidTemp, currentFluidMass, molarHeatCapacity);

            //Obliczenie nowej objetosci cieczy
            newFluidVolume = calculateFluidVolume(newFluidVolume, newFluidTemp, oldFluidTemp, thermalExpansionCoefficient);
        }

    }

    // Obliczenie temperatury cieczy
    private double calculateFluidTemp(double _containerTemp,
                                      double _currentFluidTemp,
                                      double _currentFluidMass,
                                      double _molarHeatCapacity){
        // Ze wzoru na wnikanie ciepła i wzoru na energie ogrzewania (ochładzania) ciała
        double _fluidTempDelta =
                (fluidNusseltNumber* (_containerTemp - _currentFluidTemp) * containerArea) /(_currentFluidMass *_molarHeatCapacity);
        if(_currentFluidTemp > _containerTemp){
            return _currentFluidTemp - _fluidTempDelta;
        }
        else if (_currentFluidTemp < _containerTemp){
            return _fluidTempDelta - _currentFluidTemp;
        }
        else return _currentFluidTemp;
    }

    //Obliczenie objetosci cieczy
    private double calculateFluidVolume(double _fluidVolume,
                                        double _newFluidTemp,
                                        double _oldFluidTemp,
                                        double _coefficient ){
        return _fluidVolume * (1 + _coefficient * (_newFluidTemp - _oldFluidTemp));
    }

    // Obliczenie temperatury pojemnika
    private double calculateContainerTemp(double _ambientTemp,
                                          double _currentContainerTemp){
        // TO DO..
        return 0;
    }

    private double calculateFluidMass(){
        // TO DO..
        return 0;
    }
}
