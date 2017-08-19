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


}
