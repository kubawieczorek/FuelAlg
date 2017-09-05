package com.fuelalg;

import org.jfree.JCommon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 05.09.2017.
 */
public class ChartData {

    private ArrayList<Double> dataX;
    private ArrayList<Double> dataY;

    public ChartData(){
        dataX = new ArrayList<>();
        dataY = new ArrayList<>();
    }

    public void addData(double _dataX, double _dataY){
        dataX.add(_dataX);
        dataY.add(_dataY);
    }
    public JFreeChart createChart(String descrChart, String descrX, String descrY) {

        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data =new double[2][dataY.size()];
        for(int x = 0; x <dataX.size(); x++){
            data[0][x] = dataX.get(x);
        }
        for(int y = 0;y <dataY.size(); y++){
            data[1][y] = dataY.get(y);
        }

        ds.addSeries(descrChart, data);

        JFreeChart chart =
                ChartFactory.createXYLineChart(descrChart,
                        descrX, descrY, ds, PlotOrientation.VERTICAL, true, true,
                        false);
        return chart;
    }
}
