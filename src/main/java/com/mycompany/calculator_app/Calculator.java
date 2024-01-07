/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.calculator_app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author susmit
 */
class Expression {
        static class WrongExpressionException extends Exception{
            
        } 
        static boolean isOperator(char c){
                return c=='+'||c=='-'||c=='*'||c=='/'; 
        }
        static boolean isOperand(char c){
                return c=='.'||(c>='0'&&c<='9');
        }
        static double operate(double op1,double op2,char op){
                switch(op){
                        case '+' : return op1+op2;
                        case '-' : return op1-op2;
                        case '*' : return op1*op2;
                        default : return op1/op2;
                }
        }
        static int i=0; static String proper="";
        static double calculate(String s)throws WrongExpressionException{
                //System.out.println(s);
                double lastnum1=0,lastnum2=0,currnum=0; double currfrac=1; 
                boolean dot=false;
                char lastop='+';
                while(i<s.length()){
                        if(isOperand(s.charAt(i))){
                            if(s.charAt(i)=='.'){ 
                                if(dot) throw new WrongExpressionException(); dot=true; 
                                if(i==s.length()-1) lastnum2=operate(lastnum2,currnum,lastop); 
                                i++; continue;
                            }
                            if(dot){
                                currfrac/=(double)10;
                                currnum+=(double)(currfrac*(s.charAt(i)-'0'));
                                System.out.println(currfrac+" "+currnum);
                            }
                            else
                                currnum=10*currnum+(s.charAt(i)-'0');
                            //System.out.println(currnum);
                        }
                        if(s.charAt(i)=='('){ i++; currnum=calculate(s); if(i<s.length()-1) i++; }
                        if(isOperator(s.charAt(i))){
                                if(s.charAt(i)=='+'||s.charAt(i)=='-'){
                                        if(lastop=='*'||lastop=='/'){
                                                lastnum2=operate(lastnum2,currnum,lastop);
                                                currnum=lastnum2; lastop='+'; lastnum2=0;
                                        }
                                        lastnum1=operate(lastnum1,currnum,lastop);
                                }
                                if(s.charAt(i)=='*'||s.charAt(i)=='/'){
                                        if(lastop=='*'||lastop=='/')
                                            lastnum2=operate(lastnum2,currnum,lastop);
                                        else if(lastop=='-') lastnum2=-currnum;
                                        else lastnum2=currnum;
                                }
                                lastop=s.charAt(i); currnum=0; currfrac=1; dot=false;
                        }
                        if(i==s.length()-1||s.charAt(i)==')'){
                                //System.out.println(lastnum2+" "+currnum);
                                lastnum2=operate(lastnum2,currnum,lastop); dot=false;
                                if(s.charAt(i)==')') break;
                        }
                        i++;
                }
                return lastnum1+lastnum2;
        }
        static void format(String exp){

                int parenth=0;

                for(;i<exp.length();i++){
                        char c=exp.charAt(i);

                        if((c=='+'||c=='-')&&(i>0&&isOperator(exp.charAt(i-1)))){ proper+='('+""+c; parenth++; continue; }
                        if(c=='('){ proper+=c; i++; format(exp); continue; }
                        if(!isOperand(c)){ while(parenth>0){ proper+=')'; parenth--; } }
                        proper+=c;
                        if(c==')') break;
                }
                while(parenth>0){ proper+=')'; parenth--; }
        }
        static boolean validate(String exp){
                int parenth=0;
                for(int i=0;i<exp.length();i++){
                        if(exp.charAt(i)=='('){ if(i>0&&(isOperand(exp.charAt(i-1))||exp.charAt(i-1)==')')) return false; parenth++; }
                        else if(exp.charAt(i)==')'){
                                if(i==0||isOperator(exp.charAt(i-1))||exp.charAt(i-1)=='('){ //System.out.println(exp.charAt(i-1)+"y1"); 
                                return false; }
                                parenth--;  }
                        else if(exp.charAt(i)=='+'||exp.charAt(i)=='-'){ if(i==exp.length()-1){ //System.out.println("y2"); 
                            return false; } }
                        else if(exp.charAt(i)=='*'||exp.charAt(i)=='/'){
                                if(i==0||i==exp.length()-1||isOperator(exp.charAt(i-1))||exp.charAt(i-1)=='('){ //System.out.println("y3"); 
                                    return false; }
                        }
                }
                return parenth==0;
        }
        static double evaluate(String exp) throws WrongExpressionException {
                i=0; proper="";
                format(exp);
                i=0;
                //System.out.println(proper);
                if(!validate(proper)) throw new WrongExpressionException();
                //System.out.println("yes");
                try{
                return calculate(proper);
                }
                catch(WrongExpressionException e){ throw e; }
        }         
}
                                                                                    
public class Calculator extends javax.swing.JFrame {

    /**
     * Creates new form Calculator
     */
    
    double ans,num; int cal; boolean execute=false,exception=false,cursor_lock=false; //switched=false;
    
    int caretpos=0;
    
