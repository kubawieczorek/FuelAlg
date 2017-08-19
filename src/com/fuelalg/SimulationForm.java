package com.fuelalg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by maciej on 19.08.17.
 */
public class SimulationForm extends JFrame{
    private Properties prop;
    private JPanel contentPane;
    private JProgressBar volumeBar;
    private JLabel totalVolume;
    private JLabel fluidTemperature;
    private JLabel ambientTemperature;
    private JButton startButton;
    private JButton stopButton;
    private JButton setAmbientTemperatureButton;
    private JButton addInflowButton;
    private JButton addOutflowButton;
    private JLabel working;

    private boolean isRunning = true;
    private FuelAlg fuelAlg;

    public SimulationForm(Properties p, SimulationState startState)
    {
        setContentPane(contentPane);
        setTitle("fuelAlg - simulation");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        prop = p;
        fuelAlg = new FuelAlg(startState, prop);

        int cnt = (int)(fuelAlg.getDt() * 1000);
        System.out.println(cnt);
        Timer  timer=new Timer(cnt,new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(isRunning) {
                    //System.out.println("test " + new Date().toString());
                    fuelAlg.doIteration();
                    updateData();
                }
            }
        });
        timer.start();

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isRunning = true;
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isRunning = false;
                updateData();
            }
        });

        setAmbientTemperatureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String val = JOptionPane.showInputDialog( "Enter new ambient temperature:", fuelAlg.getAmbientTemperature());
                fuelAlg.setAmbientTemperature(Double.parseDouble(val));
                updateData();
            }
        });

        addInflowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FuelInflow f = new FuelInflow();
                f.litresPerSecond = Double.parseDouble(JOptionPane.showInputDialog( "litersPersecond:", 1));
                f.limitVolume = Double.parseDouble(JOptionPane.showInputDialog( "limitVolume:", 1));
                f.temperature = Double.parseDouble(JOptionPane.showInputDialog( "temperature:", 1));
                fuelAlg.addInflow(f);
                updateData();
            }
        });

        addOutflowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FuelOutflow f = new FuelOutflow();
                f.litresPerSecond = Double.parseDouble(JOptionPane.showInputDialog( "litersPersecond:", 1));
                f.limitVolume = Double.parseDouble(JOptionPane.showInputDialog( "limitVolume:", 1));
                fuelAlg.addOutflow(f);
                updateData();
            }
        });

        volumeBar.setMaximum(1000);


    }

    private void updateData()
    {
        totalVolume.setText("Fuel volume: " + String.valueOf(fuelAlg.getFuelVolume()));
        fluidTemperature.setText("Fuel temperature: " + String.valueOf(fuelAlg.getFuelTemperature()));
        ambientTemperature.setText("Ambient temperature: " + String.valueOf(fuelAlg.getAmbientTemperature()));
        working.setText("Simulation working: " + String.valueOf(isRunning));
        int p = (int)(fuelAlg.getFuelVolume() / prop.tankVolume * 1000);
        volumeBar.setValue(p);
        //volumeBar.setString(String.valueOf(fuelAlg.getFuelVolume()));
    }

}
