package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import javax.swing.*;

import model.SalesOrder;

import database.MovieCopyOperations;
import database.MovieOperations;

public class PurchaseGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private boolean admin, added;

	private JTextField productId1, qty1,subtotal1, subtotal2,
			subtotal3, total1;
	private JButton add1, add2, add3, payment, back;
	private JLabel title, productId, qty, nights, subtotal, total,
			order;
	private JTextArea basket;
	private MovieOperations mo = new MovieOperations();
	private MovieCopyOperations mco = new MovieCopyOperations();
	private double t;
	private int num;
	private int levels[];
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<Integer> qtys = new ArrayList<Integer>();

	public PurchaseGUI(boolean admin)
	{
		this.admin = admin;

		added = false;
		setTitle("Purchase");
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

		title = new JLabel("MOVIE PURCHASE");

		productId = new JLabel("PRODUCT ID");
		productId1 = new JTextField();

		qty = new JLabel("QUANTITY");
		qty1 = new JTextField();

		add1 = new JButton("ADD");
		add1.addActionListener(this);
		add2 = new JButton("ADD");
		add2.addActionListener(this);
		add3 = new JButton("ADD");
		add3.addActionListener(this);

		total = new JLabel("TOTAL");
		total1 = new JTextField();
		total1.setEditable(false);

		subtotal = new JLabel("SUBTOTAL");
		subtotal1 = new JTextField();
		subtotal1.setEditable(false);
		subtotal2 = new JTextField();
		subtotal2.setEditable(false);
		subtotal3 = new JTextField();
		subtotal3.setEditable(false);

		order = new JLabel("BASKET");
		basket = new JTextArea();
		basket.setEditable(false);
		basket.setBorder(BorderFactory.createLineBorder(Color.black));
		basket.setText("Title:\tQuantity:\tPrice:\n");

		back = new JButton("BACK");
		back.addActionListener(this);

		payment = new JButton("PAYMENT");
		payment.addActionListener(this);

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
		addComponent(add1, f, 3, 4, 1, 1);

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

		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(5, 5, 5, 5);

		layout.setConstraints(component, constraints);
		add(component);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == add1)
		{
			try
			{
				int id = Integer.parseInt(productId1.getText());
				
				try
				{
				int qty = Integer.parseInt(qty1.getText());
				if(mco.stockEnquiry(id)!= -1)
				{
					for(int i =0; i < num; i++)
					{
						if( i == id -1)
						{
							if(levels[i] != 0)
							{
								if(levels[i] - qty >= 0)
								{
									levels[i] = levels[i] - qty;
									t = t + (mco.getPrice(id) * qty);
									basket.append(mo.getName(id) + "\t" + qty + "\t€"+ (mco.getPrice(id) * qty) + "\n");
									subtotal1.setText("€" + (mco.getPrice(id) * qty));
									total1.setText("€" + t);
									ids.add(id);
									qtys.add(qty);
									
									productId1.setText("");
									qty1.setText("");
									
									added= true;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Stock level too low");
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null,"We are currently out of stock on this product");
							}
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Product does not exist");
				}
					
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
				}
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid id");
			}

		}

		if (ae.getSource() == payment)
		{
			if(added == true)
			{
			Calendar cal = (Calendar.getInstance());
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int year = cal.get(Calendar.YEAR);
			String date = month + "-" + day + "-" + year;

			SalesOrder so = new SalesOrder(t, date);
			PurchasePaymentGUI pm = new PurchasePaymentGUI(so, ids, qtys, admin);
			this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please choose a purchase");
			}
		} else if (ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(admin);
			this.dispose();
		}
	}
}