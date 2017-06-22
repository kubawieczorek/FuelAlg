package com.fuelalg;

import java.util.ArrayList;

/**
 * Created by Kuba on 22.06.2017.
 */
public class FuelAlg {


    private ArrayList<Double> ambientTempList;
    private double startFluidTemp;
    private double startFluidVolume;
    private double newFluidVolume;
    private double newFluidTemp;
    private double newAmbientTemp;

    public FuelAlg(double _startFluidTemp, double _startFluidVolume,ArrayList<Double> _ambientTempList ){
        startFluidTemp = _startFluidTemp;
        startFluidVolume = _startFluidVolume;
        ambientTempList = _ambientTempList;
    }

    private void Run(){
        int iteration = 0;
        newFluidVolume = startFluidVolume;
        newFluidTemp = startFluidTemp;
        newAmbientTemp = ambientTempList.get(iteration);

        // Algorytm...

    }
}
