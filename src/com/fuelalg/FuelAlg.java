
        package com.fuelalg;

        import java.util.ArrayList;
        import java.util.Iterator;

/**
 * Created by Kuba on 22.06.2017.
 */
public class FuelAlg {

    //private ArrayList<Double> ambientTempList;
    //private double startFluidTemp;
    //private double startFluidVolume;
    //private double newFluidVolume;
    //private double newFluidTemp;
    //private double newAmbientTemp;
    //private double oldAmbientTemp;
    //private double oldFluidTemp;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }
    //krok symulacji w sekundach
    private double dt = 0.1;

    private Properties properties;
    private SimulationState currentState;
    private SimulationState newState;
    public ArrayList<FuelInflow> inflows;
    public ArrayList<FuelOutflow> outflows;

    public FuelAlg(SimulationState startState,  Properties constantProperties)
    {
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

    public double getFuelVolume(){ return currentState.fuelVolume; }
    public double getFuelTemperature(){ return currentState.fuelTemperature;}
    public double getAmbientTemperature(){return currentState.ambientTemperature;}

    public void doIteration()
    {
        processInflows();
        processOutflows();
        processHeatExchange();
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
        //TODO: lepsza obsługa tego że jest pusty
    }

    private void processHeatExchange()
    {
        //TODO: poprawić skopany wzór w diagramie
        double mass = getMass(newState.fuelVolume, newState.fuelTemperature);
        double kelvinPerSecond = properties.tankHeatConductionCoefficient *
                (newState.ambientTemperature - newState.fuelTemperature) * properties.tankSurfaceArea  / (mass * properties.fuelHeatCapacity);
        newState.fuelTemperature += kelvinPerSecond * dt;
        newState.fuelVolume = getVolume (mass, newState.fuelTemperature);
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

    private void calculateFluidVolume()
    {
        //return _fluidVolume * (1 + _coefficient * (_newFluidTemp - _oldFluidTemp));
    }
}