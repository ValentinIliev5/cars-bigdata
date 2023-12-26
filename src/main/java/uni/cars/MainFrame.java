package uni.cars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame{
	
	JPanel panel = new JPanel();
	
	String[] funcs = {"set filters", "by brand"};
	JComboBox<String> functionsCB = new JComboBox<>(funcs);
	
	JTextField brandTF = new JTextField();
	JTextField minHPTF = new JTextField();
	JTextField maxHPTF = new JTextField();
	JTextField MPGTF = new JTextField();
	
	JLabel funcLabel = new JLabel("choose function:");
	JLabel brandLabel = new JLabel("brand:");
	JLabel minHPLabel = new JLabel("min HP: ");
	JLabel maxHPLabel = new JLabel("max HP:");
	JLabel MPGLabel = new JLabel("MPG:");
	
	JButton searchButton = new JButton("Search");
	
	boolean brandSelected = false;
	
	public MainFrame()
	{
		this.setBounds(750,140,450,800);
		this.setTitle("Cars");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initPanel();
		
		add(panel);
		
		this.setVisible(true);
		
		final String BY_BRAND_CHECK = "by brand";
		
		functionsCB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(BY_BRAND_CHECK.equals(functionsCB.getSelectedItem().toString())) {
					brandSelected = true;
				}
				else brandSelected = false;
				
				
				if(brandSelected)
				{
					hideExtra();
				}
				else showExtra();
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				startSolver();
				
			}
		});
	}

	private void startSolver() {
		if(brandSelected)
		{
			System.out.println("Brand average MPG");
			String brandName = brandTF.getText();
			System.out.println("brand :" + brandName);
			Solver.startAvgByBrand(brandName);
		}
		else {
			String brandName = brandTF.getText();
			String minHP = minHPTF.getText();
			String maxHP = maxHPTF.getText();
			String MPG = MPGTF.getText();
			
			System.out.println("Filter search");
			System.out.print("brand: " + brandName +
					", min HP: " + minHP +
					", max HP: " + maxHP +
					", MPG: " + MPG);
			
			
			Solver.startAvgByFilters(brandName,minHP,maxHP,MPG);
		}
}
	private void initPanel() {
		panel.setLayout(null);

		
		funcLabel.setBounds(50,25,100,30);
		functionsCB.setBounds(50,50,350,50);
		
		brandLabel.setBounds(50,125,100,30);
		brandTF.setBounds(50,150,350,30);
		
		minHPLabel.setBounds(50,225,100,30);
		minHPTF.setBounds(50,250,350,30);
		
		maxHPLabel.setBounds(50,325,100,30);
		maxHPTF.setBounds(50,350,350,30);
		
		MPGLabel.setBounds(50,425,100,30);
		MPGTF.setBounds(50,450,350,30);
		
		searchButton.setBounds(175,550,100,30);
		
		panel.add(functionsCB);
		panel.add(funcLabel);
		panel.add(brandTF);
		panel.add(brandLabel);
		panel.add(minHPTF);
		panel.add(minHPLabel);
		panel.add(maxHPTF);
		panel.add(maxHPLabel);
		panel.add(MPGTF);
		panel.add(MPGLabel);
		panel.add(searchButton);
	}
	private void hideExtra() {
		
		minHPTF.setVisible(false);
		maxHPTF.setVisible(false);
		MPGTF.setVisible(false);
		
		minHPLabel.setVisible(false);
		maxHPLabel.setVisible(false);
		MPGLabel.setVisible(false);
		
		searchButton.setBounds(175,200,100,30);
		
	}
	private void showExtra() {
		
		minHPTF.setVisible(true);
		maxHPTF.setVisible(true);
		MPGTF.setVisible(true);
		
		minHPLabel.setVisible(true);
		maxHPLabel.setVisible(true);
		MPGLabel.setVisible(true);
		
		searchButton.setBounds(175,480,100,30);
		
	}
}
