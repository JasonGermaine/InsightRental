package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.RentalOrder;

import database.MemberOperations;
import database.MovieCopyOperations;
import database.MovieOperations;
import database.RentalOrderOperations;

public class RentalPaymentGUI extends JFrame implements ActionListener
{
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JLabel title, ID, rTotal, balance, total, amount, change, returnD,
			points, space1, space2, space3, phone;
	private JTextField memID, rentalTotal, oBalance, totalCharge, amountIn,
			changeDue, returnDate, pointsAwarded, phoneNum;
	private JButton search, back, finish, calc, find;
	private boolean admin, paid, rental;
	private double tot;
	private double bal;
	private int pts;
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<Integer> qtys = new ArrayList<Integer>();
	model.Member m;
	private MemberOperations mo = new MemberOperations();
	private MovieOperations mvo = new MovieOperations();
	private RentalOrderOperations roo = new RentalOrderOperations();
	private RentalOrder ro;
	private MovieCopyOperations mco = new MovieCopyOperations();

	public RentalPaymentGUI(RentalOrder ro, ArrayList<Integer> ids,
			ArrayList<Integer> qtys, boolean admin)
	{
		setTitle("Rental Payment");

		this.ro = ro;
		this.ids = ids;
		this.qtys = qtys;
		this.admin = admin;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		// Fonts
		Font t = new Font("Trebuchet MS", Font.BOLD, 30);
		Font f = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font sb = new Font("Trebuchet MS", Font.PLAIN, 10);

		// JLabels
		title = new JLabel("Payment for Rental");
		phone = new JLabel("Phone Number:");
		ID = new JLabel("Member ID:");
		rTotal = new JLabel("Rental Total:");
		balance = new JLabel("Balance:");
		total = new JLabel("Total:");
		amount = new JLabel("Amount In:");
		change = new JLabel("Change Due:");
		returnD = new JLabel("Return Date:");
		points = new JLabel("Points Awarded:");
		space1 = new JLabel(" ");
		space2 = new JLabel(" ");
		space3 = new JLabel(" ");

		// TextFields
		memID = new JTextField(15);
		phoneNum = new JTextField();

		memID.setBorder(BorderFactory.createLineBorder(Color.black));

		rentalTotal = new JTextField(15);
		rentalTotal.setBorder(BorderFactory.createLineBorder(Color.black));
		rentalTotal.setEditable(false);
		rentalTotal.setText(Double.toString(ro.getAmount()));

		oBalance = new JTextField(15);
		oBalance.setBorder(BorderFactory.createLineBorder(Color.black));
		oBalance.setEditable(false);

		totalCharge = new JTextField(15);
		totalCharge.setBorder(BorderFactory.createLineBorder(Color.black));
		totalCharge.setEditable(false);

		amountIn = new JTextField(15);
		amountIn.setBorder(BorderFactory.createLineBorder(Color.black));

		changeDue = new JTextField(15);
		changeDue.setBorder(BorderFactory.createLineBorder(Color.black));
		changeDue.setEditable(false);

		returnDate = new JTextField(15);
		returnDate.setBorder(BorderFactory.createLineBorder(Color.black));
		returnDate.setEditable(false);
		returnDate.setText(ro.getReturnDate());

		pointsAwarded = new JTextField(15);
		pointsAwarded.setBorder(BorderFactory.createLineBorder(Color.black));
		pointsAwarded.setEditable(false);

		// Buttons
		back = new JButton("Back");
		back.addActionListener(this);
		finish = new JButton("Finish");
		finish.addActionListener(this);
		calc = new JButton("Calculate");
		calc.addActionListener(this);
		calc.setVisible(false);
		search = new JButton("SEARCH");
		find = new JButton("SEARCH NAME");
		search.addActionListener(this);
		find.addActionListener(this);
		// Add Components
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(title, t, 0, 0, 2, 1);

		addComponent(ID, f, 1, 1, 1, 1);
		addComponent(search, f, 3, 2, 1, 1);
		addComponent(phone, f, 2, 1, 1, 1);
		addComponent(phoneNum, f, 2, 2, 1, 1);
		addComponent(find, sb, 1, 3, 1, 1);
		addComponent(rTotal, f, 4, 1, 1, 1);
		addComponent(balance, f, 5, 1, 1, 1);
		addComponent(total, f, 6, 1, 1, 1);
		addComponent(amount, f, 7, 1, 1, 1);
		addComponent(change, f, 8, 1, 1, 1);
		addComponent(returnD, f, 9, 1, 1, 1);
		addComponent(points, f, 10, 1, 1, 1);

		addComponent(memID, f, 1, 2, 1, 1);
		addComponent(rentalTotal, f, 4, 2, 1, 1);
		addComponent(oBalance, f, 5, 2, 1, 1);
		addComponent(totalCharge, f, 6, 2, 1, 1);
		addComponent(amountIn, f, 7, 2, 1, 1);
		addComponent(changeDue, f, 8, 2, 1, 1);
		addComponent(returnDate, f, 9, 2, 1, 1);
		addComponent(pointsAwarded, f, 10, 2, 1, 1);

		addComponent(calc, sb, 7, 3, 1, 1);
		addComponent(back, f, 12, 0, 1, 1);
		addComponent(finish, f, 12, 3, 1, 1);

		// Spacing
		addComponent(space1, f, 1, 1, 2, 1);
		addComponent(space2, f, 3, 1, 2, 1);
		addComponent(space3, f, 11, 1, 2, 1);

		this.setVisible(true);
		phone.setVisible(false);
		phoneNum.setVisible(false);
	}