    public Calculator() {
        //System.out.println(6.0+0.02+0.001*2.0);
        initComponents();
        //this.getContentPane().setBackground(Color.red);
        jRadioButton1.setEnabled(false);
        jLabel1.setText("");
        choice1.add("Basic Mode");
        choice1.add("Scientific Mode");
        choice1.add("Conversion Mode");
        choice1.add("Logical Mode");
        choice1.add("Big Integer op.");
        //choice1.add("Golden ratio cal.");
        choice1.select(0);
        //jRadioButton3.setEnabled(false);
        //jTextField1.getCaret().setVisible(true);
        //System.out.println(jTextField1.getCaretPosition());
        //jTextField1.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,KeyEvent.CTRL_DOWN_MASK, false), "ctrlleft");
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,KeyEvent.CTRL_DOWN_MASK, false), "ctrlleft");
        jTextField1.getActionMap().put("ctrlleft", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cursor_lock) return ;
                jTextField1.grabFocus(); jTextField1.getCaret().setVisible(true);
                int pos=jTextField1.getCaretPosition(); if(pos>0) jTextField1.setCaretPosition(pos-1);
            }
       });
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL,0, true), "ctrldown");
        jTextField1.getActionMap().put("ctrldown", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("c");
                if(!cursor_lock) jTextField1.getCaret().setVisible(false);
                //else 
                    //jTextField1.getCaret().setVisible(true);
            }
       });
        jTextField1.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,KeyEvent.CTRL_DOWN_MASK, false), "ctrlright");
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,KeyEvent.CTRL_DOWN_MASK, false), "ctrlright");
        jTextField1.getActionMap().put("ctrlright", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cursor_lock) return;
                jTextField1.grabFocus(); jTextField1.getCaret().setVisible(true);
                int pos=jTextField1.getCaretPosition(); if(pos<jTextField1.getText().length()) jTextField1.setCaretPosition(pos+1);
            }
       });
        jTextField1.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK, false), "caretlock");
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK, false), "caretlock");
        jTextField1.getActionMap().put("caretlock", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("a");
                //jTextField1.grabFocus();
                if(!cursor_lock){
                    jTextField1.getCaret().setVisible(true);
                    jTextField1.setFocusable(false);
                    jButton1.grabFocus(); 
                    execute=false;
                    cursor_lock=true; 
                }
                else{
                    cursor_lock=false; jTextField1.setFocusable(true);
                    jTextField1.grabFocus();
                }
            }
       });
        jTextField1.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK, true), "caretdown");
        jTextField1.getInputMap(JTextField.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL,0, true), "caretdown");
        //jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK, true), "caretdown");
        jTextField1.getActionMap().put("caretdown", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cursor_lock) jTextField1.getCaret().setVisible(true);
            }
        });
    }
    
    public void arithm_operation() {
        
        /*switch(cal)
        {
            case 1:
                ans = num + Double.parseDouble(jTextField1.getText());
                break;
            
            case 2:
                ans = num - Double.parseDouble(jTextField1.getText());
                break;
            
            case 3:
                ans = num * Double.parseDouble(jTextField1.getText());
                break;
            
            case 4:
                ans = num / Double.parseDouble(jTextField1.getText());
                break;
                      
        }*/
        String exp=jTextField1.getText();
        try{
        ans=Expression.evaluate(exp);
        if(ans==Double.POSITIVE_INFINITY||ans==Double.NEGATIVE_INFINITY||Double.isNaN(ans)) throw new RuntimeException();
        if(!jLabel1.getText().isEmpty()){
            String text=jLabel1.getText();
            int len=text.length(); double n=Double.parseDouble(text.substring(0, len-1));
            switch(text.charAt(len-1)){
                case '+':
                ans = n + ans;
                break;
            
            case '-':
                ans = n - ans;
                break;
            
            case '*':
                ans = n * ans;
                break;
            
            case '/':
                if(ans==0) throw new RuntimeException();
                ans = n / ans;
                break;
            }
        }    
        if((double)((long)ans)==ans)
            jTextField1.setText(Long.toString((long)ans));
        else        
            jTextField1.setText(Double.toString(ans));
        }
        /*catch(Expression.WrongExpressionException e){ 
            jTextField1.setText("Wrong Expression!"); exception=true; }*/
        catch(Exception e){ exception=true; 
            if(e instanceof Expression.WrongExpressionException) jTextField1.setText("Wrong Expression!");
            else if(e instanceof RuntimeException)    jTextField1.setText("Math Error!");
            int delay = 1000; //milliseconds
            java.awt.event.ActionListener taskPerformer = new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                //...Perform a task...
                jTextField1.setText(exp); exception=false;
                }
            };
            javax.swing.Timer timer=new javax.swing.Timer(delay, taskPerformer);
            timer.setRepeats(false); timer.start();
        //java.util.concurrent.TimeUnit.SECONDS.sleep(1);//Thread.sleep(2000); // 
        execute=false;
        }
    }
    public void disable() {
        jTextField1.setEnabled(false);
        
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(false);
        jRadioButton3.setEnabled(false);
        
        choice1.setEnabled(false);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
        jButton8.setEnabled(false);
        jButton9.setEnabled(false);
        jButton10.setEnabled(false);
        jButton11.setEnabled(false);
        jButton12.setEnabled(false);
        jButton13.setEnabled(false);
        jButton14.setEnabled(false);
        jButton15.setEnabled(false);
        jButton16.setEnabled(false);
        jButton18.setEnabled(false);
        jButton19.setEnabled(false);
    }
    
    public void enable() {
        jTextField1.setEnabled(true);
        
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(true);
        jRadioButton3.setEnabled(true);
        
        if(jRadioButton3.isSelected()==false) choice1.setEnabled(true);
        
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        jButton8.setEnabled(true);
        jButton9.setEnabled(true);
        jButton10.setEnabled(true);
        jButton11.setEnabled(true);
        jButton12.setEnabled(true);
        jButton13.setEnabled(true);
        jButton14.setEnabled(true);
        jButton15.setEnabled(true);
        jButton16.setEnabled(true);
        jButton18.setEnabled(true);
        jButton19.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        choice1 = new java.awt.Choice();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTextField2 = new javax.swing.JTextField();

        jMenuItem1.setText("open in new window");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("open in this window");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("lock mode switching");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator-Basic");
        setLocation(new java.awt.Point(500, 180));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1CaretUpdate(evt);
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false), "backsp");
        jTextField1.getActionMap().put("backsp", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if(jTextField1.getCaret().isVisible())
                jButton1.grabFocus(); //jButton1ActionPerformed1();
            }
        });
        jTextField1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK, false), "clearup");
        jTextField1.getActionMap().put("clearup", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton2.getModel().setPressed(true);
                jButton2.getModel().setArmed(true);
                jButton2.grabFocus(); //jButton15.doClick();
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("ON");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jRadioButton2.setText("OFF");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setText("(  <-  )");
        jButton1.setToolTipText("");
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, false), "backup");
        jButton1.getActionMap().put("backup", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1.grabFocus(); jButton1ActionPerformed1();
            }
        });
        /*jButton1.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0, true), "backdown");
        jButton1.getActionMap().put("backdown", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1.getModel().setPressed(false);
                jButton1.getModel().setArmed(false);
            }});*/
            jButton1.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_9, KeyEvent.SHIFT_DOWN_MASK, false), "left_parenthup");
            jButton1.getActionMap().put("left_parenthup", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jButton1.grabFocus(); jButton1ActionPerformed2(true);
                }
            });
            jButton1.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.SHIFT_DOWN_MASK, false), "right_parenthup");
            jButton1.getActionMap().put("right_parenthup", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jButton1.grabFocus(); jButton1ActionPerformed2(false);
                }
            });

            jButton2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
            jButton2.setText("C");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
            jButton2.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK, false), "clearup");
            jButton2.getActionMap().put("clearup", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jButton2.getModel().setPressed(true);
                    jButton2.getModel().setArmed(true);
                    jButton2.grabFocus(); //jButton15.doClick();
                }
            });
            jButton2.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK, true), "cleardown");
            jButton2.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, true), "cleardown");
            jButton2.getActionMap().put("cleardown", new javax.swing.AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jButton2.getModel().setPressed(false);
                    jButton2.getModel().setArmed(false);
                }});

                jButton3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                jButton3.setText("+");
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton3ActionPerformed(evt);
                    }
                });
                jButton3.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK, false), "plusup");
                jButton3.getActionMap().put("plusup", new javax.swing.AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jButton3.getModel().setPressed(true);
                        jButton3.getModel().setArmed(true);
                        jButton3.grabFocus(); //jButton15.doClick();
                    }
                });
                jButton3.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK, true), "plusdown");
                jButton3.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0, true), "plusdown");
                jButton3.getActionMap().put("plusdown", new javax.swing.AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jButton3.getModel().setPressed(false);
                        jButton3.getModel().setArmed(false);
                    }});

                    jButton4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                    jButton4.setText("7");
                    jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton4ActionPerformed(evt);
                        }
                    });
                    jButton4.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0, false), "sevenup");
                    jButton4.getActionMap().put("sevenup", new javax.swing.AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jButton4.getModel().setPressed(true);
                            jButton4.getModel().setArmed(true);
                            jButton4.grabFocus(); //jButton15.doClick();
                        }
                    });
                    jButton4.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0, true), "sevendown");
                    jButton4.getActionMap().put("sevendown", new javax.swing.AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jButton4.getModel().setPressed(false);
                            jButton4.getModel().setArmed(false);
                        }});

                        jButton5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                        jButton5.setText("8");
                        jButton5.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton5ActionPerformed(evt);
                            }
                        });
                        jButton5.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, false), "eightup");
                        jButton5.getActionMap().put("eightup", new javax.swing.AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                jButton5.getModel().setPressed(true);
                                jButton5.getModel().setArmed(true);
                                jButton5.grabFocus(); //jButton15.doClick();
                            }
                        });
                        jButton5.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, true), "eightdown");
                        jButton5.getActionMap().put("eightdown", new javax.swing.AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                jButton5.getModel().setPressed(false);
                                jButton5.getModel().setArmed(false);
                            }});

                            jButton6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                            jButton6.setText("9");
                            jButton6.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    jButton6ActionPerformed(evt);
                                }
                            });
                            jButton6.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0, false), "nineup");
                            jButton6.getActionMap().put("nineup", new javax.swing.AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    jButton6.getModel().setPressed(true);
                                    jButton6.getModel().setArmed(true);
                                    jButton6.grabFocus(); //jButton15.doClick();
                                }
                            });
                            jButton6.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0, true), "ninedown");
                            jButton6.getActionMap().put("ninedown", new javax.swing.AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    jButton6.getModel().setPressed(false);
                                    jButton6.getModel().setArmed(false);
                                }});

                                jButton7.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
                                jButton7.setText("-");
                                jButton7.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton7ActionPerformed(evt);
                                    }
                                });
                                jButton7.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0, false), "minusup");
                                jButton7.getActionMap().put("minusup", new javax.swing.AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        jButton7.getModel().setPressed(true);
                                        jButton7.getModel().setArmed(true);
                                        jButton7.grabFocus(); //jButton15.doClick();
                                    }
                                });
                                jButton7.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0, true), "minusdown");
                                jButton7.getActionMap().put("minusdown", new javax.swing.AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        jButton7.getModel().setPressed(false);
                                        jButton7.getModel().setArmed(false);
                                    }});

                                    jButton8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                    jButton8.setText("*");
                                    jButton8.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            jButton8ActionPerformed(evt);
                                        }
                                    });
                                    jButton8.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK, false), "starup");
                                    jButton8.getActionMap().put("starup", new javax.swing.AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jButton8.getModel().setPressed(true);
                                            jButton8.getModel().setArmed(true);
                                            jButton8.grabFocus(); //jButton15.doClick();
                                        }
                                    });
                                    jButton8.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK, true), "stardown");
                                    jButton8.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, true), "stardown");
                                    jButton8.getActionMap().put("stardown", new javax.swing.AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jButton8.getModel().setPressed(false);
                                            jButton8.getModel().setArmed(false);
                                        }});

                                        jButton9.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                        jButton9.setText("6");
                                        jButton9.addActionListener(new java.awt.event.ActionListener() {
                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton9ActionPerformed(evt);
                                            }
                                        });
                                        jButton9.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0, false), "sixup");
                                        jButton9.getActionMap().put("sixup", new javax.swing.AbstractAction() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                jButton9.getModel().setPressed(true);
                                                jButton9.getModel().setArmed(true);
                                                jButton9.grabFocus(); //jButton15.doClick();
                                            }
                                        });
                                        jButton9.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0, true), "sixdown");
                                        jButton9.getActionMap().put("sixdown", new javax.swing.AbstractAction() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                jButton9.getModel().setPressed(false);
                                                jButton9.getModel().setArmed(false);
                                            }});

                                            jButton10.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                            jButton10.setText("5");
                                            jButton10.addActionListener(new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    jButton10ActionPerformed(evt);
                                                }
                                            });
                                            jButton10.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, false), "fiveup");
                                            jButton10.getActionMap().put("fiveup", new javax.swing.AbstractAction() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    jButton10.getModel().setPressed(true);
                                                    jButton10.getModel().setArmed(true);
                                                    jButton10.grabFocus(); //jButton15.doClick();
                                                }
                                            });
                                            jButton10.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, true), "fivedown");
                                            jButton10.getActionMap().put("fivedown", new javax.swing.AbstractAction() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    jButton10.getModel().setPressed(false);
                                                    jButton10.getModel().setArmed(false);
                                                }});

                                                jButton11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                jButton11.setText("4");
                                                jButton11.addActionListener(new java.awt.event.ActionListener() {
                                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                        jButton11ActionPerformed(evt);
                                                    }
                                                });
                                                jButton11.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0, false), "fourup");
                                                jButton11.getActionMap().put("fourup", new javax.swing.AbstractAction() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                        jButton11.getModel().setPressed(true);
                                                        jButton11.getModel().setArmed(true);
                                                        jButton11.grabFocus(); //jButton15.doClick();
                                                    }
                                                });
                                                jButton11.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0, true), "fourdown");
                                                jButton11.getActionMap().put("fourdown", new javax.swing.AbstractAction() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                        jButton11.getModel().setPressed(false);
                                                        jButton11.getModel().setArmed(false);
                                                    }});

                                                    jButton12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                    jButton12.setText("/");
                                                    jButton12.addActionListener(new java.awt.event.ActionListener() {
                                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                            jButton12ActionPerformed(evt);
                                                        }
                                                    });
                                                    jButton12.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, false), "slashup");
                                                    jButton12.getActionMap().put("slashup", new javax.swing.AbstractAction() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            jButton12.getModel().setPressed(true);
                                                            jButton12.getModel().setArmed(true);
                                                            jButton12.grabFocus(); //jButton15.doClick();
                                                        }
                                                    });
                                                    jButton12.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true), "slashdown");
                                                    jButton12.getActionMap().put("slashdown", new javax.swing.AbstractAction() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {
                                                            jButton12.getModel().setPressed(false);
                                                            jButton12.getModel().setArmed(false);
                                                        }});

                                                        jButton13.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                        jButton13.setText("3");
                                                        jButton13.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jButton13ActionPerformed(evt);
                                                            }
                                                        });
                                                        jButton13.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, false), "threeup");
                                                        jButton13.getActionMap().put("threeup", new javax.swing.AbstractAction() {
                                                            @Override
                                                            public void actionPerformed(ActionEvent e) {
                                                                jButton13.getModel().setPressed(true);
                                                                jButton13.getModel().setArmed(true);
                                                                jButton13.grabFocus(); //jButton15.doClick();
                                                            }
                                                        });
                                                        jButton13.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, true), "threedown");
                                                        jButton13.getActionMap().put("threedown", new javax.swing.AbstractAction() {
                                                            @Override
                                                            public void actionPerformed(ActionEvent e) {
                                                                jButton13.getModel().setPressed(false);
                                                                jButton13.getModel().setArmed(false);
                                                            }});

                                                            jButton14.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                            jButton14.setText("2");
                                                            jButton14.addActionListener(new java.awt.event.ActionListener() {
                                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                    jButton14ActionPerformed(evt);
                                                                }
                                                            });
                                                            jButton14.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "oneup");
                                                            jButton14.getActionMap().put("oneup", new javax.swing.AbstractAction() {
                                                                @Override
                                                                public void actionPerformed(ActionEvent e) {
                                                                    jButton14.getModel().setPressed(true);
                                                                    jButton14.getModel().setArmed(true);
                                                                    jButton14.grabFocus(); //jButton15.doClick();
                                                                    //jButton15ActionPerformed(e);
                                                                    //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                                                                }
                                                            });
                                                            jButton14.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, true), "onedown");
                                                            jButton14.getActionMap().put("onedown", new javax.swing.AbstractAction() {
                                                                @Override
                                                                public void actionPerformed(ActionEvent e) {
                                                                    jButton14.getModel().setPressed(false);
                                                                    jButton14.getModel().setArmed(false);
                                                                }});

                                                                jButton15.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                                jButton15.setText("1");
                                                                jButton15.addActionListener(new java.awt.event.ActionListener() {
                                                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                        jButton15ActionPerformed(evt);
                                                                    }
                                                                });
                                                                jButton15.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "oneup");
                                                                jButton15.getActionMap().put("oneup", new javax.swing.AbstractAction() {
                                                                    @Override
                                                                    public void actionPerformed(ActionEvent e) {
                                                                        jButton15.getModel().setPressed(true);
                                                                        jButton15.getModel().setArmed(true);
                                                                        jButton15.grabFocus(); //jButton15.doClick();
                                                                        //jButton15ActionPerformed(e);
                                                                        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                                                                    }
                                                                });
                                                                jButton15.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, true), "onedown");
                                                                jButton15.getActionMap().put("onedown", new javax.swing.AbstractAction() {
                                                                    @Override
                                                                    public void actionPerformed(ActionEvent e) {
                                                                        jButton15.getModel().setPressed(false);
                                                                        jButton15.getModel().setArmed(false);
                                                                    }});

                                                                    jButton16.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                                    jButton16.setText("=");
                                                                    jButton16.addActionListener(new java.awt.event.ActionListener() {
                                                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                            jButton16ActionPerformed(evt);
                                                                        }
                                                                    });
                                                                    jButton16.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0, false), "equalup");
                                                                    jButton16.getActionMap().put("equalup", new javax.swing.AbstractAction() {
                                                                        @Override
                                                                        public void actionPerformed(ActionEvent e) {
                                                                            jButton16.getModel().setPressed(true);
                                                                            jButton16.getModel().setArmed(true);
                                                                            jButton16.grabFocus(); //jButton15.doClick();
                                                                        }
                                                                    });
                                                                    jButton16.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0, true), "equaldown");
                                                                    jButton16.getActionMap().put("equaldown", new javax.swing.AbstractAction() {
                                                                        @Override
                                                                        public void actionPerformed(ActionEvent e) {
                                                                            jButton16.getModel().setPressed(false);
                                                                            jButton16.getModel().setArmed(false);
                                                                        }});

                                                                        jButton18.setFont(new java.awt.Font("Ubuntu", 0, 30)); // NOI18N
                                                                        jButton18.setText(".");
                                                                        jButton18.addActionListener(new java.awt.event.ActionListener() {
                                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                                jButton18ActionPerformed(evt);
                                                                            }
                                                                        });
                                                                        jButton18.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0, false), "deciup");
                                                                        jButton18.getActionMap().put("deciup", new javax.swing.AbstractAction() {
                                                                            @Override
                                                                            public void actionPerformed(ActionEvent e) {
                                                                                jButton18.getModel().setPressed(true);
                                                                                jButton18.getModel().setArmed(true);
                                                                                jButton18.grabFocus(); //jButton15.doClick();
                                                                            }
                                                                        });
                                                                        jButton18.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0, true), "decidown");
                                                                        jButton18.getActionMap().put("decidown", new javax.swing.AbstractAction() {
                                                                            @Override
                                                                            public void actionPerformed(ActionEvent e) {
                                                                                jButton18.getModel().setPressed(false);
                                                                                jButton18.getModel().setArmed(false);
                                                                            }});

                                                                            jButton19.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
                                                                            jButton19.setText("0");
                                                                            jButton19.setMaximumSize(new java.awt.Dimension(29, 49));
                                                                            jButton19.setMinimumSize(new java.awt.Dimension(29, 29));
                                                                            jButton19.addActionListener(new java.awt.event.ActionListener() {
                                                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                                    jButton19ActionPerformed(evt);
                                                                                }
                                                                            });
                                                                            jButton19.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0, false), "zeroup");
                                                                            jButton19.getActionMap().put("zeroup", new javax.swing.AbstractAction() {
                                                                                @Override
                                                                                public void actionPerformed(ActionEvent e) {
                                                                                    jButton19.getModel().setPressed(true);
                                                                                    jButton19.getModel().setArmed(true);
                                                                                    jButton19.grabFocus(); //jButton15.doClick();
                                                                                }
                                                                            });
                                                                            jButton19.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0, true), "zerodown");
                                                                            jButton19.getActionMap().put("zerodown", new javax.swing.AbstractAction() {
                                                                                @Override
                                                                                public void actionPerformed(ActionEvent e) {
                                                                                    jButton19.getModel().setPressed(false);
                                                                                    jButton19.getModel().setArmed(false);
                                                                                }});

                                                                                jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
                                                                                jLabel1.setForeground(java.awt.Color.red);
                                                                                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                                                                                jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                                                                                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                                                                                        jLabel1MouseMoved(evt);
                                                                                    }
                                                                                });

                                                                                jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
                                                                                jLabel3.setForeground(new java.awt.Color(249, 84, 46));
                                                                                jLabel3.setText("E X  P R E S S");

                                                                                jSeparator1.setBackground(new java.awt.Color(68, 150, 231));
                                                                                jSeparator1.setForeground(new java.awt.Color(233, 89, 63));
                                                                                jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                                                                                jSeparator1.setOpaque(true);

                                                                                choice1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                                                                                choice1.setEnabled(false);

                                                                                jRadioButton3.setSelected(true);
                                                                                jRadioButton3.setToolTipText("Unlock mode switching");
                                                                                jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
                                                                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                                                        jRadioButton3MouseEntered(evt);
                                                                                    }
                                                                                });
                                                                                jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
                                                                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                                        jRadioButton3ActionPerformed(evt);
                                                                                    }
                                                                                });

                                                                                jTextField2.setEditable(false);
                                                                                jTextField2.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
                                                                                jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                                                                                jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 130, 182)));

                                                                                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                                                                getContentPane().setLayout(layout);
                                                                                layout.setHorizontalGroup(
                                                                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                            .addGroup(layout.createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(jTextField1))
                                                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                    .addGroup(layout.createSequentialGroup()
                                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                                            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jRadioButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                                        .addGap(15, 15, 15)
                                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                                                                                        .addGap(15, 15, 15)
                                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                            .addGroup(layout.createSequentialGroup()
                                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                    .addGroup(layout.createSequentialGroup()
                                                                                                                        .addGap(1, 1, 1)
                                                                                                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                                                                                                                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                                                .addGap(14, 14, 14)
                                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                                                                                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                                                                            .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                                                                    .addGroup(layout.createSequentialGroup()
                                                                                                        .addComponent(jRadioButton3)
                                                                                                        .addGap(2, 2, 2)
                                                                                                        .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addGap(1, 1, 1))))
                                                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                                .addGap(172, 172, 172)
                                                                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                                        .addContainerGap())
                                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                            .addContainerGap()
                                                                                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                                                                                            .addContainerGap()))
                                                                                );
                                                                                layout.setVerticalGroup(
                                                                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addGroup(layout.createSequentialGroup()
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                            .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(23, 23, 23)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                    .addComponent(jLabel3)
                                                                                                    .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                    .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(14, 14, 14))
                                                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(21, 21, 21)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                                                                                            .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jRadioButton2)))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                            .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                        .addGap(47, 47, 47)
                                                                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(55, 55, 55))
                                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                            .addGap(59, 59, 59)
                                                                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                            .addContainerGap(367, Short.MAX_VALUE)))
                                                                                );

                                                                                choice1.getAccessibleContext().setAccessibleName("");

                                                                                pack();
                                                                            }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        /*if(jTextField1.getText().isEmpty()){ jTextField1.setText("-"); return; }
        num = Double.parseDouble(jTextField1.getText());  // TODO add your handling code here:
        cal=2;
        jTextField1.setText("");
        if((double)((long)num)==num)
            jLabel1.setText((long)num+"-");
        else
            jLabel1.setText(num+"-"); 
        jLabel1.setToolTipText(jLabel1.getText());*/
        if(exception) return;
        String text=jTextField1.getText();
        if(execute&&!exception&&!text.isEmpty()){ 
            jTextField1.setText("");
            jLabel1.setText(text+"-");
            //jLabel1.setToolTipText(jLabel1.getText());
        }
        else{
            //if(execute) jTextField1.setText("");
            if(cursor_lock) jButton1.grabFocus();
            else caretpos=jTextField1.getText().length();
            int pos=caretpos;
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "-"+jTextField1.getText().substring(caretpos));
            caretpos=pos+1; jTextField1.setCaretPosition(caretpos);
        }
        execute=exception=false;
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "0"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        //if(pos==0) jTextField1.setText("1");
        //else
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "1"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "2"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
        //jTextField1.setText(jTextField1.getText() + "2"); // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "3"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "4"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "5"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "6"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "7"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "8"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(exception) return;
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "9"+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        /*num = Double.parseDouble(jTextField1.getText());  // TODO add your handling code here:
        cal=3;
        jTextField1.setText("");
        if((double)((long)num)==num)
            jLabel1.setText((long)num+"*");
        else
            jLabel1.setText(num+"*"); 
        jLabel1.setToolTipText(jLabel1.getText());*/
        if(exception) return;
        String text=jTextField1.getText();
        if(execute&&!exception&&!text.isEmpty()){ 
            jTextField1.setText("");
            jLabel1.setText(text+"*");
            //jLabel1.setToolTipText(jLabel1.getText());
        }
        else{
            if(cursor_lock) jButton1.grabFocus();
            else caretpos=jTextField1.getText().length();
            int pos=caretpos;
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "*"+jTextField1.getText().substring(caretpos));
            caretpos=pos+1; jTextField1.setCaretPosition(caretpos);
        }
        execute=exception=false;
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        /*num = Double.parseDouble(jTextField1.getText());  // TODO add your handling code here:
        cal=4;
        jTextField1.setText("");
        if((double)((long)num)==num)
            jLabel1.setText((long)num+"/");
        else
            jLabel1.setText(num+"/"); 
        jLabel1.setToolTipText(jLabel1.getText());*/
        if(exception) return;
        String text=jTextField1.getText();
        if(execute&&!exception&&!text.isEmpty()){ 
            jTextField1.setText("");
            jLabel1.setText(text+"/");
            //jLabel1.setToolTipText(jLabel1.getText());
        }
        else{
            if(cursor_lock) jButton1.grabFocus();
            else caretpos=jTextField1.getText().length();
            int pos=caretpos;
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "/"+jTextField1.getText().substring(caretpos));
            caretpos=pos+1; jTextField1.setCaretPosition(caretpos);
        }
        execute=exception=false;
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if(exception) return ; execute=true;
        //try{
            arithm_operation(); // TODO add your handling code here:
            //if(cursor_lock){ 
                caretpos=jTextField1.getText().length();
                cursor_lock=false; jTextField1.setFocusable(true); jTextField1.getCaret().setVisible(false);
            //}
        /*}    
        catch(InterruptedException e){}*/
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if(cursor_lock) jButton1.grabFocus();
        else caretpos=jTextField1.getText().length();
        if(execute){ jTextField1.setText(""); caretpos=0; }
        int pos=caretpos;
        if(pos==0) jTextField1.setText(".");
        else
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "."+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
        caretpos=pos+1; jTextField1.setCaretPosition(caretpos); execute=exception=false;
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(exception) return;
        jTextField1.setText("");// TODO add your handling code here:
        jLabel1.setText(""); execute=exception=false; caretpos=0;
        cursor_lock=false; jTextField1.setFocusable(true); jTextField1.getCaret().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        disable(); // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        enable(); // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed
    private void jButton1ActionPerformed1() {
        if(exception||cursor_lock) return;
        String str = jTextField1.getText(); // TODO add your handling code here:
        int pos_=caretpos;
        if(pos_>0){
                //jTextField1.setText(str.substring(0,str.length()-1));
                //System.out.print(caretpos+",");
                jTextField1.setText(str.substring(0,caretpos-1)+str.substring(caretpos));
                caretpos=pos_-1; //System.out.println(caretpos); 
                jTextField1.setCaretPosition(caretpos);
                /*jTextField1.setText(str.substring(0,pos-1)+str.substring(pos));
                jTextField1.setCaretPosition(pos-1);
                jTextField1.getCaret().setVisible(true);*/
        }
    }
    private void jButton1ActionPerformed2(boolean pos) {
        if(exception) return;
            if(execute){ jTextField1.setText(""); caretpos=0; }
            if(!cursor_lock) caretpos=jTextField1.getText().length();
            execute=exception=false;
            int pos_=caretpos;            
            if(pos){ 
                jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "("+jTextField1.getText().substring(caretpos)); // TODO add your handling code here:
            }
            else{ 
                jTextField1.setText(jTextField1.getText().substring(0, caretpos) + ")"+jTextField1.getText().substring(caretpos));
            }
            caretpos=pos_+1; jTextField1.setCaretPosition(caretpos);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //int pos=jTextField1.getCaretPosition();
        //System.out.println(pos);
        double pos=this.getMousePosition().getX();
        if(pos>148&&pos<184){
            jButton1ActionPerformed1();
        }
        else{
            jButton1ActionPerformed2(pos<=148);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        if(jRadioButton3.isSelected()!=true){   // TODO add your handling code here:
            jRadioButton3.setToolTipText("Lock mode");
            choice1.setEnabled(true);
        }
        else{
            /*if(choice1.getSelectedIndex()==0){
                jRadioButton3.setToolTipText("Unlock mode switching"); //switched=false;
                choice1.setEnabled(false); return;
            }*/
            //if(!switched){
                jRadioButton3.setSelected(false);
                java.awt.Component source = (java.awt.Component)evt.getSource();
            //java.awt.Point location = java.awt.MouseInfo.getPointerInfo().getLocation();
            
                jPopupMenu1.show(source, 10, 16);
                //return;
            //}
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        switch(choice1.getSelectedIndex()) {    // TODO add your handling code here:
            
            case 0 :  jRadioButton3.setSelected(true);
                      jRadioButton3.setToolTipText("Unlock mode switching");
                      choice1.setEnabled(false);
                      new Calculator().setVisible(true); break;
            
            case 1 :    
                    com.mycompany.calculator_app.scientific.Calculator1 CalSc=new
                    com.mycompany.calculator_app.scientific.Calculator1();
                    //CalSc.setLocation(getLocation());
                    /*System.out.println(this.getBounds().height+" "+this.getBounds().width
                    +" "+this.getBounds().x+" "+this.getBounds().y);*/
                    //CalSc.setBounds(getBounds());
                    CalSc.setVisible(true);
                    /*System.out.println(CalSc.getBounds().height+" "+CalSc.getBounds().width
                    +" "+CalSc.getBounds().x+" "+CalSc.getBounds().y);*/
                    //dispose();
                    break;
        }       
        //switched=true; 
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        dispose();
        switch(choice1.getSelectedIndex()) {    // TODO add your handling code here:
            
            case 0 :    Calculator CalBsc=new Calculator();
                        CalBsc.setLocation(this.getLocation());
                        CalBsc.setVisible(true); break;
            
            case 1 :    
                    com.mycompany.calculator_app.scientific.Calculator1 CalSc=new
                    com.mycompany.calculator_app.scientific.Calculator1();
                    
                    CalSc.setLocation(getLocation());
                    CalSc.requestFocusInWindow();
                    CalSc.setVisible(true);
                    break;
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        jRadioButton3.setSelected(true);  // TODO add your handling code here:
        choice1.select(0); //jRadioButton3.setToolTipText("Unlock mode switching"); 
        choice1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        if(!jButton1.isEnabled()) return;
        double x=this.getMousePosition().getX(); // TODO add your handling code here:
        if(cursor_lock){
            jTextField1.getCaret().setVisible(true);
            if(x>148&&x<184) 
                jButton1.setToolTipText("<html><p width=\"130\" style=\"border: 1px outset black;padding: 3 3 3 3;color:#4682B4;font-weight:450;font-size:8.5px\">click to free cursor"); //style=\"margin:-3 -3 -3 -3;padding: 3 3 3 3;border: 1px outset #4682B4;background:white;
            else jButton1.setToolTipText(null); return;
        }
        if(x>148&&x<184){
            jTextField1.grabFocus();
            jButton1.setToolTipText("<html><p width=\"225\" style=\"border: 1px outset black;padding: 3 3 3 3;color:#4682B4;font-weight:410;font-size:8.5px\">use arrow keys to shift cursor; right click to lock cursor</p></html>");
            jTextField1.getCaret().setVisible(true);
        } 
        else{ jButton1.setToolTipText(null); jTextField1.getCaret().setVisible(false); }
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        // TODO add your handling code here:
        if(!jButton1.isEnabled()) return;
        if(cursor_lock){ //jTextField1.getCaret().setVisible(true); 
        return; }
        //if(jButton1.isEnabled()){    
        jTextField1.setCaretPosition(jTextField1.getText().length());
        jTextField1.getCaret().setVisible(false);
        //}
        jButton1.setToolTipText(null);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        if(!jButton1.isEnabled()) return;
        double x=this.getMousePosition().getX();
        if(x>148&&x<184){
            if(evt.getButton()==java.awt.event.MouseEvent.BUTTON3){ 
                cursor_lock=true; jTextField1.getCaret().setVisible(true); jTextField1.setFocusable(false);jButton1.grabFocus(); execute=false; }
            else if(evt.getButton()==java.awt.event.MouseEvent.BUTTON1&&cursor_lock){
                cursor_lock=false; jTextField1.setFocusable(true);
                jTextField1.grabFocus();
                jTextField1.getCaret().setVisible(true);
            } 
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        // TODO add your handling code here:
        jTextField1.setCaretPosition(caretpos);
        //System.out.println(jTextField1.getCaretPosition());
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField1CaretUpdate
        //System.out.println(jTextField1.getCaretPosition()); // TODO add your handling code here:
        if(!cursor_lock)
        caretpos=jTextField1.getCaretPosition();
        //System.out.println(caretpos);
    }//GEN-LAST:event_jTextField1CaretUpdate

    private void jRadioButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseEntered
        // TODO add your handling code here:
        if(!jRadioButton3.isEnabled()) jRadioButton3.setToolTipText(null);
        else if(jRadioButton3.isSelected()) jRadioButton3.setToolTipText("Unlock mode switching");
        else jRadioButton3.setToolTipText("Lock mode");
    }//GEN-LAST:event_jRadioButton3MouseEntered

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        // TODO add your handling code here:
        if(jTextField1.isEnabled()&&!jLabel1.getText().equals(""))
            jLabel1.setToolTipText(jLabel1.getText());
        else jLabel1.setToolTipText(null);
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*num = Double.parseDouble(jTextField1.getText());  // TODO add your handling code here:
        cal=1;
        jTextField1.setText("");
        if((double)((long)num)==num)
        jLabel1.setText((long)num+"+");
        else
        jLabel1.setText(num+"+");
        jLabel1.setToolTipText(jLabel1.getText());*/
        if(exception) return;
        String text=jTextField1.getText();
        if(execute&&!exception&&!text.isEmpty()){
            jTextField1.setText("");
            jLabel1.setText(text+"+");
            //jLabel1.setToolTipText(jLabel1.getText());
        }
        else{
            if(cursor_lock) jButton1.grabFocus();
            else caretpos=jTextField1.getText().length();
            int pos=caretpos;
            jTextField1.setText(jTextField1.getText().substring(0, caretpos) + "+"+jTextField1.getText().substring(caretpos));
            caretpos=pos+1; jTextField1.setCaretPosition(caretpos);
        }
        execute=exception=false;
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private java.awt.Choice choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
