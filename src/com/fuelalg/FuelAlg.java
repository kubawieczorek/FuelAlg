
package com.fuelalg;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Kuba on 22.06.2017.
 */
public class FuelAlg {

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }
    //krok symulacji w sekundach
    private double dt = 0.1;
    private double time = 0.0;
    private int iterations = 0;

    private Properties properties;
    private FileData fileData;
    private ChartData fuelTempChart;
    private ChartData fuelVolumeChart;
    private SimulationState currentState;
    private SimulationState newState;
    public ArrayList<FuelInflow> inflows;
    public ArrayList<FuelOutflow> outflows;

    public FuelAlg(SimulationState startState,  Properties constantProperties)
    {
        fileData = new FileData();
        fileData.prepareFile();
        fuelTempChart = new ChartData();
        fuelVolumeChart = new ChartData();
        startState.verify();
        constantProperties.verify();
        currentState = startState.clone();
        newState = currentState.clone();
        properties = constantProperties.clone();
        inflows = new ArrayList<FuelInflow>();
        outflows = new ArrayList<FuelOutflow>();
    }


    public void setAmbientTemperature(double t){
        if(t < -273.15) throw new IllegalArgumentException("ambientTemperature < -273.15");
        newState.ambientTemperature = t;
    }
    public void addInflow(FuelInflow i){
        i.verify();
        inflows.add(i);
    }
    public void addOutflow(FuelOutflow o){
        o.verify();
        outflows.add(o);
    }
    public ChartData getFuelTempChartData(){
        return fuelTempChart;
    }
    public ChartData getFuelVolumeChartData(){
        return fuelVolumeChart;
    }

    public double getFuelVolume(){ return currentState.fuelVolume; }
    public double getFuelTemperature(){ return currentState.fuelTemperature;}
    public double getAmbientTemperature(){return currentState.ambientTemperature;}

    public void doIteration()
    {
        processInflows();
        processOutflows();
        processHeatExchange();
        logData();

        currentState = newState;
        newState = currentState.clone();

    }

    private void processInflows()
    {
        for(Iterator<FuelInflow> iter = inflows.listIterator(); iter.hasNext();)
        {
            FuelInflow i = iter.next();
            double addedV = i.litresPerSecond * dt;
            i.limitVolume -= addedV;
            if(i.limitVolume <= 0 ){
                addedV += i.limitVolume;
                iter.remove();
            }
            double baseMass = getMass(newState.fuelVolume, newState.fuelTemperature);
            double addedMass = getMass(addedV ,i.temperature);
            double finalMass = baseMass + addedMass;
            newState.fuelTemperature = (baseMass * newState.fuelTemperature + addedMass * i.temperature)/finalMass;
            newState.fuelVolume = getVolume(finalMass, newState.fuelTemperature);
        }
        if(newState.fuelVolume > properties.tankVolume){
            newState.fuelVolume = properties.tankVolume;
        }
    }

    private void processOutflows()
    {
        for(Iterator<FuelOutflow> iter = outflows.listIterator(); iter.hasNext();)
        {
            FuelOutflow o = iter.next();
            double removedV = o.litresPerSecond * dt;
            o.limitVolume -= removedV;
            if(o.limitVolume <= 0)
            {
                removedV += o.limitVolume;
                iter.remove();
            }
            newState.fuelVolume -= removedV;
        }
        if(newState.fuelVolume < 0)
        {
            newState.fuelVolume = 0;
        }
    }

    private void processHeatExchange()
    {
        if(newState.fuelVolume == 0) return;
        //TODO: poprawić skopany wzór w diagramie
        double mass = getMass(newState.fuelVolume, newState.fuelTemperature);
        double kelvinPerSecond = properties.tankHeatConductionCoefficient *
                (newState.ambientTemperature - newState.fuelTemperature) * properties.tankSurfaceArea  / (mass * properties.fuelHeatCapacity);
        newState.fuelTemperature += kelvinPerSecond * dt;
        newState.fuelVolume = getVolume (mass, newState.fuelTemperature);
        if(newState.fuelVolume > properties.tankVolume){
            newState.fuelVolume = properties.tankVolume;
        }
    }

    private void logData()
    {
        time += dt;
        iterations++;
        if(iterations >= 10){
            fuelTempChart.addData(Math.round(time), newState.fuelTemperature);
            fuelVolumeChart.addData(Math.round(time), newState.fuelVolume);
            fileData.saveData(Math.round(time), newState);
            iterations = 0;
        }
    }

    private double getMass(double volume, double temperature)
    {
        double expansion = properties.fuelThermalExpansionCoefficient * (temperature - properties.fuelDensityBaseTemperature);
        double vInBaseTemperature = volume /( 1 + expansion);
        return properties.fuelDensity * vInBaseTemperature;
    }

    private double getVolume(double mass, double temperature)
    {
        double expansion = properties.fuelThermalExpansionCoefficient * (temperature - properties.fuelDensityBaseTemperature);
        double vInBaseTemperature = mass / properties.fuelDensity;
        return vInBaseTemperature * (1 + expansion);
    }

    public void close(){
        fileData.close();
    }

    public void flush(){
        fileData.flush();
    }

}