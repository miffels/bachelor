package applets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Sample extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel status;
	private AppletShape shape;
	private JSlider slid = new JSlider(0, 20);
	private JScrollPane scrollpane = null;
	public static RGBValue gradientStart = new RGBValue(255, 255, 255);
	public static RGBValue gradientEnd = new RGBValue(255, 255, 255);
	
	public void init() {
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					Sample.this.createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("GUIInit failed.");
		}
	}
	
	private void createGUI() {
		this.status = new JLabel();
		this.status.setHorizontalAlignment(JLabel.CENTER);
		this.status.setForeground(new Color(255, 255, 255));
		this.status.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(255, 255, 255)));
		this.shape = ShapeFactory.getShape(0);
		this.shape.setIntParam(10);
		this.status.setText("Lines: "+(this.shape.toString().length() + 1));
		this.buildSlider();
		this.buildMenu();
		this.buildCentre();
		this.getContentPane().add(this.slid, BorderLayout.NORTH);
		this.getContentPane().add(this.scrollpane, BorderLayout.CENTER);
		this.getContentPane().setBackground(new Color(0, 0, 0));
		this.getContentPane().add(this.status, BorderLayout.SOUTH);
		this.setSize(new Dimension(700, 700));
	}
	
	private void buildMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu view = new JMenu("View");
		JMenu fractale = new JMenu("Fractale");
		bar.add(view);
		this.setJMenuBar(bar);	
		view.add(fractale);
		JMenuItem item = new JMenuItem("Dragon Curve");
		fractale.add(item);
		item.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Sample.this.shape = ShapeFactory.getShape(0);
				Sample.this.shape.setIntParam(10);
				Sample.this.scrollpane.setViewportView(Sample.this.shape);
				Sample.this.status.setText("Lines: "+(Sample.this.shape.toString().length() + 1));
				Sample.this.repaint();
				Sample.this.slid.setValue(10);
			}
			
		});
		item = new JMenuItem("Quadragon Curve");
		fractale.add(item);
		item.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Sample.this.shape = ShapeFactory.getShape(1);
				Sample.this.shape.setIntParam(5);
				Sample.this.scrollpane.setViewportView(Sample.this.shape);
				Sample.this.status.setText("Lines: "+(Sample.this.shape.toString().length() + 1));
				Sample.this.repaint();
				Sample.this.slid.setValue(5);
			}
			
		});
		JMenu customize = new JMenu("Customize");
		item = new JMenuItem("Gradient");
		customize.add(item);
		bar.add(customize);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color c = JColorChooser.showDialog(Sample.this, "Pick a gradient start color", Sample.gradientStart.getColor());
				if(c != null) {
					Sample.gradientStart = new RGBValue(c.getRed(), c.getGreen(), c.getBlue());
				}
				c = JColorChooser.showDialog(Sample.this, "Pick a gradient end color", Sample.gradientEnd.getColor());
				if(c != null) {
					Sample.gradientEnd = new RGBValue(c.getRed(), c.getGreen(), c.getBlue());
				}
				Sample.this.repaint();
			}
		});
	}
	
	private void buildCentre() {
		this.scrollpane = new JScrollPane(this.shape);
		this.scrollpane.setOpaque(true);
		this.scrollpane.setBackground(new Color(0, 0, 0));
		this.scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollpane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				JScrollBar sender = (JScrollBar)arg0.getSource();
				sender.getParent().repaint();
			}
			
		});
		this.scrollpane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				JScrollBar sender = (JScrollBar)arg0.getSource();
				sender.getParent().repaint();
			}
			
		});
	}
	
	private void buildSlider() {
		this.slid.setMajorTickSpacing(5);
		this.slid.setMinorTickSpacing(1);
		this.slid.setPaintTicks(true);
		Hashtable<Integer, JLabel> marks = new Hashtable<Integer, JLabel>();
		marks.put(0, new JLabel("0"));
		marks.put(5, new JLabel("5"));
		marks.put(10, new JLabel("10"));
		marks.put(15, new JLabel("15"));
		marks.put(20, new JLabel("20"));
		this.slid.setLabelTable(marks);
		this.slid.setPaintLabels(true);
		this.slid.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider sender = (JSlider)e.getSource();
				Sample.this.shape.setIntParam(sender.getValue());
				Sample.this.status.setText("Lines: "+(Sample.this.shape.toString().length() + 1));
				Sample.this.repaint();
			}
		});
	}

}