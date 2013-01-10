package netstat;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Netstat_Execute {
	
	private static TrayIcon ico;
	private static Integer lastIP = 0;
	private static Integer IP = 0;
	private static boolean notified = true;

	public static void main(String[] args) {
		
			PopupMenu pop = new PopupMenu();
			MenuItem item = new MenuItem("Schlieﬂen");
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
				
			});
			pop.add(item);
		    ico = new TrayIcon(Toolkit.getDefaultToolkit().getImage("C:/Users/Administrator/workspace/Tests/src/Netstat/icon.gif"), "Diablo 2 Port check", pop);
		    ico.setImageAutoSize(true);
		    ico.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(lastIP.equals(0)) {
						Netstat_Execute.ico.displayMessage("Server", "You are not connected to a server.", MessageType.INFO);
					} else if(IP.equals(0)) {
						Netstat_Execute.ico.displayMessage("Server", "Your last server IP was " + lastIP, MessageType.INFO);
					} else {
						Netstat_Execute.ico.displayMessage("Server", "You are on IP " + lastIP, MessageType.INFO);
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
		    	
		    });
		    try {
				SystemTray.getSystemTray().add(ico);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			
			Timer timer = new Timer(); {
				TimerTask task = new TimerTask() {
					public void run() {
					    Process child;
						try {
							child = Runtime.getRuntime().exec("netstat -n");
						    InputStream in = child.getInputStream();
						    int c;
						    String res = "";
						    String buf = "";
						    while ((c = in.read()) != -1) {
						        buf += (char)c;
						    }
						    in.close();
						    if(buf.contains(":4000")) {
						    	res = buf.substring(buf.indexOf(":4000") - 3, buf.indexOf(":4000"));
						    	if(res.contains(".")) {
						    		res = res.substring(res.indexOf(".") + 1);
						    	}
						    	IP = Integer.parseInt(res);
						    	if(!lastIP.equals(IP) && !IP.equals(0)) {
						    		notified = false;
						    	}
						    } else {
						    	IP = 0;
						    }
						} catch (IOException e) {
							e.printStackTrace();
						}
					    
						if(!notified) {
						Netstat_Execute.ico.displayMessage("Server", "You are on IP " + IP, MessageType.INFO);
						lastIP = IP;
						notified = true;
						}
					}
				};
				timer.scheduleAtFixedRate(task, 0, 1000);
			}
	}
}
