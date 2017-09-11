package com.fuelalg;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private JButton fuelTemperatureChartButton;
    private JButton saveCsvButton;

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

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                fuelAlg.close();
            }
        });

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
                onSetAmbientTemperature();
            }
        });

        addInflowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddInflow();
            }
        });

        addOutflowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddOutflow();
            }
        });

        fuelTemperatureChartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onShowFuelTempChart();
            }
        });
        saveCsvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  onSaveCsv(); }
        });

        volumeBar.setMaximum(1000);


    }

    private void onSetAmbientTemperature()
    {
        try{
            String val = JOptionPane.showInputDialog( "Enter new ambient temperature:", fuelAlg.getAmbientTemperature());
            fuelAlg.setAmbientTemperature(Double.parseDouble(val));
            updateData();
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Number format error: " + e.getMessage());
        }
        catch(IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(this, "Bad number value: " + e.getMessage());
        }
    }

    private void onAddInflow()
    {
        try{
            FuelInflow f = new FuelInflow();
            f.litresPerSecond = Double.parseDouble(JOptionPane.showInputDialog( "Inflow speed (liters/second):", 1));
            f.limitVolume = Double.parseDouble(JOptionPane.showInputDialog( "Outflow max volume (liters):", 1));
            f.temperature = Double.parseDouble(JOptionPane.showInputDialog( "Fuel temperature (C):", 1));
            fuelAlg.addInflow(f);
            updateData();
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Number format error: " + e.getMessage());
        }
        catch(IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(this, "Bad number value: " + e.getMessage());
        }
    }

    private void onAddOutflow()
    {
        try{
            FuelOutflow f = new FuelOutflow();
            f.litresPerSecond = Double.parseDouble(JOptionPane.showInputDialog( "Outflow speed (liters/second):", 1));
            f.limitVolume = Double.parseDouble(JOptionPane.showInputDialog( "Outflow max volume (liters):", 1));
            fuelAlg.addOutflow(f);
            updateData();
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Number format error: " + e.getMessage());
        }
        catch(IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(this, "Bad number value: " + e.getMessage());
        }
    }

    private void updateData()
    {
        totalVolume.setText("Fuel volume: " + String.valueOf(fuelAlg.getFuelVolume()) + " l");
        fluidTemperature.setText("Fuel temperature: " + String.valueOf(fuelAlg.getFuelTemperature()) + " °C");
        ambientTemperature.setText("Ambient temperature: " + String.valueOf(fuelAlg.getAmbientTemperature()) + " °C");
        working.setText("Simulation working: " + String.valueOf(isRunning));
        int p = (int)(fuelAlg.getFuelVolume() / prop.tankVolume * 1000);
        volumeBar.setValue(p);
        //volumeBar.setString(String.valueOf(fuelAlg.getFuelVolume()));
    }

    private void onShowFuelTempChart(){
        ChartData data1 = fuelAlg.getFuelTempChartData();
        ChartData data2 = fuelAlg.getFuelVolumeChartData();
        JFreeChart chart = ChartData.createChart("time[s]", "temperature[Celsius]","volume[l]", "Fuel temperature/volume", "Fuel temperature", "Fuel volume",data1, data2 );
        ChartPanel cp = new ChartPanel(chart);
        JFrame frame = new JFrame("Fuel temperature/volume in time");
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.getContentPane().add(cp);
    }

    private void onSaveCsv()
    {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        fc.setFileFilter(filter);
        fc.setSelectedFile(new File("simulationLog.csv"));
        int res = fc.showSaveDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
            fuelAlg.flush();
            File f = fc.getSelectedFile();
            try {
                Files.copy(Paths.get("data.csv"), Paths.get(f.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "error " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            System.out.println("canceled");
        }
    }

}
