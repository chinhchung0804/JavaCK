import javax.swing.*;
import java.io.*;
import java.net.*;


public class ChatPanel extends javax.swing.JPanel {

    Socket socket = null;
    BufferedReader bf = null;
    DataOutputStream os = null;
    OutputThread t = null;
    String sender;
    String receiver;
    public File file;
    String staffName;
    String fileToSend;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream Os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    static InputStream in;
    static OutputStream out;

    public ChatPanel(Socket s, String sender, String receiver) {
        initComponents();
        socket = s;
        this.sender = sender;
        this.receiver = receiver;
        try {
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new DataOutputStream(socket.getOutputStream());
            t = new OutputThread(s, txtMessages, sender, receiver);
            t.start();
        } catch (Exception e) {
        }
    }

    public JTextArea getTxtMessages() {
        return this.txtMessages;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelMessage = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnSend = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnFile = new javax.swing.JButton();
        btnSendFile = new javax.swing.JButton();
        btnEmoji = new javax.swing.JButton();
        txtFile = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessages = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        panelMessage.setLayout(new java.awt.GridLayout(1, 0));

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        jScrollPane1.setViewportView(txtMessage);

        panelMessage.add(jScrollPane1);

        jPanel1.setLayout(new java.awt.GridLayout(3, 0));

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jPanel1.add(btnSend);

        jPanel2.setLayout(null);

        jPanel3.setLayout(null);

        btnFile.setText("...");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });
        jPanel3.add(btnFile);
        btnFile.setBounds(0, 0, 40, 32);

        btnSendFile.setText("Send file");
        btnSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFileActionPerformed(evt);
            }
        });
        jPanel3.add(btnSendFile);
        btnSendFile.setBounds(40, 0, 160, 32);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 0, 200, 32);

        btnEmoji.setText("Emoji");
        btnEmoji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmojiActionPerformed(evt);
            }
        });
        jPanel2.add(btnEmoji);
        btnEmoji.setBounds(200, 0, 92, 32);

        jPanel1.add(jPanel2);

        txtFile.setText("File:");
        jPanel1.add(txtFile);

        panelMessage.add(jPanel1);

        add(panelMessage, java.awt.BorderLayout.PAGE_END);

        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane2.setViewportView(txtMessages);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );

        add(jPanel4, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>                        

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (txtMessage.getText().trim().length() == 0) {
            return;
        }
        try {
            os.writeBytes(txtMessage.getText());
            os.write(13);
            os.write(10);
            os.flush();
            this.txtMessages.append("\n" + sender + ": " + txtMessage.getText());
            txtMessage.setText("");
        } catch (Exception e) {
        }
    }                                       

    private void btnEmojiActionPerformed(java.awt.event.ActionEvent evt) {                                         
    }                                        

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {                                        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(this, "Select File");
        file = fileChooser.getSelectedFile();
        if (file != null) {
            if (!file.getName().isEmpty()) {
                btnSendFile.setEnabled(true);
                String str;

                if (txtFile.getText().length() > 30) {
                    String t = file.getPath();
                    str = t.substring(0, 20) + " [...] " + t.substring(t.length() - 20, t.length());
                } else {
                    str = file.getPath();
                }
                txtFile.setText(str);
            }
        }
    }                                       

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) {                                            

        fileToSend = file.getAbsolutePath();
        File file = new File(fileToSend);
        byte[] b = new byte[16 * 1024];

        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            out = socket.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            int count;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }                                           


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnEmoji;
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendFile;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel panelMessage;
    private javax.swing.JTextField txtFile;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextArea txtMessages;
    // End of variables declaration                   
}