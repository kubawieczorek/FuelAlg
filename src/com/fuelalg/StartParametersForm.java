package com.fuelalg;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by maciej on 19.08.17.
 */
public class StartParametersForm extends JFrame{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tankVolumeText;
    private JTextField tankSurfaceAreaText;
    private JTextField tankHeatConductionCoefficientText;
    private JTextField fuelThermalExpansionCoefficientText;
    private JTextField fuelHeatCapacityText;
    private JTextField fuelDensityText;
    private JTextField fuelDensityBaseTemperatureText;
    private JTextField fuelTemperatureText;
    private JTextField fuelVolumeText;
    private JTextField ambientTemperatureText;

    public StartParametersForm() {
        setContentPane(contentPane);
        setTitle("fuelAlg - start parameters");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        Properties prop = new Properties();
        prop.tankVolume = Double.parseDouble(tankVolumeText.getText());
        prop.tankSurfaceArea = Double.parseDouble(tankSurfaceAreaText.getText());
        prop.tankHeatConductionCoefficient = Double.parseDouble(tankHeatConductionCoefficientText.getText());
        prop.fuelThermalExpansionCoefficient = Double.parseDouble(fuelThermalExpansionCoefficientText.getText());
        prop.fuelHeatCapacity = Double.parseDouble(fuelHeatCapacityText.getText());
        prop.fuelDensity = Double.parseDouble(fuelDensityText.getText());
        prop.fuelDensityBaseTemperature =Double.parseDouble(fuelDensityBaseTemperatureText.getText());

        SimulationState state = new SimulationState();
        state.fuelTemperature = Double.parseDouble(fuelTemperatureText.getText());
        state.fuelVolume = Double.parseDouble(fuelVolumeText.getText());
        state.ambientTemperature = Double.parseDouble(ambientTemperatureText.getText());

        SimulationForm form = new SimulationForm(prop, state);
        form.pack();
        form.setVisible(true);

        System.out.println(fuelTemperatureText);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
