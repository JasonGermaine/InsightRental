package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.Days;

import model.RentalOrder;

import database.MemberOperations;
import database.MovieCopyOperations;
import database.RentalOrderOperations;

public class ReturnGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private boolean admin;
	private ResultSet rset;

	private JTextField memberId, date, name, id, copy;
	private JButton toggle, search, left, right, back, ret;
	private JLabel member, title, returnDate, movieName, movieId, copyNum,
			space1, space2, space3;

	private int rId;

	RentalOrderOperations roo = new RentalOrderOperations();
	RentalOrder ro;
	MovieCopyOperations mco = new MovieCopyOperations();
	MemberOperations mo = new MemberOperations();
	
	public ReturnGUI(boolean admin)
	{
		this.admin = admin;

		setTitle("Return");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();

		// fonts
		Font t = new Font("Trebuchet MS", Font.BOLD, 30);
		Font f = new Font("Trebuchet MS", Font.PLAIN, 18);
		Font sb = new Font("Trebuchet MS", Font.PLAIN, 10);

		title = new JLabel("MOVIE RETURN");

		member = new JLabel("MEMBER ID");
		memberId = new JTextField(10);
		memberId.setBorder(BorderFactory.createLineBorder(Color.black));
		toggle = new JButton("SEARCH RENTAL");
		toggle.addActionListener(this);
		search = new JButton("SEARCH");
		search.addActionListener(this);

		returnDate = new JLabel("EXPECTED RETURN DATE");
		date = new JTextField(15);
		date.setEditable(false);
		date.setBorder(BorderFactory.createLineBorder(Color.black));

		movieName = new JLabel("MOVIE NAME");
		name = new JTextField(15);
		name.setEditable(false);
		name.setBorder(BorderFactory.createLineBorder(Color.black));

		movieId = new JLabel("MOVIE ID");
		id = new JTextField(15);
		id.setEditable(false);
		id.setBorder(BorderFactory.createLineBorder(Color.black));

		copyNum = new JLabel("COPY NUMBER");
		copy = new JTextField(15);
		copy.setEditable(false);
		copy.setBorder(BorderFactory.createLineBorder(Color.black));

		left = new JButton("<");
		left.setVisible(false);
		left.addActionListener(this);

		space1 = new JLabel(" ");
		space2 = new JLabel(" ");
		space3 = new JLabel(" ");

		right = new JButton(">");
		right.setVisible(false);
		right.addActionListener(this);

		back = new JButton("BACK");
		back.addActionListener(this);

		ret = new JButton("RETURN");
		ret.addActionListener(this);
		ret.setVisible(false);

		// Add Components
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(title, t, 0, 0, 2, 1);

		addComponent(space1, f, 1, 0, 1, 1);

		addComponent(member, f, 2, 1, 2, 1);
		addComponent(memberId, f, 2, 3, 2, 1);
		addComponent(toggle, sb, 2, 5, 1, 1);
		addComponent(search, f, 3, 5, 1, 1);

		addComponent(space2, f, 4, 0, 1, 1);

		addComponent(returnDate, f, 5, 1, 2, 1);
		addComponent(date, f, 5, 3, 2, 1);

		addComponent(movieName, f, 6, 1, 2, 1);
		addComponent(name, f, 6, 3, 2, 1);

		addComponent(movieId, f, 7, 1, 2, 1);
		addComponent(id, f, 7, 3, 2, 1);

		addComponent(copyNum, f, 8, 1, 2, 1);
		addComponent(copy, f, 8, 3, 2, 1);

		constraints.fill = GridBagConstraints.NONE;
		addComponent(left, f, 9, 2, 1, 1);
		addComponent(right, f, 9, 5, 1, 1);

		constraints.fill = GridBagConstraints.BOTH;
		addComponent(space3, f, 10, 0, 1, 1);

		addComponent(back, f, 11, 0, 1, 1);
		addComponent(ret, f, 11, 6, 1, 1);

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

	public int checkLate()
	{
		Calendar cal = (Calendar.getInstance());
		int month1 = cal.get(Calendar.MONTH) + 1;
		int day1 = cal.get(Calendar.DAY_OF_MONTH);
		int year1 = cal.get(Calendar.YEAR);
		
		int month2,day2,year2;
		
		String date1 = date.getText();
		String[] parts;
		int results[];
		parts = date1.split("-");
		results = new int[parts.length];
		
		for (int i = 0; i < parts.length; i++)
		{
			results[i] = Integer.parseInt(parts[i].trim());
		}
		
		month2 = results[0];
		day2 = results[1];
		year2 = results[2];
		
		
		DateTime dt1 = new DateTime(year1, month1, day1, 0, 0, 0, 0);
		DateTime dt2 = new DateTime(year2, month2, day2, 0, 0, 0, 0);
		int days = Days.daysBetween(dt2, dt1).getDays();
		double lateFee =0;
		
		if(days > 0 && days < 30)
		{
			lateFee = days *.5;
		}
		else if(days > 30)
		{
			lateFee = 30;
		}
		
		int mId = roo.getRentalMember(rId);
		double bal = mo.getBalance(mId);
		bal = bal - lateFee;
		mo.updateBalance(mId, bal);
		
		return days;
	}
	
	public void setFields(ResultSet rset)
	{
		try
		{
			int prodId = rset.getInt(1);
			name.setText((roo.getName(prodId)));
			id.setText(Integer.toString(rset.getInt(1)));
			copy.setText(Integer.toString(rset.getInt(2)));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent ae)
	{

		if (ae.getSource() == toggle)
		{
			if (toggle.getText().equals("SEARCH MEMBER"))
			{
				member.setText("MEMBER ID");
				toggle.setText("SEARCH RENTAL");
			} else
			{
				member.setText("RENTAL ID");
				toggle.setText("SEARCH MEMBER");
			}
		}
		if (ae.getSource() == search)
		{
			try{
			int id1 = Integer.parseInt(memberId.getText());
			if (member.getText().equals("MEMBER ID"))
			{
				try
				{
					rId = roo.getRentalId(id1);
					rset = mco.getRental(rId);
					if (roo.checkReturn(rId) == true)
					{
						rset.first();
						date.setText(roo.getReturnDate(rId));
						setFields(rset);
						left.setVisible(true);
						right.setVisible(true);
						ret.setVisible(true);
					} else
					{
						JOptionPane.showMessageDialog(null,
								"No Rental under this Member ID");
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			} else if (member.getText().equals("RENTAL ID"))
			{
				try
				{
					rId = id1;
					rset = mco.getRental(rId);
					if (roo.checkReturn(rId) == true)
					{
						rset.first();
						date.setText(roo.getReturnDate(rId));
						setFields(rset);
						left.setVisible(true);
						right.setVisible(true);
						ret.setVisible(true);
					} else
					{
						JOptionPane.showMessageDialog(null,
								"No Rental under this Rental ID");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null,
						"Invalid id");
			}

		}

		if (ae.getSource() == left)
		{
			try
			{
				if (rset.previous() == false)
				{
					rset.last();
				}
				setFields(rset);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (ae.getSource() == right)
		{
			try
			{
				if (rset.next() == false)
				{
					rset.first();
				}
				setFields(rset);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		if (ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(admin);
			this.dispose();
		}
		if (ae.getSource() == ret)
		{
			try
			{
				if (rset.first() && rset.next() == false)
				{
					int mId = Integer.parseInt(id.getText());
					int copy1 = Integer.parseInt(copy.getText());
					if (mco.returnFilm(mId, copy1) == true)
					{
						int late = checkLate();
						if(late > 0)
						{
							JOptionPane.showMessageDialog(null, "Movie returned " + late + " days late");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Movie returned on time");
						}
						
						date.setText(" ");
						name.setText(" ");
						id.setText(" ");
						copy.setText(" ");
						roo.returned(rId);
						left.setVisible(false);
						right.setVisible(false);
						ret.setVisible(false);
					}

					else
					{
						JOptionPane.showMessageDialog(null, "Operation Failed");
					}
				} else
				{
					int mId = Integer.parseInt(id.getText());
					int copy1 = Integer.parseInt(copy.getText());
					if (mco.returnFilm(mId, copy1) == true)
					{
						try
						{
							checkLate();
							rset = mco.getRental(rId);
							rset.first();
							date.setText(roo.getReturnDate(rId));
							setFields(rset);
							
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}

					else
					{
						JOptionPane.showMessageDialog(null, "Operation Failed");
					}
				}
			}

			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}