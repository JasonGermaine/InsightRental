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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Member;

import database.MemberOperations;

public class ViewMemberGUI extends JFrame implements ActionListener
{
	// Global Variables
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private JTextField field1, field2, memID, fname, lname, address, dob,
			points, phone, transCount, balance;
	private JButton search, find, back, update, done, cancel;
	private JLabel field1Label, field2Label;
	private boolean admin;
	private MemberOperations mo = new MemberOperations();
	private Member m;

	public ViewMemberGUI(boolean admin) // Boolean admin parameter to determine
										// which screen to show
	{
		this.admin = admin;

		// Frame attributes
		setSize(800, 600);
		setTitle("View Member");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		layout = new GridBagLayout();
		setLayout(layout);
		gbc = new GridBagConstraints();

		// Font
		Font bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font titleFont = new Font("Trebuchet MS", Font.BOLD, 30);
		Font smallButtonFont = new Font("Trebuchet MS", Font.PLAIN, 10);

		// Labels
		field1Label = new JLabel("Member ID:");

		field2Label = new JLabel("Phone Number:");
		field2Label.setVisible(false);

		// Text fields
		field1 = new JTextField();

		field2 = new JTextField();
		field2.setVisible(false);

		memID = new JTextField();
		memID.setEditable(false);

		fname = new JTextField();
		fname.setEditable(false);

		lname = new JTextField();
		lname.setEditable(false);

		address = new JTextField();
		address.setEditable(false);

		dob = new JTextField();
		dob.setEditable(false);

		points = new JTextField();
		points.setEditable(false);

		phone = new JTextField();
		phone.setEditable(false);

		transCount = new JTextField();
		transCount.setEditable(false);

		balance = new JTextField();
		balance.setEditable(false);

		find = new JButton("Can't Find?");
		find.addActionListener(this);

		// Buttons
		search = new JButton("Search");
		search.addActionListener(this);

		update = new JButton("Update");
		update.addActionListener(this);
		update.setVisible(false);

		done = new JButton("Done");
		done.addActionListener(this);
		done.setVisible(false);

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setVisible(false);

		back = new JButton("Back");
		back.addActionListener(this);

		// Adding components
		gbc.fill = GridBagConstraints.BOTH; // Setting constraint to fill
											// vertically and horizontally

		addComponent(new JLabel("Member Account"), titleFont, 0, 0, 2, 1);

		addComponent(field1Label, bodyFont, 0, 1, 1, 1);
		addComponent(field1, bodyFont, 2, 1, 1, 1);
		addComponent(find, smallButtonFont, 3, 1, 1, 1);

		addComponent(field2Label, bodyFont, 0, 2, 1, 1);
		addComponent(field2, bodyFont, 2, 2, 1, 1);

		addComponent(search, bodyFont, 2, 3, 1, 1);

		addComponent(new JLabel("Member ID:"), bodyFont, 0, 4, 1, 1);
		addComponent(memID, bodyFont, 1, 4, 1, 1);

		addComponent(new JLabel("First Name:"), bodyFont, 0, 5, 1, 1);
		addComponent(fname, bodyFont, 1, 5, 1, 1);

		addComponent(new JLabel("Last Name:"), bodyFont, 0, 6, 1, 1);
		addComponent(lname, bodyFont, 1, 6, 1, 1);

		addComponent(new JLabel("Address:"), bodyFont, 0, 7, 1, 1);
		addComponent(address, bodyFont, 1, 7, 3, 1);

		addComponent(new JLabel("Date Of Birth:"), bodyFont, 0, 8, 1, 1);
		addComponent(dob, bodyFont, 1, 8, 1, 1);

		addComponent(new JLabel("Award Points"), bodyFont, 2, 8, 1, 1);
		addComponent(points, bodyFont, 3, 8, 1, 1);

		addComponent(new JLabel("Phone Number:"), bodyFont, 0, 9, 1, 1);
		addComponent(phone, bodyFont, 1, 9, 1, 1);

		addComponent(new JLabel("Transaction Count"), bodyFont, 2, 9, 1, 1);
		addComponent(transCount, bodyFont, 3, 9, 1, 1);

		addComponent(new JLabel("Balance:"), bodyFont, 2, 10, 1, 1);
		addComponent(balance, bodyFont, 3, 10, 1, 1);

		addComponent(back, bodyFont, 0, 11, 1, 1);
		addComponent(cancel, bodyFont, 0, 11, 1, 1);
		addComponent(update, bodyFont, 3, 11, 1, 1);
		addComponent(done, bodyFont, 3, 11, 1, 1);

		this.setVisible(true); // Setting the visibility

	}

