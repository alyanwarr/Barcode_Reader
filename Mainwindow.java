import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class Mainwindow extends JFrame {
	private JTextField barCodeText;
	private int newnum;
	private BufferedImage image;
	private JFrame parent;
	private JPanel panel;
	private JPanel panel_3;

	/*
	 * User input fields
	 */
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	private JLabel label;
	private JRadioButton rdbtnStepA;
	private JRadioButton rdbtnStepB;
	private JRadioButton rdbtnStepC;
	private JLabel view_package;
	private JLabel view_datasent;
	private JLabel view_recieved;
	private JLabel view_id;

	/*
	 * Thread waiting for input from barcode reader
	 */
	private Thread th;

	private BarCode tokens;
	private File storedData;
	private boolean fileNotGenerated;

	public static void main(String[] args) throws FileNotFoundException {
		new Mainwindow();
	}

	@SuppressWarnings("deprecation")
	Mainwindow() throws FileNotFoundException {
		storedData = null;

		setSize(532, 462);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		parent = this;

		panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 265, 424);
		getContentPane().add(panel_3);
		panel_3.hide();

		textField_4 = new JTextField();
		textField_4.setBounds(92, 173, 163, 20);
		textField_4.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(92, 142, 163, 20);
		textField_3.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(92, 80, 163, 20);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(92, 111, 163, 20);
		textField_2.setColumns(10);
		panel_3.setLayout(null);
		panel_3.add(textField_4);
		panel_3.add(textField_3);
		panel_3.add(textField_1);
		panel_3.add(textField_2);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				panel_3.hide();
				panel.show();
				label.setIcon(null);
				textField_4.setText("");
				textField_3.setText("");
				textField_1.setText("");
				textField_2.setText("");
				rdbtnStepA.setSelected(false);
				rdbtnStepB.setSelected(false);
				rdbtnStepC.setSelected(false);
				view_datasent.setText("");
				view_id.setText("");
				view_package.setText("");
				view_recieved.setText("");
			}
		});
		btnBack.setBounds(75, 25, 89, 23);
		panel_3.add(btnBack);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
				search(newnum);
				panel.show();
				panel_3.hide();
				label.setIcon(null);
				textField_4.setText("");
				textField_3.setText("");
				textField_1.setText("");
				textField_2.setText("");
				rdbtnStepA.setSelected(false);
				rdbtnStepB.setSelected(false);
				rdbtnStepC.setSelected(false);
				view_datasent.setText("");
				view_id.setText("");
				view_package.setText("");
				view_recieved.setText("");
			}
		});
		btnSubmit.setBounds(75, 345, 89, 23);
		panel_3.add(btnSubmit);

		ButtonGroup group = new ButtonGroup();
		
		rdbtnStepA = new JRadioButton("step A");
		rdbtnStepA.setBounds(55, 225, 109, 23);
		panel_3.add(rdbtnStepA);
		
		rdbtnStepB = new JRadioButton("step B");
		rdbtnStepB.setBounds(55, 247, 109, 23);
		panel_3.add(rdbtnStepB);

		rdbtnStepC = new JRadioButton("step C");
		rdbtnStepC.setBounds(55, 273, 109, 23);
		panel_3.add(rdbtnStepC);

		group.add(rdbtnStepA);
		group.add(rdbtnStepB);
		group.add(rdbtnStepC);
		
		JLabel lblNewLabel = new JLabel("Package");
		lblNewLabel.setBounds(10, 83, 72, 14);
		panel_3.add(lblNewLabel);

		JLabel lblDataSent = new JLabel("Data sent");
		lblDataSent.setBounds(10, 114, 72, 14);
		panel_3.add(lblDataSent);

		JLabel lblDataReceived = new JLabel("Data received");
		lblDataReceived.setBounds(10, 145, 72, 14);
		panel_3.add(lblDataReceived);

		JLabel lblIdNumber = new JLabel("ID Number");
		lblIdNumber.setBounds(10, 176, 72, 14);
		panel_3.add(lblIdNumber);
		panel = new JPanel();
		panel.setBounds(0, 0, 265, 424);
		getContentPane().add(panel);
		panel.setLayout(null);

		tokens = null;
		// //////////////////////////////////////////////////////////////
		storedData = new File("data.txt");
		if(!storedData.exists())
			try {
				storedData.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		tokens = new BarCode(storedData);
		if (tokens == null) {
			fileNotGenerated = true;
		}
		// /////////////////////////////////////////////////////////////
		JButton btnNewBarcode = new JButton("New Barcode");
		btnNewBarcode.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				panel.hide();
				panel_3.show();
			}
		});
		btnNewBarcode.setBounds(42, 42, 177, 23);
		panel.add(btnNewBarcode);
		barCodeText = new JTextField();
		barCodeText.setBounds(42, 184, 177, 20);
		panel.add(barCodeText);
		barCodeText.setColumns(10);

		JButton searchButton = new JButton("Enter Barcode");
		searchButton.addActionListener(new ActionListener() {
			String valueOfBarCodeText = null;

			public void actionPerformed(ActionEvent e) {
				valueOfBarCodeText = barCodeText.getText();
				if (valueOfBarCodeText != null) {
					try {
						newnum=Integer.parseInt(valueOfBarCodeText);
						search(newnum);
						barCodeText.setText("");
					} catch (Exception ex) {
						// this mean that the input was not a number
						System.out.println("wrong entry");
						barCodeText.setText("");
					}

				}
			}
		});

		searchButton.setBounds(42, 215, 177, 23);
		panel.add(searchButton);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				System.exit(0);
			}
		});
		btnExit.setBounds(42, 328, 177, 23);
		panel.add(btnExit);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(264, 0, 252, 424);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		// this thread waits for input
		th = new Thread(new Runnable() {
			public void run() {
				Scanner sc = new Scanner(System.in);
				while (true) {
					// must check if not in adding case or program is not in
					// error case
					newnum = sc.nextInt();
					search(newnum);
				}
			}
		});
		th.start();

		label = new JLabel();
		label.setBounds(52, 47, 150, 80);
		panel_1.add(label);
		
		JLabel lblPackage = new JLabel("Package");
		lblPackage.setBounds(10, 193, 60, 14);
		panel_1.add(lblPackage);
		
		JLabel label_1 = new JLabel("Data sent");
		label_1.setBounds(10, 218, 72, 14);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("Data received");
		label_2.setBounds(10, 243, 99, 14);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("ID Number");
		label_3.setBounds(10, 268, 72, 14);
		panel_1.add(label_3);
		
		view_package = new JLabel("");
		view_package.setBounds(114, 193, 128, 14);
		panel_1.add(view_package);
		
		view_datasent = new JLabel("");
		view_datasent.setBounds(114, 218, 128, 14);
		panel_1.add(view_datasent);
		
		view_recieved = new JLabel("");
		view_recieved.setBounds(114, 243, 128, 14);
		panel_1.add(view_recieved);
		
		view_id = new JLabel("");
		view_id.setBounds(114, 268, 128, 14);
		panel_1.add(view_id);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	save();
		    }

		}));
		
		show();
	}

	/**
	 * This function views the barcode image for a given barcode.
	 * 
	 * @param barcode
	 */
	void make_bar_code(int barcode) {

		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new Code128Writer().encode(String.valueOf(barcode),
					BarcodeFormat.CODE_128, 150, 80, null);
			image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		label.setIcon(new ImageIcon(image));
	}

	/**
	 * This function search for barcode in files If found an element view it
	 * else open a window to register new element
	 * 
	 * @param barcode
	 * @throws FileNotFoundException
	 */
	private void search(int barcode) {
		boolean found = false;
		int result = tokens.FindWithGeneratedCode(barcode);
		if (result == -1) {
			found = false;
		} else {
			found = true;
		}
		if (found) {
			view(result);
		} else {
			view_datasent.setText("");
			view_id.setText("");
			view_package.setText("");
			view_recieved.setText("");
			label.setIcon(null);
			make_bar_code(barcode);
			panel.hide();
			panel_3.show();
		}
	}

	private void add() {
		System.out.println(newnum);
		Data temp = new Data();
		String choice = null;
		temp.setGeneratedNumber(newnum);
		temp.setPackageData(textField_1.getText());
		temp.setDataSent(textField_2.getText());
		temp.setRecievedData(textField_3.getText());
		temp.setIDNumber(Integer.parseInt(textField_4.getText()));
		choice = getSellectedButton();
		temp.setChoice(choice);
		tokens.addToList(temp);
	}

	private String getSellectedButton() {
		String value = null;
		if (rdbtnStepA.isSelected()) {
			value = "Step A";
		} else if (rdbtnStepB.isSelected()) {
			value = "Step B";
		} else if (rdbtnStepC.isSelected()) {
			value = "Step C";
		}
		return value;
	}
	
	void save(){
		ArrayList<Data> allData = tokens.getTokens();
		try {
			PrintWriter writer = new PrintWriter(storedData);
			for(int i = 0; i < allData.size(); i++){
				writer.println(allData.get(i).getGeneratedNumber());
				writer.println(allData.get(i).getPackageData());
				writer.println(allData.get(i).getDataSent());
				writer.println(allData.get(i).getRecievedData());
				writer.println(allData.get(i).getIDNumber());
				writer.println(allData.get(i).getChoice());
			}
			writer.flush();writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * This function initialzie the view panel with the element data of the
	 * given barcode.
	 * 
	 * @param barcode
	 */
	private void view(int res) {
		make_bar_code(tokens.getTokens().get(res).getGeneratedNumber());
		view_package.setText(tokens.getTokens().get(res).getPackageData());
		view_id.setText(String.valueOf(tokens.getTokens().get(res).getIDNumber()));
		view_datasent.setText(tokens.getTokens().get(res).getDataSent());
		view_recieved.setText(tokens.getTokens().get(res).getRecievedData());
	}
}
