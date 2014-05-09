import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.JLabel;

import java.awt.Canvas;


public class Mainwindow extends JFrame {
	private JTextField textField;
	private int newrakam;
	private  BufferedImage image;
	private JFrame parent;
	private JPanel panel;
	private JPanel panel_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel label;
	
	
	public static void main(String [] args){
		new Mainwindow();
	}
	
	Mainwindow(){
		setSize(532,462);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		parent = this;
		panel = new JPanel();
		panel.setBounds(0, 0, 265, 424);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewBarcode = new JButton("New Barcode");
		btnNewBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.hide();
				panel_3.show();
			}
		});
		btnNewBarcode.setBounds(42, 42, 177, 23);
		panel.add(btnNewBarcode);
		textField = new JTextField();
		textField.setBounds(42, 184, 177, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Enter Barcode");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search(Integer.parseInt(textField.getText()));
			}
		});
		
		btnSearch.setBounds(42, 215, 177, 23);
		panel.add(btnSearch);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
			}
		});
		btnExit.setBounds(42, 328, 177, 23);
		panel.add(btnExit);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(264, 0, 252, 424);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		Thread th = new Thread(new Runnable(){
			public void run() {
				Scanner sc = new Scanner(System.in);
				while(true){
				int x = sc.nextInt();
				search(x);
				}
			}	
		});
		th.start();
		label = new JLabel();
		label.setBounds(64, 62, 150, 80);
		panel_1.add(label);
				
		panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 265, 424);
		getContentPane().add(panel_3);
		panel_3.hide();
		
		textField_1 = new JTextField();
		textField_1.setBounds(92, 173, 163, 20);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(92, 142, 163, 20);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(92, 80, 163, 20);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(92, 111, 163, 20);
		textField_4.setColumns(10);
		panel_3.setLayout(null);
		panel_3.add(textField_1);
		panel_3.add(textField_2);
		panel_3.add(textField_3);
		panel_3.add(textField_4);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_3.hide();
				panel.show();
				label.setIcon(null);
			}
		});
		btnBack.setBounds(75, 25, 89, 23);
		panel_3.add(btnBack);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		btnSubmit.setBounds(75, 345, 89, 23);
		panel_3.add(btnSubmit);
		
		JRadioButton rdbtnStepA = new JRadioButton("step A");
		rdbtnStepA.setBounds(55, 225, 109, 23);
		panel_3.add(rdbtnStepA);
		
		JRadioButton rdbtnStepB = new JRadioButton("step B");
		rdbtnStepB.setBounds(55, 247, 109, 23);
		panel_3.add(rdbtnStepB);
		
		JRadioButton rdbtnStepB_1 = new JRadioButton("step B");
		rdbtnStepB_1.setBounds(55, 273, 109, 23);
		panel_3.add(rdbtnStepB_1);
		
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
		show();
	}
	
	void make_bar_code(int number){
		BitMatrix bitMatrix = null;
		try {
		    bitMatrix = new Code128Writer().encode(String.valueOf(number), BarcodeFormat.CODE_128, 150, 80, null);
		    image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		    } catch (WriterException e){
		        e.printStackTrace();
		    } 
		label.setIcon(new ImageIcon(image));
	}
	
	private void search(int value) {
		boolean found = false;
		if(found){
			view(value);
		}else{
			make_bar_code(value);
			panel.hide();
			panel_3.show();
		}
	}
	
	private void add() {
		
	}
	
	private void view(int value){
		
	}
}