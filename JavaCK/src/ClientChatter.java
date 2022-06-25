		
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ClientChatter extends javax.swing.JFrame {

Socket mngSocket = null;
BufferedReader bf = null;
DataOutputStream os = null;
OutputThread t = null;
String mngIP = "";
String staffName = "";
int mngPort = 0;
static InputStream in;
static OutputStream out;

public ClientChatter() {
    initComponents();
    this.setSize(600, 300);
}

@SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    txtStaff = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    txtSeverIP = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    txtSeverPort = new javax.swing.JTextField();
    btnCode = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jPanel1.setLayout(new java.awt.GridLayout());

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("Staff");
    jLabel1.setToolTipText("");
    jPanel1.add(jLabel1);

    txtStaff.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtStaffActionPerformed(evt);
        }
    });
    jPanel1.add(txtStaff);

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("Mng IP");
    jPanel1.add(jLabel2);
    jPanel1.add(txtSeverIP);

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("Port:");
    jPanel1.add(jLabel3);
    jPanel1.add(txtSeverPort);

    btnCode.setText("Connect");
    btnCode.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCodeActionPerformed(evt);
        }
    });
    jPanel1.add(btnCode);

    getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

    pack();
}// </editor-fold>                        

private void txtStaffActionPerformed(java.awt.event.ActionEvent evt) {                                         
}                                        

private void btnCodeActionPerformed(java.awt.event.ActionEvent evt) {                                        
    mngIP = this.txtSeverIP.getText();
    mngPort = Integer.parseInt(this.txtSeverPort.getText());
    staffName = this.txtStaff.getText();
    try {
        mngSocket = new Socket(mngIP, mngPort);
        if (mngSocket != null) {
            ChatPanel p = new ChatPanel(mngSocket, staffName, "Manager");
            this.getContentPane().add(p);
            p.getTxtMessages().append("Manager is running\n");
            p.updateUI();

            bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
            os = new DataOutputStream(mngSocket.getOutputStream());
            os.writeBytes("Staff: " + staffName);
            os.write(13);
            os.write(10);
            os.flush();
            
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Manager is not running");
        System.exit(0);
    }
}                                       

public static void main(String args[]) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new ClientChatter().setVisible(true);
        }
    });
}

void download(String str) {
    File file = new File(str);

    byte[] b = new byte[16 * 1024];

    try {
        in = new FileInputStream(file);
    } catch (FileNotFoundException e1) {
        e1.printStackTrace();
    }

    try {
        out = mngSocket.getOutputStream();
    } catch (IOException e1) {
        e1.printStackTrace();
    }

    try {
        int count;
        while ((count = in.read(b)) > 0) {
            out.write(b, 0, count);
        }
    } catch (IOException e1) {
        e1.printStackTrace();
    }
}

// Variables declaration - do not modify                     
private javax.swing.JButton btnCode;
private javax.swing.JLabel jLabel1;
private javax.swing.JLabel jLabel2;
private javax.swing.JLabel jLabel3;
private javax.swing.JPanel jPanel1;
private javax.swing.JTextField txtSeverIP;
private javax.swing.JTextField txtSeverPort;
private javax.swing.JTextField txtStaff;
private File file;
private void InputFile(File file) throws IOException {
	  if (!file.exists()) {
	    throw new IOException("File not found: " + file.getAbsolutePath());
	  }
	  this.file = file;
	}
// End of variables declaration                   
}