package gui;


import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.chart.plot.*;

import database.SalesOrderOperations;
import database.RentalOrderOperations;

import java.awt.*;
import java.util.Calendar;

public class BarChart
{
 
private SalesOrderOperations soo = new SalesOrderOperations();
private RentalOrderOperations roo = new RentalOrderOperations();

public BarChart()
{
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	double total;
	 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	 total= roo.getSales(1,year) + soo.getSales(1,year);
	 dataset.setValue(total, "Marks", "Jan");
	 total= roo.getSales(2,year) + soo.getSales(2,year);
	 dataset.setValue(total, "Marks", "Feb");
	 total= roo.getSales(3,year) + soo.getSales(3,year);
	 dataset.setValue(total, "Marks", "March");
	 total= roo.getSales(4,year) + soo.getSales(4,year);
	 dataset.setValue(total, "Marks", "April");
	 total= roo.getSales(5,year) + soo.getSales(5,year);
	 dataset.setValue(total, "Marks", "May");
	 total= roo.getSales(6,year) + soo.getSales(6,year);
	 dataset.setValue(total, "Marks", "June");
	 total= roo.getSales(7,year) + soo.getSales(7,year);
	 dataset.setValue(total, "Marks", "July");
	 total= roo.getSales(8,year) + soo.getSales(8,year);
	 dataset.setValue(total, "Marks", "Aug");
	 total= roo.getSales(9,year) + soo.getSales(9,year);
	 dataset.setValue(total, "Marks", "Sep");
	 total= roo.getSales(10,year) + soo.getSales(10,year);
	 dataset.setValue(total, "Marks", "Oct");
	 total= roo.getSales(11,year) + soo.getSales(11,year);
	 dataset.setValue(total, "Marks", "Nov");
	 total= roo.getSales(12,year) + soo.getSales(12,year);
	 dataset.setValue(total, "Marks", "Dec");
	 
	 JFreeChart chart = ChartFactory.createBarChart
	 ("Monthly Sales For 2013","Months", "Sales", dataset,PlotOrientation.VERTICAL, false,false, false);
	 chart.setBackgroundPaint(Color.white);
	 chart.getTitle().setPaint(Color.black); 
	 CategoryPlot p = chart.getCategoryPlot(); 
	 p.setRangeGridlinePaint(Color.black); 
	 ChartFrame frame1=new ChartFrame("Monthly Sales",chart);
	 frame1.setSize(600,300);
	 frame1.setVisible(true);
	 frame1.setLocationRelativeTo(null);
}
}