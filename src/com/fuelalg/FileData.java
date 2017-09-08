package com.fuelalg;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Kuba on 05.09.2017.
 */
public class FileData {
    PrintWriter out;
    public void prepareFile(){
        try {
            File file = new File("data.csv");
            out = new PrintWriter(file);
            out.println("Time, Fuel Temperature, Fuel Volume, Ambient Temperature");
        }catch(Exception ex){}
    }
    public void saveData(double time, SimulationState state){
         out.println(time+","+state.fuelTemperature +","+state.fuelVolume +","+state.ambientTemperature);
    }

    public void close(){
        out.close();
    }
    public void flush() {out.flush();}
}
