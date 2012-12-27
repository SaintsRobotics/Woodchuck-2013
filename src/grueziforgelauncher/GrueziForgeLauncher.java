/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grueziforgelauncher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author liam_000
 */
public class GrueziForgeLauncher extends javax.swing.JFrame {

    /**
     * Creates new form GrueziForgeLauncher
     */
    public GrueziForgeLauncher() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GrueziForge Launcher");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GrueziForge Launcher");

        jTextField1.setToolTipText("");

        jButton1.setText("Log In");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        jButton2.setText("Update");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        String s = MinecraftLogin.getSessionID(jTextField1.getText(), jPasswordField1.getText());
        if (s.contains("Bad login")) {
            JOptionPane.showMessageDialog(null, "Incorrect username or Password");
        } else if (s.contains("User not premium")) {
            JOptionPane.showMessageDialog(null, "Account not premium");
        } else {
            String[] split = s.split(":");
            String sessionID = split[3];
            System.out.println(split[3]);
//            try {
//               String[] env = new String[0];
//               File operatingdir = new File(System.getenv("APPDATA").concat("\\.grueziforge\\minecraft\\"));
//                Process p = Runtime.getRuntime().exec("java  -Xms512m -Xmx1024m -cp launcher ",env,operatingdir);
//                
//                System.out.println("java  -Xms512m -Xmx1024m -jar ".concat(System.getenv("APPDATA").concat("\\.grueziforge\\minecraft\\MultiMCLauncher.jar ")).concat(jTextField1.getText()).concat(" ").concat(sessionID).concat(" GrueziForge 854x480"));
//            } catch (IOException ex) {
//                Logger.getLogger(GrueziForgeLauncher.class.getName()).log(Level.SEVERE, null, ex);
//            }
            String[] args = {split[2],split[3],"GrueziForge","",""};
            setVisible(false);
            MultiMCLauncher.launch(args);
            
            
            
            
       }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
jButton1.setEnabled(false);
jButton2.setEnabled(false);
jTextField1.setEnabled(false);
jPasswordField1.setEnabled(false);
jLabel1.setText("Updating...");
String dataDir = System.getenv("APPDATA").concat("\\.grueziforge\\");
        try {
            
            File f = new File(dataDir);
            System.out.println(dataDir);
            if (!f.exists()) {
                System.out.println(f.mkdir());
            }


            System.out.println("Connecting...");
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://bricinmc.bluepyjama.com/updates/latest.zip");
            HttpResponse response = client.execute(get);

            InputStream input = null;
            OutputStream output = null;
            byte[] buffer = new byte[1024];

            try {
                System.out.println("Downloading file...");
                input = response.getEntity().getContent();
                output = new FileOutputStream(new File(dataDir.concat("latest.zip")));
                for (int length; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
                System.out.println("File successfully downloaded!");
            } finally {
                if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
                if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
            }



        } catch (IOException ex) {
            Logger.getLogger(GrueziForgeLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }

File minecraftdir = new File(dataDir.concat("minecraft\\"));
if(!minecraftdir.exists()){
    minecraftdir.mkdir();
}


UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(dataDir.concat("latest.zip"), dataDir.concat("minecraft\\"));
        } catch (IOException ex) {
            Logger.getLogger(GrueziForgeLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
JOptionPane.showMessageDialog(null, "Client updated!");


        jButton1.setEnabled(true);
jButton2.setEnabled(true);
jTextField1.setEnabled(true);
jPasswordField1.setEnabled(true);
jLabel1.setText("GrueziForge Launcher");
    }//GEN-LAST:event_jButton2MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Get the size of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
    // Determine the new location of the window
    int w = this.getSize().width;
    int h = this.getSize().height;
    int x = (dim.width-w)/2;
    int y = (dim.height-h)/2;
    
    // Move the window
    this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    
    
    
    
    public static final void writeFile(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	public static void unzipMyZip(String zipFileName,
			String directoryToExtractTo) {
		Enumeration entriesEnum;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipFileName);
			entriesEnum = zipFile.entries();
			
			File directory= new File(directoryToExtractTo);
			
			/**
			 * Check if the directory to extract to exists
			 */
			if(!directory.exists())
			{
				/** 
				 * If not, create a new one.
				 */
				new File(directoryToExtractTo).mkdir();
				System.err.println("...Directory Created -"+directoryToExtractTo);
			}
			while (entriesEnum.hasMoreElements()) {
				try {
					ZipEntry entry = (ZipEntry) entriesEnum.nextElement();

					if (entry.isDirectory()) {
						/** 
						 * Currently not unzipping the directory structure. 
						 * All the files will be unzipped in a Directory
						 *  
						 **/
					} else {

						System.err.println("Extracting file: "
								+ entry.getName());
						/**
						 * The following logic will just extract the file name
						 * and discard the directory
						 */
						int index = 0;
						String name = entry.getName();
						index = entry.getName().lastIndexOf("/");
						if (index > 0 && index != name.length())
							name = entry.getName().substring(index + 1);

						System.out.println(name);

						writeFile(zipFile.getInputStream(entry),
								new BufferedOutputStream(new FileOutputStream(
										directoryToExtractTo + name)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			zipFile.close();
		} catch (IOException ioe) {
			System.err.println("Some Exception Occurred:");
			ioe.printStackTrace();
			return;
		}
	}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GrueziForgeLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrueziForgeLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrueziForgeLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrueziForgeLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrueziForgeLauncher().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
