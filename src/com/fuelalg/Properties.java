package com.fuelalg;

/**
 * Created by maciej on 18.08.17.
 * Klasa na parametry symulacji które się nie zmieniają w trakcie jej trwania
 */
public class Properties {
    public double tankVolume;
    // Pole pojemnika na ciecz
    public double tankSurfaceArea;
    public double tankHeatConductionCoefficient;
    // Wspolczynnik rozszerzalnosci cieplnej paliwa
    public double fuelThermalExpansionCoefficient;
    // Cieplo wlasciwe paliwa
    public double fuelHeatCapacity;
    //gęstość
    public double fuelDensity;
    //temperatura w której został wykonany pomiar gęstości
    public double fuelDensityBaseTemperature;
    @Override
    protected Properties clone()
    {
        Properties p = new Properties();
        p.tankVolume = tankVolume;
        p.tankSurfaceArea = tankSurfaceArea;
        p.tankHeatConductionCoefficient = tankHeatConductionCoefficient;
        p.fuelThermalExpansionCoefficient = fuelThermalExpansionCoefficient;
        p.fuelHeatCapacity = fuelHeatCapacity;
        p.fuelDensity = fuelDensity;
        p.fuelDensityBaseTemperature = fuelDensityBaseTemperature;
        return p;
    }

    public void verify()
    {
        if (tankVolume <=0) throw new IllegalArgumentException("tankVolume <=0");
        if (tankSurfaceArea <= 0) throw new IllegalArgumentException("tankSurfaceArea <=0");
        if (tankHeatConductionCoefficient <=0) throw new IllegalArgumentException("tankHeatConductionCoefficient <=0");
        if (fuelThermalExpansionCoefficient <=0 ) throw new IllegalArgumentException("fuelThermalExpansionCoefficient <=0 ");
        if (fuelHeatCapacity <=0) throw new IllegalArgumentException("fuelHeatCapacity <=0");
        if (fuelDensity <= 0) throw new IllegalArgumentException("fuelDensity <= 0");
        if (fuelDensityBaseTemperature < -273.15) throw new IllegalArgumentException("fuelDensityBaseTemperature < -273.15");
    }


}
