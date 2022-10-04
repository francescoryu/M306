package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

import backend.Messwerte;

public class DataGUI {

    private static final String title = "Stromzähler-App";
    private Messwerte m = new Messwerte();
    private Map<Long, Double> mapB = new TreeMap<Long, Double>();
    private Map<Long, Double> mapE = new TreeMap<Long, Double>();
    private ChartPanel chartPanel;
    /*private Date d = new Date(2005, Calendar.DECEMBER, 6);
    private RegularTimePeriod rtp = RegularTimePeriod.createInstance(null, d, 431, "en-US");*/


    public DataGUI(Messwerte m) {
        mapB = m.getMapB();
        mapE = m.getMapE();
        System.out.println("Test");
        chartPanel = createChart();
        JFrame f = new JFrame(title);
        f.setTitle(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setMouseWheelEnabled(true);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //panel.add(createTrace());
        //panel.add(createDate());
        panel.add(createZoom());
        f.add(panel, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }



    /*private JComboBox createTrace() {
        final JComboBox trace = new JComboBox();
        final String[] traceCmds = {"Enable Trace", "Disable Trace"};
        trace.setModel(new DefaultComboBoxModel(traceCmds));
        trace.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (traceCmds[0].equals(trace.getSelectedItem())) {
                    chartPanel.setHorizontalAxisTrace(true);
                    chartPanel.setVerticalAxisTrace(true);
                    chartPanel.repaint();
                } else {
                    chartPanel.setHorizontalAxisTrace(false);
                    chartPanel.setVerticalAxisTrace(false);
                    chartPanel.repaint();
                }
            }
        });
        return trace;
    }

    private JComboBox createDate() {
        final JComboBox date = new JComboBox();
        final String[] dateCmds = {"Horizontal Dates", "Vertical Dates"};
        date.setModel(new DefaultComboBoxModel(dateCmds));
        date.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFreeChart chart = chartPanel.getChart();
                XYPlot plot = (XYPlot) chart.getPlot();
                DateAxis domain = (DateAxis) plot.getDomainAxis();
                if (dateCmds[0].equals(date.getSelectedItem())) {
                    domain.setVerticalTickLabels(false);
                } else {
                    domain.setVerticalTickLabels(true);
                }
            }
        });
        return date;
    }*/

    private JButton createZoom() {
        final JButton auto = new JButton(new AbstractAction("Zoom Back") {

            @Override
            public void actionPerformed(ActionEvent e) {
                chartPanel.restoreAutoBounds();
            }
        });
        return auto;
    }

    private ChartPanel createChart() {
        XYDataset roiData = createDataset();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Datum", "Wh", roiData, true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer =
                (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        //renderer.setBaseShapesVisible(true);
        /*DateFormat dateFormat = DateFormat.getDateInstance();
        DateAxis dateAxis = (DateAxis) plot.getRangeAxis();
        dateAxis.setDateFormatOverride(dateFormat);*/
        return new ChartPanel(chart);
    }

    private XYDataset createDataset() { // creates the different lines
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(createSeries("Bezug", mapB));
        tsc.addSeries(createSeries("Einspeisung", mapE));
        return tsc;
    }



    private TimeSeries createSeries(String name, Map<Long, Double> map) { // creates the points on the lines
        TimeSeries series = new TimeSeries(name);
        for (Map.Entry<Long, Double> entry : map.entrySet()) {
            series.add(new FixedMillisecond(entry.getKey()), entry.getValue());
        }
        return series;
    }
}