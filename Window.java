import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class Window extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 4511551924301248243L;

	//class variables;
	AsciiCanvas txtArea;
	JButton btnNext;
	JButton btnBack;
	JButton btnSave;
	JButton btnLoad;
	JLabel lblFrame;
	JLabel lblCounter;
	String[] frames;
	int counter = 0;
	int upperBound;
	
	
	public static void main(String[] args){
		new Window();
	} // end main
	
	public Window(){
		super("Animation!");
		setupGUI();
		registerListeners();
	   
	    updateScreen();
	} // end constructor
	
	public void setupGUI(){
		//create components
		JPanel pnlCanvas = new JPanel();
		txtArea = new AsciiCanvas();
	
		
		JPanel pnlControls = new JPanel();
		btnBack = new JButton("<--");
		lblCounter = new JLabel("0");
		btnSave = new JButton("Save");
		btnLoad = new JButton("Load");
		btnNext = new JButton("-->");
		
		//add components to Panels
		pnlCanvas.setLayout(new GridLayout(0,1));
		pnlCanvas.add(txtArea);
		
		pnlControls.setLayout(new FlowLayout());
		pnlControls.add(btnBack);
		pnlControls.add(btnSave);
		pnlControls.add(lblCounter);
		pnlControls.add(btnLoad);
		pnlControls.add(btnNext);
		
		//set up main layout
		Container mainPanel = this.getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(pnlCanvas, BorderLayout.CENTER);
		mainPanel.add(pnlControls, BorderLayout.SOUTH);
		
		
		this.setSize(500, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		txtArea.setFont(font);
		txtArea.loadAnimation();
		upperBound =txtArea.getCount();
	} // end 
	
	public void registerListeners(){
		

		btnBack.addActionListener(this);
		btnNext.addActionListener(this);
		btnSave.addActionListener(this);
		btnLoad.addActionListener(this);
		
	} // end registerListeners
	
	
	
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		//check all button presses and send
		//control to appropriate methods
		
	  
		if (e.getSource() == btnBack){
			txtArea.setText(null);
			prevFrame();
		} 
		else if (e.getSource() == btnNext){
			txtArea.setText(null);
			nextFrame();
		} 
		else if (e.getSource() == btnLoad){
			txtArea.setText(null);
			counter = 0;
			updateScreen();
			txtArea.loadAnimation();
			
			
		}
		else if (e.getSource() == btnSave){
			
			txtArea.saveAnimation(counter);
		}
		else {
			
		} // end if
		
		
	} // end actionPerformed
	
	
	
	public void prevFrame(){
		//back up one frame
		counter--;
		if (counter < 0){
			counter = 0;
		} // end if
		txtArea.updateFrame(counter);
		updateScreen();
	} // end prevQuestion
	
	public void nextFrame(){
		//go forward one frame
		counter++;
		if (counter >= upperBound){
			counter = upperBound-1;
			
		}
		
		txtArea.updateFrame(counter);
        updateScreen();
	} // end prevQuestion
	
	public void updateScreen(){
		//updates screen with current problem
		lblCounter.setText(String.valueOf(counter));
		
		
		
		
	} // end updateScreen
	
} // end class definition

class AsciiCanvas extends JTextArea {
	// each field is a private instance variable

	private JTextArea textArea;
	private String[] frames; 
	
	//constructor expects all inputs
	public AsciiCanvas (){
	textArea = new JTextArea(300,300);
	} // end constructor
	
	
	//loads up the animation 
	public void loadAnimation(){
		frames = new String[getCount()];
		int counter = 0;
		String firstFrame;
		 try {
			 File theFile = new File("frames.txt");
			  Scanner input = new Scanner(theFile);
			  
			 
				
			  while (input.hasNext()){
				  
				 
				  
				  
				  
				  frames[counter] = input.nextLine();
				
				  
				  counter++;
			  } // end while
			  input.close();
			   
		  } 
		 
		 catch (IOException e){
			  System.out.println(e.getMessage());
			  
			  
		  } // end try
		 firstFrame = frames[0];
		 String[] array = firstFrame.split(",");
		  
		 int length = array.length;
		 while(length > 0){
			length--;
			this.insert("\n", 0);
			this.insert(array[length], 0);
			
		 }
		 
		
	}//end load animation
	
	public int getCount(){
		int lineCount = 0;
		
		
		try{
			 
    		File file = new File("frames.txt");
 
    		if(file.exists()){
 
    		    FileReader fileRead = new FileReader(file);
    		    LineNumberReader line = new LineNumberReader(fileRead);
 
    		    
 
    	            while (line.readLine() != null){
    	           
    	        	lineCount++;
    	            }
    	            
    	            line.close();

    		}
    		else{
    			 
    		}
 
    	}
		catch(IOException e){
    		
    	}
		 System.out.println(lineCount);
		 
	return lineCount;
	
	
	}//end line count
	
	
	public void saveAnimation(int number){
		String text = this.getText();
		text = text.replaceAll("\n", ",");
		
		frames[number] = text;
		int length = frames.length;
		
		try {
		    FileWriter outFile = new FileWriter("frames.txt", false);
		    PrintWriter output = new PrintWriter(outFile);
		    
		    for(int i = 0; i < length; i++){
				output.println(frames[i]);
			 }
		    
		
		    output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // end try
		
		
		
		
		}
	


	
	public void updateFrame(int frame){
		
	
		 String currentFrame = frames[frame];
		 String[] array = currentFrame.split(",");
		
		int length = array.length;
		 while(length > 0){
			length--;
			this.insert("\n", 0);
			this.insert(array[length], 0);
		 }
	} // end class definition
	
}