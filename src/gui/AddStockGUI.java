
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Movie;

import database.MovieCopyOperations;
import database.MovieOperations;

public class AddStockGUI extends JFrame implements ActionListener
{

	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private Font bodyFont, titleFont;
	private JTextField prodID, movieName, runTime, yearOfRelease, qty;
	private JTextArea desc;
	private JButton add, back;
	private JLabel title, pID, mName,rTime,release, type, age, description;
	private JRadioButton newest, existing;
	private String [] genres = {"Fantasy", "Horror", "Thriller", "Sci-fi", "Comedy", "Drama", "Romance"};
	private String [] ratings = {"U", "PG", "12", "15", "18"};
	private JComboBox genre, ageRating;
	private ButtonGroup options;
	private MovieCopyOperations mco = new MovieCopyOperations();
	private MovieOperations mo = new MovieOperations();
	
	public AddStockGUI()
	{
		setSize(800,600);
	  	setTitle("Update Stock");
	  	setLocationRelativeTo(null);
	  	setDefaultCloseOperation(EXIT_ON_CLOSE);
	  	
	  	layout = new GridBagLayout();
	  	setLayout(layout);
	  	gbc = new GridBagConstraints();
		
	  	bodyFont = new Font("Trebuchet MS", Font.PLAIN, 18);
	  	titleFont = new Font("Trebuchet MS", Font.BOLD, 30);
	  	
	  	title = new JLabel("Update Stock");
	  	addComponent(title, titleFont, 0, 0, 2, 1);
	  	
	  	
	  	addComponent(new JLabel("Movie Type:"),bodyFont,0,2,2,1);
	  	newest = new JRadioButton("New", true);	  	
	  	existing = new JRadioButton("Existing", false);

	  	options = new ButtonGroup();
	  	options.add(newest);
	  	options.add(existing);

	  	newest.addActionListener(this);
	  	existing.addActionListener(this);
	  	addComponent(existing,bodyFont,4,2,1,1);
	  	addComponent(newest,bodyFont,3,2,1,1);
	  	
	  	pID = new JLabel("Product ID:");
		addComponent(pID,bodyFont,0,3,2,1);
		pID.setVisible(false);
		prodID = new JTextField();
		addComponent(prodID,bodyFont,3,3,2,1);
		prodID.setVisible(false);

		mName = new JLabel("Movie Name:");
	  	addComponent(mName,bodyFont,0,4,2,1);
		movieName = new JTextField();
		addComponent(movieName,bodyFont,3,4,2,1);
		
		type = new JLabel("Genre:");
		addComponent(type,bodyFont,0,5,2,1);
		genre = new JComboBox();
		genre = new JComboBox(genres);
		genre.setSelectedIndex(6);
		genre.addActionListener(this);
		addComponent(genre,bodyFont,3,5,2,1);
	  	
		age = new JLabel("Age Rating:");
		addComponent(age,bodyFont,0,6,2,1);
		ageRating = new JComboBox();
		ageRating = new JComboBox(ratings);
		ageRating.setSelectedIndex(1);
		ageRating.addActionListener(this);
		addComponent(ageRating,bodyFont,3,6,1,1);
		
		rTime = new JLabel("Running Time:");
		addComponent(rTime,bodyFont,0,7,2,1);
		runTime = new JTextField();
		addComponent(runTime,bodyFont,3,7,2,1);
		
		release = new JLabel("Year of Release:");
		addComponent(release,bodyFont,0,8,2,1);
		yearOfRelease = new JTextField();
		addComponent(yearOfRelease,bodyFont,3,8,2,1);

		description = new JLabel("Description:");
		addComponent(description,bodyFont,0,9,2,1);
		desc = new JTextArea();
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		addComponent(desc,bodyFont,3,9,2,3);
	  	
		addComponent(new JLabel("Quantity"), bodyFont, 0,12,2,1);
		qty = new JTextField();
		addComponent(qty,bodyFont,2,12,2,1);
		
		add= new JButton("Add");
		add.addActionListener(this);
	  	addComponent(add,bodyFont,3,13,2,1);
	  	
	  	back= new JButton("Back");
	  	back.addActionListener(this);
	  	addComponent(back,bodyFont,0,13,1,1);
	  	
		this.setVisible(true);
	}
	
	
	public void addComponent(Component c, Font font, int gx, int gy, int gw, int gh)
	{
	   c.setFont(font);
	   gbc.gridx = gx;
	   gbc.gridy = gy;
	   gbc.gridwidth = gw;
	   gbc.gridheight = gh;
	   gbc.weightx = 0;
	   gbc.weighty = 0;  
	   gbc.insets = new Insets(5,5,5,5);
	   gbc.fill = GridBagConstraints.BOTH;
	   add(c, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if(existing.isSelected())
		{
			pID.setVisible(true);
			prodID.setVisible(true);
			mName.setVisible(false);
			rTime.setVisible(false);
			release.setVisible(false);
			type.setVisible(false);
			age.setVisible(false);
			description.setVisible(false);
			movieName.setVisible(false);
			runTime.setVisible(false); 
			yearOfRelease.setVisible(false);
			genre.setVisible(false);
			ageRating.setVisible(false);
			desc.setVisible(false);
			
			if(ae.getSource() == add)
			{
				try
				{
					int id = Integer.parseInt(prodID.getText());
					
					try
					{
						int quantity = Integer.parseInt(qty.getText());
						int year = mo.getYear(id);
						boolean exist = mco.addExisting(id, quantity,year);
						if(id == 0)
						{
							JOptionPane.showMessageDialog(null,"Movie does not exist");
						}
						else if(exist == true)
						{
							JOptionPane.showMessageDialog(null,"Movie " + id + " updated");
						}
						prodID.setText("");
						qty.setText("");
					}
					catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null,"Invalid quanity entered");
					}
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null,"Invalid product id entered");
				}
			}
		}
		else if(newest.isSelected())
		{
			pID.setVisible(false);
			prodID.setVisible(false);
			mName.setVisible(true);
			rTime.setVisible(true);
			release.setVisible(true);
			type.setVisible(true);
			age.setVisible(true);
			description.setVisible(true);
			movieName.setVisible(true);
			runTime.setVisible(true); 
			yearOfRelease.setVisible(true);
			genre.setVisible(true);
			ageRating.setVisible(true);
			desc.setVisible(true);
			
			if(ae.getSource() == add)
			{
				try
				{
					int quantity = Integer.parseInt(qty.getText());
					try
					{
						int runTime1 = Integer.parseInt(runTime.getText());
						try
						{
							int year = Integer.parseInt(yearOfRelease.getText());
							String name = movieName.getText();
							String genre1 = (String)genre.getSelectedItem();
							String rating = (String)ageRating.getSelectedItem();
							String des = desc.getText();
							
							if(!name.equals(""))
							{
								if(!des.equals(""))
								{
									Movie m = new Movie(name,genre1,runTime1,rating,des, year);
									
									int id = mo.addMovie(m, quantity);
									if(id == 0)
									{
										JOptionPane.showMessageDialog(null,"Movie already exists!");
									}
									else
									{
										JOptionPane.showMessageDialog(null,"Movie " + id + " added");
									}
									movieName.setText("");
									runTime.setText("");
									desc.setText("");
									yearOfRelease.setText("");
									qty.setText("");
									genre.setSelectedIndex(6);
									ageRating.setSelectedIndex(1);
								}
								else
								{
									JOptionPane.showMessageDialog(null,"Please enter a description");
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Please enter movie name");
							}
						}
						catch(NumberFormatException ex)
						{
							JOptionPane.showMessageDialog(null,"Invalid year entered");
						}
					}
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null,"Invalid running time entered");
					}

				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null,"Invalid quantity entered");
				}
			}
		}
		if(ae.getSource() == back)
		{
			HomeGUI h = new HomeGUI(true);
			this.dispose();
		}
	}

}


