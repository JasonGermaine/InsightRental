package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import database.MovieCopyOperations;

public class HomeGUI extends JFrame implements ActionListener
{
	//Global Variables
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private JButton logout, register, display, purchase, rental, returnB, sales, stock, add, remove, movie;
	private JLabel user;
	private JTextArea message;
	private boolean admin;
	private MovieCopyOperations mco = new MovieCopyOperations();
	
	
	public HomeGUI(boolean admin) // Boolean admin parameter to determine which screen to show
	{
		this.admin = admin;
		
		//Frame attributes
		
		setSize(800,600);
	  	setTitle("Home");
	  	setLocationRelativeTo(null);
	  	setDefaultCloseOperation(EXIT_ON_CLOSE);
	  	
	  	layout = new GridBagLayout();
	  	setLayout(layout);
	  	gbc = new GridBagConstraints();
	  	
	  	
	  	//Fonts
	  	
	  	Font bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
	  	Font titleFont = new Font("Trebuchet MS", Font.BOLD, 30);
	  	
	  	//Labels
	  	user = new JLabel("Sales Staff");
	  	
	  	//Buttons
	  	movie = new JButton("View Movies");
	  	movie.addActionListener(this);
	  	
	  	register = new JButton("Register Member");
	  	register.addActionListener(this);
	  	
		display = new JButton("Display Member Account");
	  	display.addActionListener(this);
	  	
		purchase = new JButton("Movie Purchase");
	  	purchase.addActionListener(this);
	  
		rental = new JButton("Movie Rental");
	  	rental.addActionListener(this);
	  		
		returnB = new JButton("Rental Return");
	  	returnB.addActionListener(this);
	  	
		logout = new JButton("Log Out");
	  	logout.addActionListener(this);
	  	
	  	//Adding Components
	  	addComponent(user,titleFont,0,0,1,1);
	  	
	  	addComponent(movie,bodyFont,2,1,2,1);
	  	addComponent(register,bodyFont,2,2,2,1);
	  	addComponent(display,bodyFont,2,3,2,1);
		addComponent(purchase,bodyFont,2,4,2,1);
	  	addComponent(rental,bodyFont,2,5,2,1);
	  	addComponent(returnB,bodyFont,2,6,2,1);
	  	addComponent(logout,bodyFont,0,11,1,1);
	  	
	  	if(admin == true)	//Check if user is an admin
	  	{
	  		//Admin components
	  		
	  		//Buttons
			sales = new JButton("View Sales Report");
		  	sales.addActionListener(this);

		  	stock = new JButton("Add Stock");
		  	stock.addActionListener(this);
		  	
			add = new JButton("Add Staff");
		  	add.addActionListener(this);
		  	
			remove = new JButton("Remove Staff");
		  	remove.addActionListener(this);
		  			  	
		  	//Text Area
		  	
		  	//Displays products with stock level less than 10
		  	message= new JTextArea();
		  	message.setEditable(false);
		  	
		  	message.setText(mco.checkStock()); //Checking Stock Levels
		  	
		  	//Adding components 
		  	addComponent(sales,bodyFont,2,7,2,1);
		  	addComponent(stock,bodyFont,2,8,2,1);
		  	addComponent(add,bodyFont,2,9,2,1);
		  	addComponent(remove,bodyFont,2,10,2,1);
		  	addComponent(new JLabel("The following product stock levels are low:"),bodyFont, 0,4,2,1);
		  	addComponent(message,bodyFont, 0,5,2,4);
		  	
		  	user.setText("Admin"); //Setting title label
	  	}
	  	
	  	
	  	this.setVisible(true); //Setting the visibility
	  	
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
	   
	   //Setting separate insets
	   
	   if(admin == true) 	//For admin home screen
	   {
		   gbc.insets = new Insets(5,5,5,5);   
	   }
	   else		//For sales staff home screen
	   {
		   gbc.insets = new Insets(30,150,5,150);
	   }
	   
	   gbc.fill = GridBagConstraints.BOTH; //Setting constraint to fill vertically and horizontally
	   add(c, gbc);
	}


	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == logout )
		{
			LoginGUI l = new LoginGUI();
			this.dispose();
		}
		else if(ae.getSource() == display)
		{
			ViewMemberGUI vm =new ViewMemberGUI(admin);
			this.dispose();
		}
		else if(ae.getSource() == sales)
		{
			SalesReportGUI sr = new SalesReportGUI();
			this.dispose();
		}
		else if(ae.getSource() == purchase)
		{
			PurchaseGUI p = new PurchaseGUI(admin);
			this.dispose();
		}
		else if(ae.getSource()== rental)
		{
			RentalGUI rl = new RentalGUI(admin);
			this.dispose();
		}
		else if(ae.getSource()== returnB)
		{
			ReturnGUI ru = new ReturnGUI(admin);
			this.dispose();
		}
		else if(ae.getSource()== remove)
		{
			RemoveStaffGUI rs = new RemoveStaffGUI();
			this.dispose();
		}
		else if(ae.getSource() == stock)
		{
			AddStockGUI st = new AddStockGUI();
			this.dispose();
		}
		else if(ae.getSource() == add)
		{
			AddStaffGUI sf = new AddStaffGUI();
			this.dispose();
		}
		else if(ae.getSource() == register)
		{
			AddMemberGUI am = new AddMemberGUI();
			this.dispose();
		}
		else if(ae.getSource() == movie)
		{
			MovieScroll ms = new MovieScroll(admin);
			this.dispose();
		}
	}

}
