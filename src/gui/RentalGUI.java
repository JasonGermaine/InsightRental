package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import database.MovieCopyOperations;
import database.MovieOperations;

import model.RentalOrder;

public class RentalGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private boolean admin, added;

	private JTextField productId1, qty1, subtotal1,
			total1, nights1;
	private JButton add, payment, back, find;
	private JLabel title, productId, qty, nights, subtotal, total, order;
	private JTextArea basket;
	private MovieOperations mo = new MovieOperations();
	private MovieCopyOperations mco = new MovieCopyOperations();
	private double t;
	private int levels[];
	private int num;
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<Integer> qtys = new ArrayList<Integer>();

	public RentalGUI(boolean admin)
	{
		this.admin = admin;

		setTitle("Rental");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		// Variables
		t = 0; // total price
		num = mo.getCount();
		levels = new int[num];

		for (int i = 0; i < num; i++)
		{
			levels[i] = mco.stockEnquiry(i + 1);
		}

		// fonts
		Font t = new Font("Trebuchet MS", Font.BOLD, 30);
		Font f = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font smallButtonFont = new Font("Trebuchet MS", Font.PLAIN, 10);

		title = new JLabel("RENTAL PURCHASE");

		productId = new JLabel("PRODUCT ID");
		productId1 = new JTextField();

		qty = new JLabel("QUANTITY");
		qty1 = new JTextField();

		nights = new JLabel("NIGHTS");
		nights1 = new JTextField();

		add = new JButton("ADD");
		add.addActionListener(this);

		total = new JLabel("TOTAL");
		total1 = new JTextField();
		total1.setEditable(false);

		subtotal = new JLabel("SUBTOTAL");
		subtotal1 = new JTextField();
		subtotal1.setEditable(false);

		order = new JLabel("BASKET");
		basket = new JTextArea();
		basket.setEditable(false);
		basket.setBorder(BorderFactory.createLineBorder(Color.black));
		basket.setText("Title:\tQuantity:\tPrice:\tNights:\n");

		back = new JButton("BACK");
		back.addActionListener(this);

		payment = new JButton("PAYMENT");
		payment.addActionListener(this);

		find = new JButton("Can't Find?");
		find.addActionListener(this);

		// title etc.
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(title, t, 0, 0, 2, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(productId, f, 2, 1, 2, 1);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(qty, f, 2, 2, 1, 1);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		addComponent(subtotal, f, 2, 5, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(productId1, f, 3, 1, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(qty1, f, 3, 2, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(nights1, f, 1, 2, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(nights, f, 1, 1, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(add, f, 3, 4, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(subtotal1, f, 3, 5, 1, 1);

		addComponent(order, f, 4, 1, 1, 1);
		addComponent(basket, f, 5, 1, 5, 2);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(total, f, 7, 4, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(total1, f, 7, 5, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(back, f, 8, 1, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(payment, f, 8, 5, 1, 1);

		this.setVisible(true);
	}

	private void addComponent(Component component, Font f, int row, int column,
			int width, int height)
	{
		component.setFont(f);
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout.setConstraints(component, constraints);
		add(component);

		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5, 5, 5, 5);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == add)
		{
			try
			{
				int id = Integer.parseInt(productId1.getText());
				try
				{
					int days = Integer.parseInt(nights1.getText());
					nights1.setEditable(false);
					try
					{
						int qty = Integer.parseInt(qty1.getText());
						if (mco.stockEnquiry(id) != -1)
						{
							double sub = (mco.getPrice(id) * qty) / 4 * days;
							for (int i = 0; i < num; i++)
							{
								if (i == id - 1)
								{
									if (levels[i] != 0)
									{
										if (levels[i] - qty >= 0)
										{
											levels[i] = levels[i] - qty;
											t = t + sub;
											basket.append(mo.getName(id) + "\t"
													+ qty + "\t€" + sub + "\t"
													+ days + "\n");
											subtotal1.setText("€" + sub);
											total1.setText("€" + t);
											ids.add(id);
											qtys.add(qty);

											productId1.setText("");
											qty1.setText("");
											added = true;
										} else
										{
											JOptionPane.showMessageDialog(null,
													"Stock level too low");
										}
									} else
									{
										JOptionPane
												.showMessageDialog(null,
														"We are currently out of stock on this product");
									}
								}
							}
						} else
						{
							JOptionPane.showMessageDialog(null,
									"Product does not exist");
						}

					} catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null,
								"Please enter a valid quantity");
					}
				} catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null,
							"Please enter a valid number of nights");
				}
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid id");
			}

		}
		if (ae.getSource() == payment)
		{
			if (added == true)
			{
				Calendar cal = (Calendar.getInstance());
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int year = cal.get(Calendar.YEAR);
				String date = month + "-" + day + "-" + year;
				int days = Integer.parseInt(nights1.getText());

				RentalOrder ro = new RentalOrder(t, date, days);
				RentalPaymentGUI rp = new RentalPaymentGUI(ro, ids, qtys, admin);
				this.dispose();
			} else
			{
				JOptionPane.showMessageDialog(null, "Please choose a rental");
			}

		} else if (ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(admin);
			this.dispose();
		}
	}
}