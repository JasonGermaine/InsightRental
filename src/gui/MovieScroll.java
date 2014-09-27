package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.MovieCopyOperations;
import database.MovieOperations;

public class MovieScroll extends JFrame implements ActionListener
{

	// Global Variables
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private JTextField prodID, name, runTime, year, genre, rating, qty;
	private JLabel title;
	private JTextArea desc;
	private JButton next, prev, back;
	private boolean admin;
	private ResultSet rset;
	private MovieCopyOperations mco = new MovieCopyOperations();
	private MovieOperations mo = new MovieOperations();

	public MovieScroll(boolean admin) // Boolean admin parameter to determine
	// which screen to show
	{
		this.admin = admin;

		// Frame attributes
		setSize(800, 600);
		setTitle("Movies");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		layout = new GridBagLayout();
		setLayout(layout);
		gbc = new GridBagConstraints();

		// Font
		Font bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font titleFont = new Font("Trebuchet MS", Font.BOLD, 30);

		// Text fields
		prodID = new JTextField();
		prodID.setEditable(false);
		name = new JTextField();
		name.setEditable(false);
		runTime = new JTextField();
		runTime.setEditable(false);
		year = new JTextField();
		year.setEditable(false);
		genre = new JTextField();
		genre.setEditable(false);
		rating = new JTextField();
		rating.setEditable(false);
		qty = new JTextField();
		qty.setEditable(false);

		// Text Area
		desc = new JTextArea();
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);

		// Buttons
		next = new JButton("Next");
		next.addActionListener(this);

		prev = new JButton("Prev");
		prev.addActionListener(this);

		back = new JButton("Back");
		back.addActionListener(this);

		// Adding components
		gbc.fill = GridBagConstraints.BOTH; // Setting constraint to fill
		// vertically and horizontally

		addComponent(title = new JLabel("Movies"), titleFont, 0, 0, 1, 1);

		addComponent(new JLabel("Movie ID:"), bodyFont, 0, 1, 2, 1);
		addComponent(prodID, bodyFont, 2, 1, 2, 1);

		addComponent(new JLabel("Name:"), bodyFont, 4, 1, 2, 1);
		addComponent(name, bodyFont, 6, 1, 2, 1);

		addComponent(new JLabel("Running Time:"), bodyFont, 0, 2, 2, 1);
		addComponent(runTime, bodyFont, 2, 2, 2, 1);

		addComponent(new JLabel("Year of Release:"), bodyFont, 4, 2, 2, 1);
		addComponent(year, bodyFont, 6, 2, 2, 1);

		addComponent(new JLabel("Genre:"), bodyFont, 0, 3, 2, 1);
		addComponent(genre, bodyFont, 2, 3, 2, 1);

		addComponent(new JLabel("Age Rating"), bodyFont, 4, 3, 2, 1);
		addComponent(rating, bodyFont, 6, 3, 2, 1);

		addComponent(new JLabel("Description:"), bodyFont, 0, 4, 2, 1);
		addComponent(desc, bodyFont, 2, 4, 6, 3);

		addComponent(new JLabel("Stock Level:"), bodyFont, 0, 7, 2, 1);
		addComponent(qty, bodyFont, 2, 7, 2, 1);

		addComponent(prev, bodyFont, 1, 8, 1, 1);
		addComponent(next, bodyFont, 5, 8, 1, 1);
		addComponent(back, bodyFont, 0, 9, 1, 1);

		setFirst();

		this.setVisible(true); // Setting the visibility

	}

	public void setFirst()
	{
		try
		{
			rset = mo.returnRset();
			rset.first();

			prodID.setText(Integer.toString(rset.getInt(1)));
			name.setText((rset.getString(2)));
			runTime.setText(Integer.toString((rset.getInt(3))));
			year.setText(Integer.toString((rset.getInt(4))));
			genre.setText((rset.getString(5)));
			rating.setText((rset.getString(6)));
			desc.setText((rset.getString(7)));
			qty.setText(Integer.toString(mco.stockEnquiry(rset.getInt(1))));

		} catch (SQLException ex)
		{
			System.out.println("Error in returning result set");
		}
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

		if (c.equals(back) || c.equals(title))
		{
			gbc.insets = new Insets(20, 5, 20, 20);
		} else
		{
			gbc.insets = new Insets(5, 5, 5, 5);
		}

		gbc.fill = GridBagConstraints.BOTH; // Setting constraint to fill
		// vertically and horizontally
		add(c, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if (ae.getSource() == next)
			{
				if(rset.next() == false)
				{
					rset.first();
				}
				prodID.setText(Integer.toString(rset.getInt(1)));
				name.setText((rset.getString(2)));
				runTime.setText(Integer.toString((rset.getInt(3))));
				year.setText(Integer.toString((rset.getInt(4))));
				genre.setText((rset.getString(5)));
				rating.setText((rset.getString(6)));
				desc.setText((rset.getString(7)));
				qty.setText(Integer.toString(mco.stockEnquiry(rset.getInt(1))));
			} else if (ae.getSource() == prev)
			{
				if (rset.previous() == false)
				{
					rset.last();
				}

				prodID.setText(Integer.toString(rset.getInt(1)));
				name.setText((rset.getString(2)));
				runTime.setText(Integer.toString((rset.getInt(3))));
				year.setText(Integer.toString((rset.getInt(4))));
				genre.setText((rset.getString(5)));
				rating.setText((rset.getString(6)));
				desc.setText((rset.getString(7)));
				qty.setText(Integer.toString(mco.stockEnquiry(rset.getInt(1))));
			} else
			{
				HomeGUI h = new HomeGUI(admin);
				this.dispose();
			}
		} catch (SQLException ex)
		{

		}

	}
}