	// Method to add a component to GridBagLayout
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

		gbc.fill = GridBagConstraints.BOTH; // Setting constraint to fill
											// vertically and horizontally
		add(c, gbc);
	}

	public void setDetails(Member m)
	{
		if (m == null)
		{
			JOptionPane.showMessageDialog(null, "Member does not exist!");
		} else
		{
			update.setVisible(true);
			memID.setText(Integer.toString(m.getId()));
			fname.setText(m.getFname());
			lname.setText(m.getLname());
			dob.setText(m.getDob());
			phone.setText(m.getPhone());
			points.setText(Integer.toString(m.getPoints()));
			transCount.setText(Integer.toString(m.getTransCount()));
			address.setText(m.getAddress());
			balance.setText(Double.toString(m.getBalance()));
		}
	}

	public boolean validate(String str)
	{
		for (char c : str.toCharArray())
		{
			if (Character.isDigit(c))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == search)
		{
			if (field1Label.getText().equalsIgnoreCase(("Member ID:")))
			{
				try
				{
					int id = Integer.parseInt(field1.getText());
					if (id == 0)
					{
						JOptionPane.showMessageDialog(null,
								"Member 0 does not exist");
					} else
					{
						m = mo.searchID(id);
						setDetails(m);
					}

				} catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null,
							"Please enter a number ID");
				}
			} else
			{
				String lname = field1.getText();
				String pNum = field2.getText();
				if (!lname.equals(""))
				{
					if (!pNum.equals(""))
					{
						m = mo.findMember(field1.getText(), field2.getText());
						setDetails(m);
					} else
					{
						JOptionPane.showMessageDialog(null,
								"Please enter a number");
					}
				} else
				{
					JOptionPane.showMessageDialog(null,
							"Please enter last name");
				}

			}
		} else if (ae.getSource() == find)
		{
			if (find.getText().equals("Can't Find?"))
			{
				find.setText("Search ID");
				field1Label.setText("Member Name:");
				field2Label.setVisible(true);
				field2.setVisible(true);
			} else
			{
				find.setText("Can't Find?");
				field1Label.setText("Member ID:");
				field2Label.setVisible(false);
				field2.setVisible(false);
			}
		} else if (ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(admin);
			this.dispose();
		} else if (ae.getSource() == update)
		{
			if (!memID.getText().equals(""))
			{
				address.setText("");
				address.setEditable(true);
				phone.setText("");
				phone.setEditable(true);
				update.setVisible(false);
				done.setVisible(true);
				back.setVisible(false);
				cancel.setVisible(true);
			}
		} else if (ae.getSource() == done)
		{
			int id = m.getId();

			String add = address.getText();
			String ph = phone.getText();
			boolean value = validate(ph);

			if (add.equals("") || ph.equals(""))
			{
				JOptionPane.showMessageDialog(null,
						"Please enter in both fields");
			} else if (value == false)
			{

				JOptionPane.showMessageDialog(null,
						"Please enter a valid number");

			} else
			{
				mo.updateDetails(id, add, ph);
				m.setAddress(add);
				m.setPhone(ph);
				done.setVisible(false);
				update.setVisible(true);
				cancel.setVisible(false);
				back.setVisible(true);
				address.setEditable(false);
				phone.setEditable(false);
			}

		} else if (ae.getSource() == cancel)
		{
			address.setText(m.getAddress());
			phone.setText(m.getPhone());
			done.setVisible(false);
			update.setVisible(true);
			cancel.setVisible(false);
			back.setVisible(true);
			address.setEditable(false);
			phone.setEditable(false);
		}

	}

}
