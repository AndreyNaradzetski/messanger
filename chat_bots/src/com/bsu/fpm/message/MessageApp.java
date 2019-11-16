package com.bsu.fpm.message;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.bsu.fpm.message.controller.MainController;

public class MessageApp {

	public static void main(String[] args) {
	    
		final MainController controller = new MainController();
		
		controller.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		controller.getFrame().setResizable(true);
		
        SwingUtilities.invokeLater(()-> controller.getFrame().setVisible(true));
        
        Thread.setDefaultUncaughtExceptionHandler((t,e)->e.printStackTrace());
	}
}
