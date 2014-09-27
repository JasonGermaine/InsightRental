package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.MemberOperations;
import database.MovieCopyOperations;
import database.MovieOperations;
import database.SalesOrderOperations;

import model.Member;
import model.SalesOrder;

public class PurchasePaymentGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JRadioButton member, nonMember;
	private JLabel title, phone, ID, pTotal, balance, total, amount, change,
			points, space1, space3;
	private JTextField memID, phoneNum, purchaseTotal, oBalance, totalCharge,
			amountIn, changeDue, pointsAwarded, total1;
	private JButton back, finish, calc, find, search;
	private ButtonGroup options;
	private boolean admin, registered, paid;
	private SalesOrder so;
	private MemberOperations mo = new MemberOperations();
	private MovieOperations mvo = new MovieOperations();
	private MovieCopyOperations mco = new MovieCopyOperations();
	private SalesOrderOperations soo = new SalesOrderOperations();
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<Integer> qtys = new ArrayList<Integer>();
	private double tot;
	private double bal;
	private int pts;
	Member m;

	public PurchasePaymentGUI(SalesOrder so, ArrayList<Integer> ids,
			ArrayList<Integer> qtys, boolean admin)
	{
		setTitle("Purchase Payment");

		this.ids = ids;
		this.qtys = qtys;
		this.so = so;
		this.admin = admin;
		registered = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;

		// Radio Buttons
		options = new ButtonGroup();
		member = new JRadioButton("Member", true);
		member.addActionListener(this);
		nonMember = new JRadioButton("Non-Member", false);
		nonMember.addActionListener(this);
		options.add(member);
		options.add(nonMember);

		// Fonts
		Font t = new Font("Trebuchet MS", Font.BOLD, 30);
		Font f = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font sb = new Font("Trebuchet MS", Font.PLAIN, 10);

		// JLabels
		title = new JLabel("Payment for Purchase");

		ID = new JLabel("Member ID:");
		pTotal = new JLabel("Purchase Total:");
		balance = new JLabel("Balance:");
		total = new JLabel("Total:");
		amount = new JLabel("Amount In:");
		change = new JLabel("Change Due:");
		points = new JLabel("Points Awarded:");
		space1 = new JLabel(" ");
		space3 = new JLabel(" ");

		// TextFields
		memID = new JTextField(15);
		memID.setBorder(BorderFactory.createLineBorder(Color.black));

		purchaseTotal = new JTextField(15);
		purchaseTotal.setBorder(BorderFactory.createLineBorder(Color.black));
		purchaseTotal.setEditable(false);
		purchaseTotal.setText(Double.toString(so.getAmount()));

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

		pointsAwarded = new JTextField(15);
		pointsAwarded.setBorder(BorderFactory.createLineBorder(Color.black));
		pointsAwarded.setEditable(false);

		// Buttons
		search = new JButton("Search");
		search.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);
		finish = new JButton("Finish");
		finish.addActionListener(this);
		calc = new JButton("Calculate");
		calc.addActionListener(this);
		calc.setVisible(false);

		phone = new JLabel("Phone Number");
		phoneNum = new JTextField();
		phoneNum.setBorder(BorderFactory.createLineBorder(Color.black));
		phone.setVisible(false);
		phoneNum.setVisible(false);
		find = new JButton("Can't Find?");
		find.addActionListener(this);

		// Add Components
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(title, t, 0, 0, 2, 1);

		addComponent(nonMember, f, 2, 1, 1, 1);
		addComponent(member, f, 3, 1, 1, 1);

		addComponent(ID, f, 4, 1, 1, 1);
		addComponent(phone, f, 5, 1, 1, 1);
		addComponent(pTotal, f, 7, 1, 1, 1);
		addComponent(balance, f, 8, 1, 1, 1);
		addComponent(total, f, 9, 1, 1, 1);
		addComponent(amount, f, 10, 1, 1, 1);
		addComponent(change, f, 11, 1, 1, 1);
		addComponent(points, f, 12, 1, 1, 1);

		addComponent(memID, f, 4, 2, 1, 1);
		addComponent(phoneNum, f, 5, 2, 1, 1);
		addComponent(purchaseTotal, f, 7, 2, 1, 1);
		addComponent(oBalance, f, 8, 2, 1, 1);
		addComponent(totalCharge, f, 9, 2, 1, 1);
		addComponent(amountIn, f, 10, 2, 1, 1);
		addComponent(changeDue, f, 11, 2, 1, 1);
		addComponent(pointsAwarded, f, 12, 2, 1, 1);

		addComponent(find, sb, 4, 3, 1, 1);
		addComponent(calc, sb, 10, 3, 1, 1);
		addComponent(back, f, 14, 0, 1, 1);
		addComponent(finish, f, 14, 3, 1, 1);

		addComponent(space1, f, 1, 1, 2, 1);
		addComponent(search, f, 6, 2, 1, 1);
		addComponent(space3, f, 13, 1, 2, 1);

		this.setVisible(true);

	}

	// Method for adding components
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

	public void updateCopies(int soID)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			int id = ids.get(i).intValue();

			int quantity = qtys.get(i).intValue();
			for (int j = 0; j < quantity; j++)
			{
				mco.sellCopies(id, soID);
			}

			mvo.updateTrans(id);
		}
	}

	public void setFields(Member m)
	{
		if (m == null)
		{
			JOptionPane.showMessageDialog(null, "Member does not exist!");
		} else
		{
			bal = m.getBalance();
			oBalance.setText(Double.toString(bal));
			double sub = so.getAmount();
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
		}
	}

	public void addPoints(Member m)
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
		if (ae.getSource() == nonMember)
		{
			registered = false;
			search.setVisible(false);
			find.setVisible(false);
			ID.setVisible(false);
			memID.setVisible(false);
			balance.setVisible(false);
			oBalance.setVisible(false);
			points.setVisible(false);
			pointsAwarded.setVisible(false);
			phone.setVisible(false);
			phoneNum.setVisible(false);
			total.setVisible(false);
			totalCharge.setVisible(false);
			tot = so.getAmount();
			calc.setVisible(true);

		}

		if (ae.getSource() == member)
		{
			registered = true;
			find.setVisible(true);
			if (find.getText().equals("Search ID"))
			{
				find.setText("Can't Find?");
				ID.setText("Member ID:");
				phone.setVisible(false);
				phoneNum.setVisible(false);
			}
			search.setVisible(true);
			ID.setVisible(true);
			memID.setVisible(true);
			balance.setVisible(true);
			oBalance.setVisible(true);
			points.setVisible(true);
			pointsAwarded.setVisible(true);
			total.setVisible(true);
			totalCharge.setVisible(true);
			calc.setVisible(false);

		}

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
						calc.setVisible(true);
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
		if (ae.getSource() == calc)
		{
			try
			{
				double amt = Double.parseDouble(amountIn.getText());
				double change = amt - tot;
				if (change < 0)
				{
					JOptionPane.showMessageDialog(null, "Insufficient Funds!");
					paid = false;
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
		}
		if (ae.getSource() == find)
		{
			if (find.getText().equals("Can't Find?"))
			{
				find.setText("Search ID");
				ID.setText("Member Name:");
				phone.setVisible(true);
				phoneNum.setVisible(true);
			} else
			{
				find.setText("Can't Find?");
				ID.setText("Member ID:");
				phone.setVisible(false);
				phoneNum.setVisible(false);
			}
		}
		if (ae.getSource() == back)
		{
			PurchaseGUI pg = new PurchaseGUI(admin);
			this.dispose();
		}

		if (ae.getSource() == finish)
		{
				if (m != null)
				{
					if (paid == true)
					{
						int num = soo.newSale(so, m.getId());
						if (num == 0)
						{
							JOptionPane.showMessageDialog(null,
									"Transaction incomplete");
						} else
						{
							mo.updateBalance(m.getId(), bal);
							mo.updatePoints(m, pts);
							JOptionPane.showMessageDialog(null, "Sale " + num
									+ " complete");
							updateCopies(num);
							mo.updateTrans(m);
						}
						HomeGUI h = new HomeGUI(admin);
						this.dispose();
					} else
					{
						JOptionPane.showMessageDialog(null,
								"You have not paid enough!");
					}
				} else if (registered == false)
				{
					if (paid == true)
					{
						int num = soo.newSale(so, 0);
						if (num == 0)
						{
							JOptionPane.showMessageDialog(null,
									"Transaction incomplete");
						} else
						{
							JOptionPane.showMessageDialog(null, "Sale " + num
									+ " complete");
							updateCopies(num);
						}
						HomeGUI h = new HomeGUI(admin);
						this.dispose();
					} else
					{
						JOptionPane.showMessageDialog(null,
								"You have not paid enough!");
					}

				} else
				{
					JOptionPane.showMessageDialog(null,
							"Please select a member");
				}
		}
	}
}
