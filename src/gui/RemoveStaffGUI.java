package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.UserOperations;

public class RemoveStaffGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	private JLabel title, ID;
	private JTextField staffID;
	private JButton remove, back;
	private UserOperations uo = new UserOperations();
	
	public RemoveStaffGUI()
	{
		setTitle("Remove Staff");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
		
		Font t = new Font("Trebuchet MS",Font.BOLD, 30);
		Font f = new Font("Trebuchet MS", Font.PLAIN, 18);
		
		title = new JLabel("Remove Staff");
		
		ID = new JLabel("Staff ID:");
				
		staffID = new JTextField(15);
		staffID.setBorder(BorderFactory.createLineBorder(Color.black));
		
		remove = new JButton("Remove");
		remove.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);
		
		addComp(title, t, 0, 0, 2, 1);
		
		addComponent(ID, f, 1, 1, 1, 1);
		addComponent(staffID, f, 1, 2, 1, 1);
		
		addComp(remove, f, 2, 3, 1, 1);
		addComponent(back, f, 3, 0, 1, 1);
		
		this.setVisible(true);
	}
	
	public void addComponent(Component c,Font f, int row, int column, int width, int height)
	{
		c.setFont(f);
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5,5,5,5);
		
		layout.setConstraints(c, constraints);
		add(c);
	}
	
	public void addComp(Component c,Font f, int row, int column, int width, int height)
	{
		c.setFont(f);
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5,5,170,70);
		
		layout.setConstraints(c, constraints);
		add(c);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == remove)
		{
			try
			{
			int id = Integer.parseInt(staffID.getText());
			uo.findUser(id);
			staffID.setText("");
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null,	"Please enter a valid id");
			}
			
		}
		if(ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(true);
			this.dispose();
		}	
		
	}
}
