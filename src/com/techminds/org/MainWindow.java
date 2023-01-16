package com.techminds.org;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

class MainWindow extends JFrame implements ActionListener {
	/**
	 * This class is use to move the files in datewise
	 */
	private static final long serialVersionUID = 1L;

	static JLabel sourceLabel;
	static JLabel destinationLabel;
	static JLabel emptyLabel;
	static JComboBox<?> selectionComboBox;
	static JFrame jframe;

	public static void main(String args[]) {
		componentCreation();
	}

	public static void componentCreation() {
		jframe = new JFrame("file chooser to select directories");
		jframe.setSize(600, 600);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button1 = new JButton("Source");
		JButton button2 = new JButton("Destination");
		JButton button3 = new JButton("Copy");

		MainWindow f1 = new MainWindow();
		button1.addActionListener(f1);
		button2.addActionListener(f1);
		button3.addActionListener(f1);

		JPanel p = new JPanel();
		p.add(button1);
		p.add(button2);
		p.add(button3);

		sourceLabel = new JLabel("no file selected");
		destinationLabel = new JLabel("no file selected");
		emptyLabel = new JLabel();

		String s1[] = { "YearWise", "DateWise" };
		selectionComboBox = new JComboBox<Object>(s1);

		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() == selectionComboBox) {
					emptyLabel.setText(selectionComboBox.getSelectedItem() + " selected");
				}
			}
		};

		selectionComboBox.addItemListener(itemListener);

		p.add(sourceLabel);
		p.add(destinationLabel);
		p.add(selectionComboBox);
		p.add(emptyLabel);
		jframe.add(p);
		jframe.setVisible(true);
	}

	public void actionPerformed(ActionEvent evt) {
		// if the user presses the save button show the save dialog
		String com = evt.getActionCommand();
		if (com.equals("Source")) {
			fileChooserActions(true);
		} else if (com.equals("Destination")) {
			fileChooserActions(false);
		} else if (com.equals("Copy")) {
			copyActionPerformed();
		}
	}

	public void fileChooserActions(boolean source) {
		if (source) {
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = j.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {
				sourceLabel.setText(j.getSelectedFile().getAbsolutePath());
			} else
				sourceLabel.setText("the user cancelled the operation");
		} else {
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = j.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {
				destinationLabel.setText(j.getSelectedFile().getAbsolutePath());
			} else
				destinationLabel.setText("the user cancelled the operation");
		}
	}

	public void copyActionPerformed() {
		String sourceFolderLocation = sourceLabel.getText();
		String destFolderLocation = destinationLabel.getText();
		String selectedType = selectionComboBox.getSelectedItem().toString();

		boolean isYearlySelected = false;
		if (selectedType.equalsIgnoreCase("Yearly")) {
			isYearlySelected = true;
		}

		if (sourceFolderLocation.equalsIgnoreCase(destFolderLocation)) {
			JOptionPane.showMessageDialog(jframe, "Souce and Destination are Same, Please select Different Loc",
					"Location Error", JOptionPane.ERROR_MESSAGE);
		} else if (sourceFolderLocation.equalsIgnoreCase("no file selected")
				|| destFolderLocation.equalsIgnoreCase("no file selected")
				|| sourceFolderLocation.equalsIgnoreCase("the user cancelled the operation")
				|| destFolderLocation.equalsIgnoreCase("the user cancelled the operation")) {
			JOptionPane.showMessageDialog(jframe, "Please Select Valid Loc", "Location Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			int total = FileCopierUtil.filesCopy(sourceFolderLocation, destFolderLocation, isYearlySelected);
			if (total > 0) {
				JOptionPane.showMessageDialog(jframe, "Total Files Copied:" + total, "Copied Successful",
						JOptionPane.OK_OPTION);
			} else {
				JOptionPane.showMessageDialog(jframe, "The Folder is Empty!", "No Files", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println(total);

		}

	}

}
