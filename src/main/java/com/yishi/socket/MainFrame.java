package com.yishi.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFrame extends Server{
    private static final int defaultPort=46687;
    private static final int clientPort=46686;
    private JFrame jFrame;
    private JPanel jPanel;
    private JPanel leftPannel;
    private JButton jButton;
    private JTextArea jTextArea;
    private JLabel jLabel;
    private JScrollPane jScrollPane;
//    private Server server;
    private Client client;

    public void init(){

        this.client=new Client();

//        this.leftPannel=new JPanel();
//        this.leftPannel.setBounds(100,100,200,400);
        this.jScrollPane=new JScrollPane();
//        textArea=new JTextArea();
        //scrollPane.add(textArea);
        this.jLabel =new JLabel();
//        this.jLabel.setSize(100,100);
        jScrollPane.setViewportView(jLabel);
//        this.jLabel.setBounds(500,300,600,400);
        this.jTextArea =new JTextArea(4,20);
        this.jTextArea.setSize(100,100);

        this.jButton=new JButton("发送");
//        this.jButton.setBounds(500,300,600,400);
        this.jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message=new Message(Message.Type.STRING);
                message.setContent(jTextArea.getText());
                message.setIp("127.0.0.1");
                message.setPort(clientPort);
                try {
                    client.send(message,jLabel);
                    jTextArea.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        this.jPanel=new JPanel();
        this.jPanel.setLayout(new BoxLayout(this.jPanel,BoxLayout.Y_AXIS));
        this.jPanel.setBounds(200,100,600,400);
        this.jPanel.add(jLabel);
//        this.jPanel.add(jScrollPane,BorderLayout.CENTER);
        this.jPanel.add(jTextArea);
        this.jPanel.add(this.jButton);


        this.jFrame=new JFrame();
        this.jFrame.setBounds(100,100,600,400);
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setContentPane(this.jPanel);
        this.jTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    jButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        try {
            startUp(defaultPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MainFrame() {
        super();
        init();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
    @Override
    public  void parseMessage(Message message){
        switch (message.getType()){
            case ACK:
                break;
            case STRING:
//                System.out.println(socket.getInetAddress()+":"+socket.getPort()+"says");
//                System.out.println(message.getContent());
                Pattern pattern= Pattern.compile("<html><body>(.*)<body/><html/>");
                Matcher matcher=pattern.matcher(this.jLabel.getText());
                String content;
                if(matcher.find()){
                    content=matcher.group();
                }else {
                    content="";
                }
                this.jLabel.setText("<html><body>"+content+"<br/>"+message.getIp()+":"+message.getPort()+" :<br/>"+message.getContent()+"<body/><html/>");
                this.jLabel.repaint();
                break;
            case IMG:
                break;
            case FILE:
                break;
        }
    }
}
