package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Member;

import database.MemberOperations;
import database.MovieOperations;
import database.RentalOrderOperations;
import database.SalesOrderOperations;

public class SalesReportGUI extends JFrame implements ActionListener
{

	//Global variables
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private JTextField pCount, pSales, rCount, rSales, total, best, id, transCount, points, balance;
	private JButton award, back, sales;
	private SalesOrderOperations soo = new SalesOrderOperations();
	private RentalOrderOperations roo = new RentalOrderOperations();
	private MovieOperations mvo = new MovieOperations();
	private MemberOperations mo = new MemberOperations();
	private Member m;

	
	public SalesReportGUI()
	{
		setTitle("Sales Report");
		m = mo.getBest();
		//Frame attributes
		setSize(800,600);
	  	setTitle("Sales Report");
	  	setLocationRelativeTo(null);
	  	setDefaultCloseOperation(EXIT_ON_CLOSE);
	  	
	  	layout = new GridBagLayout();
	  	setLayout(layout);
	  	gbc = new GridBagConstraints();
		
	  	//Font
	  	Font bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
	  	Font titleFont = new Font("Trebuchet MS", Font.BOLD, 30);
	  	
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		  	
		//Text fields
		pCount = new JTextField();
		pCount.setEditable(false);
		pCount.setText(Integer.toString(soo.getCount(month,year)));

		pSales = new JTextField();
		pSales.setEditable(false);
		pSales.setText(Double.toString(soo.getSales(month,year)));

		rCount = new JTextField();
		rCount.setEditable(false);
		rCount.setText(Integer.toString(soo.getCount(month,year)));

		rSales = new JTextField();
		rSales.setEditable(false);
		rSales.setText(Double.toString(roo.getSales(month,year)));
		
		total = new JTextField();
		total.setEditable(false);
		total.setText(Double.toString(roo.getSales(month,year) + soo.getSales(month,year)));
		
		best = new JTextField();
		best.setEditable(false);
		best.setText(mvo.getBest());	  	

		
		id = new JTextField();
		id.setEditable(false);

		transCount = new JTextField();
		transCount.setEditable(false);

		points = new JTextField();
		points.setEditable(false);

		balance = new JTextField();
		balance.setEditable(false);
	
		
		//Buttons
	  	award = new JButton("Award Points");
	  	award.addActionListener(this);
	  	
		back = new JButton("Back");
	  	back.addActionListener(this);
	  	
	  	sales = new JButton("View Sales For Year");
	  	sales.addActionListener(this);
	  	
	  	
	  	//Adding components
	  	addComponent(new JLabel("Sales Report"),titleFont,0,0,2,1);
	  	
		addComponent(new JLabel("Purchase Count:"),bodyFont,0,2,2,1);
	  	addComponent(pCount,bodyFont,3,2,2,1);
	  	addComponent(new JLabel("Purchase Sales:"),bodyFont,5,2,2,1);
	  	addComponent(pSales,bodyFont,7,2,2,1);
	  	
	  	addComponent(new JLabel("Rental Count:"),bodyFont,0,3,2,1);
	  	addComponent(rCount,bodyFont,3,3,2,1);
	  	addComponent(new JLabel("Rental Sales:"),bodyFont,5,3,2,1);
	  	addComponent(rSales,bodyFont,7,3,2,1);
	  	
	  	addComponent(new JLabel("Total Sales:"),bodyFont,0,4,2,1);
	  	addComponent(total,bodyFont,3,4,2,1);
		addComponent(new JLabel("Best Seller:"),bodyFont,5,4,2,1);
	  	addComponent(best,bodyFont,7,4,2,1);
	  	
		addComponent(sales,bodyFont,7,5,2,1);
	  	
	  	addComponent(new JLabel("Best Customer"),titleFont,0,6,3,1);
	  	
	  	addComponent(new JLabel("Member ID:"),bodyFont,0,7,2,1);
	  	addComponent(id,bodyFont,3,7,2,1);
	  	
	  	addComponent(new JLabel("Transaction Count:"),bodyFont,0,8,2,1);
	  	addComponent(transCount,bodyFont,3,8,2,1);
	  	
	  	addComponent(new JLabel("Loyalty Points:"),bodyFont,0,9,2,1);
	  	addComponent(points,bodyFont,3,9,2,1);
	  	addComponent(award,bodyFont,7,9,2,1);
	  
	  	addComponent(new JLabel("Balance:"),bodyFont,0,10,2,1);
	  	addComponent(balance,bodyFont,3,10,2,1);
	  	
	  	addComponent(back,bodyFont,0,11,1,1);
		
	    setDetails(m);
		
		this.setVisible(true);	//Setting the visibility
	}
	

	public void setDetails(Member m)
	{
		id.setText(Integer.toString(m.getId()));
		points.setText(Integer.toString(m.getPoints()));
		transCount.setText(Integer.toString(m.getTransCount()));
		balance.setText(Double.toString(m.getBalance()));
	}

	
	
	//Method for adding components to GridBagLayout
	public void addComponent(Component c, Font font, int gx, int gy, int gw, int gh)
	{
	   c.setFont(font);
	   
	   gbc.gridx = gx;
	   gbc.gridy = gy;
	   gbc.gridwidth = gw;
	   gbc.gridheight = gh;
	   gbc.weightx = 0;
	   gbc.weighty = 0;
	   
	   gbc.insets = new Insets(5,5,5,5);
	   
	   gbc.fill = GridBagConstraints.BOTH; //Setting constraint to fill vertically and horizontally
	   add(c, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == award)
		{
			award.setVisible(false);
			mo.updatePoints(m,100);
			m = mo.getBest();
			setDetails(m);
			JOptionPane.showMessageDialog(null, "100 Points added to member: " + m.getId());
		}
		else if(ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(true);
			this.dispose();
		}
		else
		{
			BarChart bc = new BarChart();
		}
	}

}
