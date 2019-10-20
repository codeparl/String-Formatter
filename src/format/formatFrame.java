/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package format;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;

/**
 *
 * @author HASSAN
 */
public class formatFrame extends javax.swing.JFrame {

    /**
     * Creates new form formatFrame
     */
    public formatFrame() {
        initComponents();
        
        disableComponent("tf",integerText,floatText);
        disableComponent("tf",dateText,charText,timeText);
        disableComponent("tf",boolText);
        disableComponent("jb",addBtn,printBtn,displayBtn,resetBtn);
        stringtext.requestFocus();
        setHeaders();
       
        addDocListener(stringtext,integerText,floatText,dateText,charText,timeText,boolText);
        addMouseListenerToFields(integerText,"int");
        addMouseListenerToFields(floatText,"fl");
        addMouseListenerToFields(dateText,"dt");
        
        
    }

    StringBuilder  sb  =  new StringBuilder();
    Formatter  fm  =  new Formatter(sb);
    private void setHeaders(){
    
        JViewport   viewport =  new JViewport();
        
        JPanel   pane  =  new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        
        JLabel[]  lab =  {new JLabel("String"), new JLabel("Integer"), 
            new JLabel("Decimal Point"),new JLabel("Date"),new JLabel("Time")
                ,new JLabel("Character"),new JLabel("Boolean")};
      
        
        for (JLabel lb : lab) {
            pane.add(lb);
            lb.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            pane.add(Box.createHorizontalStrut(38));
        }
        viewport.add(pane);
        
       textAreaScrollPane.setColumnHeader(viewport);
        
    
    
    }
    
  private void displaydata(){
   String format  ="%s%20d%33.4f%37tD%15tT%15C%34b\t%n"; 
   Date now  =  new Date();    
   fm.format(format,stringtext.getText(),Integer.parseInt(integerText.getText())
           ,Double.parseDouble(floatText.getText()), now,now,new Character(charText.getText().charAt(0)),new Boolean(boolText.getText()) );
    sb.append(fm.toString());
    }
    
    
    
