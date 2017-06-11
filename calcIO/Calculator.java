package calcIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mathEval.Evaluator;
import mathEval.TestInputs;

public class Calculator {

	private JFrame f;
	private JPanel p;
	@SuppressWarnings("unused")
	private JButton[] nbIn;
	private JButton add;
	private JButton sub;
	private JButton mul;
	private JButton div;
	private JButton eval;

	public Calculator() {

		f = new JFrame("Java Calculator");
		p = new JPanel();

		nbIn = new JButton[10];
		for(int i = 0; i < 10; i++) {
			JButton temp = new JButton(Integer.toString(i));
			temp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					TestInputs.calc.buttonPressed(temp.getText());
				}
			});
			p.add(temp);
		}
		
		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestInputs.calc.buttonPressed("+");
			}
		});
		p.add(add);
		
		sub = new JButton("-");
		sub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestInputs.calc.buttonPressed("-");
			}
		});
		p.add(sub);
		
		mul = new JButton("x");
		mul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestInputs.calc.buttonPressed("*");
			}
		});
		p.add(mul);
		
		div = new JButton("%");
		div.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestInputs.calc.buttonPressed("/");
			}
		});
		p.add(div);
		
		eval = new JButton("=");
		div.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestInputs.calc.buttonPressed("=");
			}
		});
		p.add(eval);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(p);
		f.pack();
		f.setVisible(true);
	}
	
	public void buttonPressed(String userInput) {
		
		if(userInput.equals("=")) {
			LinkedList<Object> exp = Evaluator.primeExp(TestInputs.cExp);
			System.out.println(Evaluator.evaluate(exp).toString());
		} else {
			TestInputs.cExp += userInput;
		}
	}
}