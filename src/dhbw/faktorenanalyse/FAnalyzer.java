package dhbw.faktorenanalyse;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FAnalyzer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Double[][] inputTable;
	private Integer lineIndex = 0;
	private Integer cols = null;
	private Integer maxLine = null;
	private JTextArea input = new JTextArea();
	private JLabel info = new JLabel();
	private String regex = "";
	private Integer corAbsPrec = 10000;
	private Integer corAngPrec = 100;
	private Integer jacAbsPrec = 1000;
	private Integer MAbsPrec = 1000;
	private boolean ready = false;
	private boolean inputValid = false;
	private boolean clusterReady = false;
	private Integer max_at_i = null;
	private Integer max_at_j = null;
	private String clusterHistory = "";
	private String clusterHistoryBuf = "";
	private Integer clusterCount = 1;
	private Double[][] clustRawData = null;
	JButton go = new JButton("Add line");
	
	public FAnalyzer() {
		// initialize UI
		
		try {
			this.maxLine = Integer.parseInt(JOptionPane.showInputDialog("Anzahl der Produkte:"));
			this.cols = Integer.parseInt(JOptionPane.showInputDialog("Anzahl der Merkmale:"));
		} catch(NumberFormatException e1) {
			System.exit(ABORT);
		}
		
		this.setTitle("Faktoren- und Clusteranalyse");
		
		if(this.maxLine < 2 || this.cols < 2) {
			System.err.println("Ungültige Eingaben: Anzahl Merkmale/Produkte muss >= 2 sein");
			System.exit(0);
		}

		this.buildRegex();
		
		this.setVisible(true);
		go.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FAnalyzer.this.addLine();
			}
			
		});
		
		this.go.setEnabled(false);
		this.input.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch(arg0.getModifiers()) {
					case 2:
						switch(arg0.getKeyCode()) {
							case KeyEvent.VK_I:
								if(FAnalyzer.this.inputValid)
									FAnalyzer.this.addLine();
								break;
							case KeyEvent.VK_K:
								if(FAnalyzer.this.ready)
									FAnalyzer.this.startCor();
								break;
							case KeyEvent.VK_J:
								if(FAnalyzer.this.ready)
									FAnalyzer.this.startJac();
								break;
							case KeyEvent.VK_M:
								if(FAnalyzer.this.ready)
									FAnalyzer.this.startM();
								break;
							case KeyEvent.VK_C:
								if(FAnalyzer.this.clusterReady)
									FAnalyzer.this.avgCluster(FAnalyzer.this.clustRawData);
								break;
						}
						break;
					default:
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		this.input.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				FAnalyzer.this.inputValid = FAnalyzer.this.input.getText().matches(FAnalyzer.this.regex);
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				FAnalyzer.this.inputValid = FAnalyzer.this.input.getText().matches(FAnalyzer.this.regex);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				FAnalyzer.this.inputValid = FAnalyzer.this.input.getText().matches(FAnalyzer.this.regex);
			}
			
		});
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));//new GridLayout(3, 1));
		this.getContentPane().add(this.info);
		JScrollPane scrollpane = new JScrollPane(this.input);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollpane);
		//this.getContentPane().add(this.go);
		this.info.setText("Bitte Merkmalsausprägungen der Produkte von Leerzeichen getrennt zeilenweise eingeben:");
		this.input.setPreferredSize(new Dimension(200, 300));
		this.pack();
		this.input.requestFocus();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void buildRegex() {
		if(this.cols > 2) {
			this.regex = "( -?[0-9]+(.[0-9]+)?){" + (FAnalyzer.this.cols - 2) + "}";
		}
		this.regex =	"(-?[0-9]+(.[0-9]+)?)" +
						this.regex + 
						" (-?[0-9]+(.[0-9]+)?)";
		this.regex = "^(" + this.regex + "\\n){" + (this.maxLine - 1) + "," + (this.maxLine - 1) + "}" + this.regex + "$";

		System.out.println("Input regex: " + FAnalyzer.this.regex);
	}
	
	protected void addLine() {
		// adds maxLine lines to the matrix
		
		// 1) split at line breaks
		String[] r = this.input.getText().split("\n");
		String[][] s = new String[r.length][];
		// 2) split at space chars
		for(Integer i = 0; i < r.length; i++) {
			s[i] = r[i].split(" ");
		}
		
		// initialize table
		if(this.inputTable == null) {
			this.inputTable = new Double[this.maxLine][this.cols];
		}
		
		// convert to double
		if(this.lineIndex < this.maxLine) {
			for(Integer i = 0; i < this.maxLine; i++) {
				for(Integer j = 0; j < this.cols; j++) {
					this.inputTable[i][j] = Double.parseDouble((s[i][j]));
				}
				this.lineIndex++;
			}
		} else {
			return;
		}
		
		// start if full
		if(this.lineIndex == this.maxLine) {
			this.info.setText("Matrix gefüllt.");
			this.ready = true;
		} else {
			this.info.setText("Bitte Merkmalsausprägungen von Produkt " + (this.lineIndex + 1) + " von Leerzeichen getrennt eingeben:");
			this.input.setText("");
			this.input.requestFocus();
		}
	}

	private void startCor() {
//		this.inputTable[0][0] = 2.;
//		this.inputTable[1][0] = 4.;
//		this.inputTable[2][0] = 7.;
//		this.inputTable[3][0] = 5.;
//		this.inputTable[4][0] = 2.;
//		this.inputTable[5][0] = 1.;
//		
//		this.inputTable[0][1] = 5.;
//		this.inputTable[1][1] = 3.;
//		this.inputTable[2][1] = 2.;
//		this.inputTable[3][1] = 3.;
//		this.inputTable[4][1] = 4.;
//		this.inputTable[5][1] = 2.;
//		
//		this.inputTable[0][2] = 1.;
//		this.inputTable[1][2] = 1.;
//		this.inputTable[2][2] = 6.;
//		this.inputTable[3][2] = 2.;
//		this.inputTable[4][2] = 6.;
//		this.inputTable[5][2] = 6.;
//		
//		this.inputTable[0][3] = 7.;
//		this.inputTable[1][3] = 4.;
//		this.inputTable[2][3] = 1.;
//		this.inputTable[3][3] = 3.;
//		this.inputTable[4][3] = 2.;
//		this.inputTable[5][3] = 2.;
//		
//		this.inputTable[0][4] = 5.;
//		this.inputTable[1][4] = 2.;
//		this.inputTable[2][4] = 3.;
//		this.inputTable[3][4] = 4.;
//		this.inputTable[4][4] = 4.;
//		this.inputTable[5][4] = 6.;

		
		Double[][] tableT = new Double[this.cols][this.maxLine];
		Double[][] res = new Double[this.cols][this.cols];
		Double curVal = 0.0;
		Double curAng = 0.0;
		for(Integer i = 0; i < this.maxLine; i++) {
			for(Integer j = 0; j < this.cols; j++) {
				tableT[j][i] = this.inputTable[i][j];
			}
		}
		for(Integer i = 0; i < this.cols; i++) {
			for(Integer j = 0; j <= i; j++) {
				curVal = this.corr(tableT[i], tableT[j]);
				if(i != j) {
					res[i][j] = new Double(Math.round(curVal * this.corAbsPrec)) / this.corAbsPrec;
					curAng = Math.toDegrees(Math.acos(curVal));
					if(curAng == 180.0) {
						curAng = 0.0;
					}
					res[j][i] = new Double(Math.round(Math.toDegrees(Math.acos(curVal)) * this.corAngPrec)) / corAngPrec;
				} else {
					res[i][j] = new Double(Math.round(curVal * this.corAbsPrec)) / this.corAbsPrec;
				}
			}
		}
		
		this.print(res, true);
	}
	
	private String print(Double[][] res, boolean printDeg) {
		this.input.setEditable(false);
		String text = "";
		for(Integer i = 0; i < res.length; i++) {
			for(Integer j = 0; j < res[i].length; j++) {
				if(res[i][j] != null) {
					text += res[i][j];
					if(j > i && printDeg) {
						text += "°";
					}
					text += "\t";
				}
			}
			text += "\n";
		}
		
		System.out.print(text);
		this.input.setText(text);
		return text;
	}
	
	private void startJac() {
		Double curVal = null;
		Double[][] res = new Double[this.maxLine][this.maxLine];
		for(Integer i = 0; i < this.maxLine; i++) {
			for(Integer j = 0; j <= i; j++) {
				curVal = this.jac(this.inputTable[i], this.inputTable[j]);
				res[i][j] = new Double(Math.round(curVal * this.jacAbsPrec)) / this.jacAbsPrec;
			}
		}
		
		this.print(res, false);
		this.clusterReady = true;
		this.clustRawData = res;
	}
	
	private void startM() {
		Double curVal = null;
		Double[][] res = new Double[this.maxLine][this.maxLine];
		for(Integer i = 0; i < this.maxLine; i++) {
			for(Integer j = 0; j <= i; j++) {
				curVal = this.M(this.inputTable[i], this.inputTable[j]);
				res[i][j] = new Double(Math.round(curVal * this.MAbsPrec)) / this.MAbsPrec;
			}
		}
		
		this.print(res, false);
		this.clusterReady = true;
		this.clustRawData = res;
	}
	
	private Double[][] avgCluster(Double[][] matr) {
		if(matr.length == 1) {
			this.clusterCount = 1;
			this.clusterHistory = this.clusterHistoryBuf;
			return matr;
		} else {
			Double[][] res = new Double[matr.length - 1][];
			Double max = this.getMax(matr, true);
			Integer cpy_i = 0;
			for(Integer i = 0; i < matr.length; i++) {
				Integer cpy_j = 0;
				if(!(i.equals(this.max_at_i) || i.equals(this.max_at_j))) {
					res[cpy_i] = new Double[matr[i].length - 1];
					for(Integer j = 0; j <= i; j++) {
						if(!(j.equals(this.max_at_i) || j.equals(this.max_at_j))) {
							res[cpy_i][cpy_j] = matr[i][j];
							cpy_j++;
						}
					}
					cpy_i++;
				}
			}
			res[res.length - 1] = new Double[res.length - 1];
			for(Integer i = 0; i < res[res.length - 1].length; i++) {
				if(!(i.equals(this.max_at_i) || i.equals(this.max_at_j))) {
					Integer current_i = this.max_at_i;
					Integer current_j = i;
					if(i > this.max_at_i) {
						current_i = i;
						current_j = this.max_at_i;
					}
					
					current_i = this.max_at_j;
					current_j = i;
					if(i > this.max_at_j) {
						current_i = i;
						current_j = this.max_at_j;
					}
				}
			}
			this.clusterHistoryBuf += this.print(res, false);
			return res;
		}
	}
	
	private Double jac(Double[] var1, Double[] var2) {
		Integer	num_And = 0,
				num_Xor = 0;
		
		if(var1.length != var2.length) {
			return null;
		}
		
		for(Integer i = 0; i < var1.length; i++) {
			if(var1[i].equals(var2[i]) && !var1[i].equals(0.0)) {
				num_And++;
			} else if(!var1[i].equals(var2[i])) {
				num_Xor++;
			}
		}
		
		return new Double(num_And)/(num_And + num_Xor);
	}
	
	private Double M(Double[] var1, Double[] var2) {
		Integer	num_EQ = 0;
		
		if(var1.length != var2.length) {
			return null;
		}
		
		for(Integer i = 0; i < var1.length; i++) {
			if(var1[i].equals(var2[i])) {
				num_EQ++;
			}
		}
		
		return new Double(num_EQ)/(this.cols);
	}
	
	private Double corr(Double[] var1, Double[] var2) {
		if(var1.length != var2.length) {
			return null;
		}
		
		// 1) get avrg
		Double	avrg1 = 0.0,
				avrg2 = 0.0;
		
		for(Integer i = 0; i < var1.length; i++) {
			avrg1 = avrg1 + var1[i];
			avrg2 = avrg2 + var2[i];
		}
		avrg1 = avrg1/var1.length;
		avrg2 = avrg2/var2.length;
		
		// 2) get differences
		
		Double[]	d1 = new Double[var1.length],
					d2 = new Double[var2.length];
		
		for(Integer i = 0; i < var1.length; i++) {
			d1[i] = avrg1 - var1[i];
			d2[i] = avrg2 - var2[i];
		}
		
		// 3) get s/o (var1-avrg1)*(var2-avrg2)
		
		Double dp = 0.0;
		
		for(Integer i = 0; i < var1.length; i++) {
			dp += d1[i] * d2[i];
		}
		
		// 4) get s/o squares
		
		Double	sqs1 = 0.0,
				sqs2 = 0.0;
		
		for(Integer i = 0; i < var1.length; i++) {
			sqs1 += d1[i] * d1[i];
			sqs2 += d2[i] * d2[i];
		}
		
		return dp / Math.sqrt(sqs1*sqs2);
	}
	
	private Double getMax(Double[][] matr, boolean ltr_only) {
		Double max = null;
		for(Integer i = 0; i < matr.length; i++) {
			for(Integer j = 0; j < matr[i].length; j++) {
				if(j < i) {
					if(max == null) {
						max = matr[i][j];
					} else if(max < matr[i][j]){
						max = matr[i][j];
						this.max_at_i = i;
						this.max_at_j = j;
					}
				} else if(!ltr_only) {
					if(max == null) {
						max = matr[i][j];
					} else if(max < matr[i][j]){
						max = matr[i][j];
						this.max_at_i = i;
						this.max_at_j = j;
					}
				}
			}
		}
		return max;
	}

}