	public void addComponent(Component c, Font f, int row, int column,
			int width, int height)
	{
		c.setFont(f);
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;

		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5, 5, 5, 5);

		layout.setConstraints(c, constraints);
		add(c);
	}

	public void updateCopies(int roID)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			int id = ids.get(i).intValue();
			int quantity = qtys.get(i).intValue();
			for (int j = 0; j < quantity; j++)
			{
				mco.rentCopies(id, roID);
			}

			mvo.updateTrans(id);
		}
	}

	public void setFields(model.Member m)
	{
		if (m == null)
		{
			JOptionPane.showMessageDialog(null, "Member does not exist!");
		} else
		{
			calc.setVisible(true);
			if (roo.checkRental(m.getId()) == false)
			{
				bal = m.getBalance();
				oBalance.setText(Double.toString(bal));
				double sub = ro.getAmount();
				if (bal > sub)
				{
					bal = bal - sub;
					tot = 0;
				} else
				{
					tot = sub - bal;
					bal = 0;
				}
				totalCharge.setText(Double.toString(tot));
				addPoints(m);
				rental = false;
			} else
			{
				JOptionPane.showMessageDialog(null,
						"Member currently has a rental out!");
				rental = true;
			}
		}
	}

	public void addPoints(model.Member m2)
	{
		if (tot > 50)
		{
			pts = 100;
		} else
		{
			pts = 50;
		}
		pointsAwarded.setText(Integer.toString(pts));
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == search)
		{
			if (ID.getText().equals("Member ID:"))
			{
				try
				{
					int id = Integer.parseInt(memID.getText());
					if (id == 0)
					{
						JOptionPane.showMessageDialog(null,
								"Member 0 does not exist");
					} else
					{
						m = mo.searchID(id);
						setFields(m);
					}
				} catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null,
							"Please enter a valid id");
				}

			} else
			{
				String name = memID.getText();
				String number = phoneNum.getText();
				if (!name.equals(""))
				{
					if (!number.equals(""))
					{
						m = mo.findMember(memID.getText(), phoneNum.getText());
						setFields(m);
					} else
					{
						JOptionPane.showMessageDialog(null,
								"Please enter phone number");
					}
				} else
				{
					JOptionPane.showMessageDialog(null,
							"Please enter last name");
				}
			}
		}

		else if (ae.getSource() == find)
		{
			if (find.getText().equals("SEARCH NAME"))
			{
				find.setText("Search ID");
				ID.setText("Member Name:");
				phone.setVisible(true);
				phoneNum.setVisible(true);
			} else
			{
				find.setText("SEARCH NAME");
				ID.setText("Member ID:");
				phone.setVisible(false);
				phoneNum.setVisible(false);
			}

		} else if (ae.getSource() == calc)
		{
			try
			{
				double amt = Double.parseDouble(amountIn.getText());
				double change = amt - tot;
				if (change < 0)
				{
					JOptionPane.showMessageDialog(null, "Insufficient Funds!");
				} else
				{
					changeDue.setText(Double.toString(change));
					paid = true;
				}

			} catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null,
						"Please enter a valid amount in");
			}

		} else if (ae.getSource() == back)
		{
			RentalGUI rg = new RentalGUI(admin);
			this.dispose();
		}

		else if (ae.getSource() == finish)
		{
			if (m != null)
			{
				if (rental == false)
				{
					if (paid == true)
					{
						if (!amountIn.getText().equals(""))
						{
							int num = roo.newRental(ro, m.getId());
							if (num == 0)
							{
								JOptionPane.showMessageDialog(null,
										"Transaction incomplete");
							} else
							{
								mo.updateBalance(m.getId(), bal);
								mo.updatePoints(m, pts);
								JOptionPane.showMessageDialog(null, "Rental "
										+ num + " complete");
								updateCopies(num);
								mo.updateTrans(m);
							}
							HomeGUI h = new HomeGUI(admin);
							this.dispose();
						} else
						{
							JOptionPane.showMessageDialog(null,"Please Enter Amount");
						}
					} else
					{
						JOptionPane.showMessageDialog(null,
								"You have not paid enough!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,
							"Members are only allowed one rental at a time!");
				}
			}

			else
			{
				JOptionPane.showMessageDialog(null, "Please select a member");
			}
		}

	}

}