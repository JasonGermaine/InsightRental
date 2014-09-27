package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import database.SaleStaffOperations;
import database.UserOperations;

public class LoginGUI extends JFrame implements ActionListener
{
	
	//Global Variables
	private JTextField uName;
	private JLabel header;
	private JPasswordField pWord;
	private JButton enter, cancel;
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private boolean admin;
	private UserOperations uo = new UserOperations();
	private SaleStaffOperations sso = new SaleStaffOperations();
	
	public LoginGUI()
	{
		
		//Frame attributes
		setSize(800,600);
	  	setTitle("Login");
	  	setLocationRelativeTo(null);
	  	setDefaultCloseOperation(EXIT_ON_CLOSE);
	  	
	  	layout = new GridBagLayout();
	  	setLayout(layout);
	  	gbc = new GridBagConstraints();
	  	
	  	//Fonts
	  	Font headerFont = new Font("lithograph", Font.BOLD, 30);
	  	Font bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
	  	
	  	
	  	//Header
	  	header = new JLabel("Insight DVD Rental System");
	  	
	  	//Username text field
	 	uName = new JTextField();
	  	
	  	
	  	//Password Line
	  
	 	pWord = new JPasswordField();
	  	
	  	
	  	//Buttons
	 	
		enter = new JButton("Login");
		enter.addActionListener(this);
	 	enter.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
	 	AbstractAction action = new AbstractAction()
	 	  {                                        
	 		   @Override                                  
	 		   public void actionPerformed(ActionEvent e)                                   
	 		   {                                          
	 			   enter = (JButton)e.getSource();                                                                                
	 			   Login();  
	 			  }           
	 	};   
	 	enter.getActionMap().put("Enter", action);
	 	enter.addActionListener(action);
	  	
	  	
	  	cancel = new JButton("Cancel");
	  	cancel.addActionListener(this);
	  	
	  	//Adding Components
	  	addComponent(header,headerFont, 0,0,4,1);
	  	addComponent(new JLabel("User ID:"),bodyFont,0,1,1,1);
	  	addComponent(uName,bodyFont,1,1,3,1);
		addComponent(new JLabel("Password:"),bodyFont,0,2,1,1);
	  	addComponent(pWord,bodyFont,1,2,3,1);
	  	addComponent(enter,bodyFont,1,3,1,1);
	  	addComponent(cancel,bodyFont,2,3,1,1);

	  	this.setVisible(true); //Sets visibility when object is created
	}
	
	//Method to add a component to GridBagLayout
	public void addComponent(Component c, Font font, int gx, int gy, int gw, int gh)
			{
				c.setFont(font);
			   gbc.gridx = gx;
			   gbc.gridy = gy;
			   gbc.gridwidth = gw;
			   gbc.gridheight = gh;
			   gbc.weightx = 0;
			   gbc.weighty = 0;
			   
			   if(c == header)
			   {
				   gbc.insets = new Insets(5,5,50,5); 
			   }
			   else if(c == enter)
			   {
				   gbc.insets = new Insets(5,60,5,5); 
			   }
			   else
			   {
				   gbc.insets = new Insets(5,5,5,5); 
			   }
				   
			   

			  	
			   gbc.fill = GridBagConstraints.BOTH;		//Setting constraint to fill vertically and horizontally
			   add(c, gbc);
			}
	
	
	public void Login()
	{
			if(!uName.getText().equals("") || pWord.getPassword().length != 0)
			{
				try
				{
					int userId = Integer.parseInt(uName.getText());
					char[] list = pWord.getPassword();
					String pword = new String(list);
					
					boolean exist = uo.login(userId, pword);

					if(exist == true)
					{
						admin = sso.findSaleStaff(userId);
						HomeGUI h = new HomeGUI(admin);
						this.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Invalid username/password");
						pWord.setText("");
						uName.setText("");
					}
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null, "Please enter a number ID");
				}
				
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please fill in both fields");
			}
		}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == cancel)
		{
			pWord.setText("");
			uName.setText("");
		}
	}
	
	public static void main(String[] args)
	{
		LoginGUI gui = new LoginGUI();
	}
}