    private  void addMouseListenerToFields(final JTextField f,final String opt){
    
      if(opt.equals("int"))
         f.addKeyListener( new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent e) {
              char c = e.getKeyChar();
            if( !String.valueOf(c).matches("[0-9]") && (c != KeyEvent.VK_BACK_SPACE)){ 
                  e.consume();
                  Toolkit.getDefaultToolkit().beep(); }
      }
 });
    
      else if(opt.equals("fl"))
        f.addKeyListener( new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent e) {
              char c = e.getKeyChar();
            if( !String.valueOf(c).matches("[0-9]|\\.") && (c != KeyEvent.VK_BACK_SPACE)){ 
                  e.consume();
                  Toolkit.getDefaultToolkit().beep(); }
      }
 });
      
    else if(opt.equals("dt"))
        f.addKeyListener( new KeyAdapter() {
          @Override
          public void keyTyped(KeyEvent e) {
              char c = e.getKeyChar();
            if( !String.valueOf(c).matches("[0-9]|/") && (c != KeyEvent.VK_BACK_SPACE)){ 
                  e.consume();
                  Toolkit.getDefaultToolkit().beep(); }
      }
 });
    
        
        
   
            }//end method 

          
        
       
       
  
    
  private void addIconToLabel(JLabel l, String opt){
  
      Icon   icon  =  new ImageIcon(getClass().getResource("arr_2.png"));
      
      if(opt.equals("add")){
      l.setIcon(icon);
      l.setHorizontalTextPosition(JLabel.LEFT);
      l.setIconTextGap(l.getText().length()+10);
      }else l.setIcon(null);
  }  
    
  private void disableComponent(String opt,JComponent...f){
  JTextField[]  fields ={stringtext,integerText,floatText,dateText,charText,timeText,boolText};
  
    
      for (JComponent tf : f) {
       
          if(tf instanceof  JTextField && opt.equalsIgnoreCase("tf")){
            JTextField fd  = (JTextField)tf;
            fd.setEditable(false);
              tf.setBackground(new Color(249,249,249));
              
              
          }else if(tf instanceof  JButton && opt.equalsIgnoreCase("jb")){
            JButton b  = (JButton)tf;
            b.setEnabled(false);
              
          }else if(tf instanceof  JTextField && opt.equalsIgnoreCase("tfeb")){
            JTextField fd  = (JTextField)tf;
            disableComponent("tf",Arrays.copyOfRange(f, 1, f.length));
            fd.setText(null);
            fd.setBackground(new Color(249,249,249));
              stringtext.setEditable(true);
              stringtext.setBackground(new Color(255,255,255));
              stringtext.requestFocus();
              addIconToLabel(strLab, "add");
              addIconToLabel(boolLab, "rm");
              
          }
  
  } 
  }//end method
    
  
  private boolean allFieldsAreFilld(JTextField...f){

  return ( !f[0].isEditable())&& 
          ( !f[1].isEditable())&&
          ( !f[2].isEditable())&&
          (!f[3].isEditable())&&
          (!f[4].isEditable())&&
          ( !f[5].isEditable())&&
          (!f[6].isEditable());
  }
  
 private void addDocListener(JTextField...f){
 
     for (JTextField tf : f) {
         tf.getDocument().addDocumentListener(new DocumentListener() {
             @Override
             public void insertUpdate(DocumentEvent e) {
              
                   if(e.getDocument().getLength() > 0)
                       addBtn.setEnabled(true);
           
             }

             @Override
             public void removeUpdate(DocumentEvent e) {
                  if(e.getDocument().getLength() == 0)
                     addBtn.setEnabled(false);
                 
             }

             @Override
             public void changedUpdate(DocumentEvent e) {
                 
                 
             }
         });
     }
     
     
 
 }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        textAreaScrollPane = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        leftPane = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        strLab = new javax.swing.JLabel();
        stringtext = new javax.swing.JTextField();
        intLab = new javax.swing.JLabel();
        integerText = new javax.swing.JTextField();
        decLab = new javax.swing.JLabel();
        floatText = new javax.swing.JTextField();
        dateLab = new javax.swing.JLabel();
        dateText = new javax.swing.JTextField();
        charLab = new javax.swing.JLabel();
        boolLab = new javax.swing.JLabel();
        charText = new javax.swing.JTextField();
        boolText = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        timeLab = new javax.swing.JLabel();
        timeText = new javax.swing.JTextField();
        printBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        displayBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FormatterDemo");
        setResizable(false);

        textAreaScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, java.awt.Color.black), javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        textAreaScrollPane.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(textAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        leftPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "String(%s)", "Integer(%d)", "Floating-point(%f)", "Character(%c)", "Boolean(%b)", "Date(%td/%te%tY)", "Time(%tl:%tM:%tS)" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Format");

        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "Formatted Objects", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 153, 255)), javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2))); // NOI18N

        strLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        strLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        strLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/format/arr_2.png"))); // NOI18N
        strLab.setLabelFor(stringtext);
        strLab.setText("String");
        strLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        strLab.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        strLab.setIconTextGap(50);

        stringtext.setName("strText"); // NOI18N

        intLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        intLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        intLab.setText("Integer");
        intLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        integerText.setName("intger"); // NOI18N

        decLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        decLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        decLab.setText("Decimal point");
        decLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        floatText.setName("floatp"); // NOI18N

        dateLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dateLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dateLab.setText("Date");
        dateLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        dateText.setName("datet"); // NOI18N

        charLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        charLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        charLab.setText("Character");
        charLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        boolLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        boolLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        boolLab.setText("Boolean");
        boolLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        charText.setName("chart"); // NOI18N
        charText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charTextActionPerformed(evt);
            }
        });

        boolText.setName("boolt"); // NOI18N

        addBtn.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        addBtn.setText("Add format");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        timeLab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        timeLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        timeLab.setText("Time");
        timeLab.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        timeText.setName("timet"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(charLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(strLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(intLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decLab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boolLab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(charText)
                    .addComponent(dateText, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(integerText, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stringtext, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boolText)
                    .addComponent(timeText)
                    .addComponent(floatText, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(addBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(strLab)
                    .addComponent(stringtext, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intLab)
                    .addComponent(integerText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decLab)
                    .addComponent(floatText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLab)
                    .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeLab)
                    .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(charLab)
                    .addComponent(charText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boolLab)
                    .addComponent(boolText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addBtn)
                .addGap(12, 12, 12))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {boolLab, boolText, charLab, charText, dateLab, dateText, decLab, floatText, intLab, integerText, strLab, stringtext, timeLab, timeText});

        javax.swing.GroupLayout leftPaneLayout = new javax.swing.GroupLayout(leftPane);
        leftPane.setLayout(leftPaneLayout);
        leftPaneLayout.setHorizontalGroup(
            leftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPaneLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPaneLayout.setVerticalGroup(
            leftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPaneLayout.createSequentialGroup()
                .addGroup(leftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(leftPaneLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        leftPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox1, jLabel1});

        printBtn.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        printBtn.setText("Print ");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        resetBtn.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        displayBtn.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        displayBtn.setText("Display");
        displayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(leftPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(printBtn)
                            .addComponent(resetBtn)
                            .addComponent(displayBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(leftPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void charTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_charTextActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed

   JTextField[]  fields ={stringtext,integerText,floatText,dateText,charText,timeText,boolText};
  
   
     chaecTextField(stringtext,integerText,strLab,intLab);
     chaecTextField(integerText,floatText,intLab,decLab);
     chaecTextField(floatText,dateText,decLab,dateLab);
     chaecTextField(dateText,timeText,dateLab, timeLab);
     chaecTextField(timeText,charText,timeLab,charLab);
     chaecTextField(charText,boolText,charLab,boolLab);
     
     
     if(boolText.getText().length() > 0 && boolText.isEditable()){
        boolText.setEditable(false);
        boolText.setBackground(new Color(249,249,249));
        addBtn.setEnabled(false);
     }
     
      if(allFieldsAreFilld(fields)){
             displayBtn.setEnabled(true);
             disableComponent("tfl",stringtext,integerText,floatText,dateText,charText,timeText,boolText);
             displaydata();
             }
     
     
     
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void displayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayBtnActionPerformed


disableComponent("tfeb",stringtext,integerText,floatText,dateText,charText,timeText,boolText);
displayBtn.setEnabled(false);
jTextArea1.setText(null);
jTextArea1.setText(sb.toString());
if(jTextArea1.getText().length() > 0)
    printBtn.setEnabled(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_displayBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed

if(jTextArea1.getText().length() > 0)
    try {
 boolean printed  =        jTextArea1.print();
        if(printed)
        resetBtn.setEnabled(true);
        
        // TODO add your handling code here:
} catch (PrinterException ex) {
    JOptionPane.showMessageDialog(jTextArea1, "Error occurred while trying to print the document");
}
    }//GEN-LAST:event_printBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed

        
        jTextArea1.setText(null);
        printBtn.setEnabled(false);
        resetBtn.setEnabled(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_resetBtnActionPerformed

    
    private void chaecTextField(JTextField field, JTextField next, JLabel l1, JLabel l2){
         String formt =  (String)jComboBox1.getSelectedItem();
    if(field.getText().length() > 0 && field.isEditable()){
        
       if(formt.substring(0, 2).equalsIgnoreCase(field.getName().substring(0,2) )){
         
        if(field.getName().startsWith("float") && !isValidInput(field,"fl")){
             backToField(field);
                
            }
        
        field.setEditable(false);
        field.setBackground(new Color(249,249,249));
        
        
        addIconToLabel(l1,"rm"); 
        next.setEditable(true);
        next.requestFocus();
        next.setBackground(Color.WHITE);
        addIconToLabel(l2,"add");
        addBtn.setEnabled(false);
        
        }else {
         backToField(field);
           JOptionPane.showMessageDialog(field, "Unsupported format, please try a valid format","FormatterDemo", JOptionPane.ERROR_MESSAGE);
           
           
       } //end if
        
    }
    
    }
    
    void backToField(JTextField  field){
      
           field.requestFocus();
           field.setSelectionColor(Color.DARK_GRAY);
           field.setSelectionEnd(field.getText().length());
           field.setSelectionStart(0);
         
            
    
    }
    
    boolean isValidInput(JTextField  f,String op){
    
        if(op.equals("fl")){
          if( !f.getText().matches("(\\d+\\.\\d+){1}"))
            JOptionPane.showMessageDialog(f, "Must be floating point numbers only","FormatterDemo", JOptionPane.ERROR_MESSAGE);
          return false;
        }
    
    return true;
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
            java.util.logging.Logger.getLogger(formatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formatFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JLabel boolLab;
    private javax.swing.JTextField boolText;
    private javax.swing.JLabel charLab;
    private javax.swing.JTextField charText;
    private javax.swing.JLabel dateLab;
    private javax.swing.JTextField dateText;
    private javax.swing.JLabel decLab;
    private javax.swing.JButton displayBtn;
    private javax.swing.JTextField floatText;
    private javax.swing.JLabel intLab;
    private javax.swing.JTextField integerText;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel leftPane;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel strLab;
    private javax.swing.JTextField stringtext;
    private javax.swing.JScrollPane textAreaScrollPane;
    private javax.swing.JLabel timeLab;
    private javax.swing.JTextField timeText;
    // End of variables declaration//GEN-END:variables
}
