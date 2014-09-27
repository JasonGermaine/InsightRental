package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.UserOperations;

import model.Admin;
import model.SaleStaff;

public class AddStaffGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private Font bodyFont, titleFont;
	private JTextField sType, fName, lName, addLine1, addLine2, dob, phoneNum;
	private JButton add, back;
	private JLabel title;
	private JRadioButton sale, admin;
	private JComboBox county, day, month;
	private String[] days =
	{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
			"25", "26", "27", "28", "29", "30", "31" };
	private String[] months =
	{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	private String[] counties =
	{ "Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry",
			"Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry",
			"Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford",
			"Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon",
			"Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath",
			"Wexford", "Wicklow" };
	private ButtonGroup options;
	private UserOperations uo = new UserOperations();

	public AddStaffGUI()
	{
		setSize(800, 600);
		setTitle("Add Staff");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		layout = new GridBagLayout();
		setLayout(layout);
		gbc = new GridBagConstraints();

		bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
		titleFont = new Font("Trebuchet MS", Font.BOLD, 30);

		title = new JLabel("Add Staff");
		addComponent(title, titleFont, 0, 0, 2, 1);

		addComponent(new JLabel("Staff Type:"), bodyFont, 0, 1, 1, 1);
		sale = new JRadioButton("Sale Staff", true);
		admin = new JRadioButton("Admin", false);

		options = new ButtonGroup();
		options.add(sale);
		options.add(admin);

		sale.addActionListener(this);
		admin.addActionListener(this);
		addComponent(sale, bodyFont, 3, 1, 1, 1);
		addComponent(admin, bodyFont, 4, 1, 1, 1);

		addComponent(new JLabel("First Name:"), bodyFont, 0, 2, 1, 1);
		fName = new JTextField();
		addComponent(fName, bodyFont, 2, 2, 3, 1);

		addComponent(new JLabel("Last Name:"), bodyFont, 0, 3, 1, 1);
		lName = new JTextField();
		addComponent(lName, bodyFont, 2, 3, 3, 1);

		addComponent(new JLabel("Address Line 1:"), bodyFont, 0, 4, 1, 1);
		addLine1 = new JTextField();
		addComponent(addLine1, bodyFont, 2, 4, 3, 1);

		addComponent(new JLabel("Address Line 2:"), bodyFont, 0, 5, 1, 1);
		addLine2 = new JTextField();
		addComponent(addLine2, bodyFont, 2, 5, 3, 1);

		addComponent(new JLabel("County:"), bodyFont, 0, 6, 1, 1);
		county = new JComboBox(counties);
		county.setSelectedIndex(9);
		county.addActionListener(this);
		addComponent(county, bodyFont, 2, 6, 3, 1);

		addComponent(new JLabel("Date of Birth:"), bodyFont, 0, 7, 1, 1);
		day = new JComboBox(days);
		day.setSelectedIndex(0);
		day.addActionListener(this);
		addComponent(day, bodyFont, 2, 7, 1, 1);

		month = new JComboBox(months);
		month.setSelectedIndex(0);
		month.addActionListener(this);
		addComponent(month, bodyFont, 3, 7, 1, 1);

		dob = new JTextField(5);
		addComponent(dob, bodyFont, 4, 7, 1, 1);

		addComponent(new JLabel("Phone Number:"), bodyFont, 0, 8, 1, 1);
		phoneNum = new JTextField();
		addComponent(phoneNum, bodyFont, 2, 8, 3, 1);

		add = new JButton("Register");
		add.addActionListener(this);
		addComponent(add, bodyFont, 2, 9, 3, 1);

		back = new JButton("Back");
		back.addActionListener(this);
		addComponent(back, bodyFont, 0, 9, 1, 1);

		this.setVisible(true);
	}

	public void addComponent(Component c, Font font, int gx, int gy, int gw,
			int gh)
	{
		c.setFont(font);
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		add(c, gbc);
	}

	public void addStaff()
	{
		try
		{
			String fname = fName.getText();
			String lname = lName.getText();
			String add1 = addLine1.getText();
			String add2 = addLine2.getText();
			String address = (add1 + ", " + add2 + ", " + (String) county
					.getSelectedItem());
			String phone = phoneNum.getText();
			String dayString = (String) day.getSelectedItem();
			String monthString = (String) month.getSelectedItem();
			int year = Integer.parseInt(dob.getText());

			String date = (monthString + dayString + year);

			if (!fname.equals(""))
			{
				if (!lname.equals(""))
				{
					if (!add1.equals("") || !add2.equals(""))
					{
						if (!phone.equals(""))
						{
							String password = JOptionPane.showInputDialog(null,"Please enter a password for " + fname+ " " + lname + ".");
							if (password != null)
							{
								int id;
								if (sale.isSelected())
								{
									SaleStaff ss = new SaleStaff(fname, lname,date, address, phone, password);
									id = uo.addStaffUser(ss);
								} else
								{
									Admin a = new Admin(fname, lname, date,address, phone, password);
									id = uo.addAdminUser(a);
								}

								if (id == 0)
								{
									JOptionPane.showMessageDialog(null,"User already exists");
								} else
								{
									JOptionPane
											.showMessageDialog(null,"User "+ id	+ " has been added to the database");
								}
								
								fName.setText("");
								lName.setText("");
								addLine1.setText("");
								addLine2.setText("");
								county.setSelectedIndex(9);
								phoneNum.setText("");
								day.setSelectedIndex(0);
								month.setSelectedIndex(0);
								dob.setText("");
							}
						} else
						{
							JOptionPane.showMessageDialog(null,	"Please enter a phone number");
						}
					} else
					{
						JOptionPane.showMessageDialog(null,"Please enter an address");
					}
				} else
				{
					JOptionPane.showMessageDialog(null,	"Please enter last name");
				}
			} else
			{
				JOptionPane.showMessageDialog(null, "Please enter first name");
			}
		} catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Please enter a valid year");
		}
	}
	
	public boolean validate(String str)
	{
		for(char c : str.toCharArray())
		{
			if(Character.isDigit(c))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == add)
		{
			String fn = fName.getText();
			String ln = lName.getText();
			boolean value1 = validate(fn);
			boolean value2 = validate(ln);
			
			if(value1 == false && value2 == false)
			{
				String ph = phoneNum.getText();
				value1 = validate(ph);
				if(value1 == true)
				{
					addStaff();			
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid phone number");	
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Invalid name");	
			}
		} else if (ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(true);
			this.dispose();
		}

	}

}
