/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.calculator_app.scientific;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author susmit
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.IOException;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

class RotatingButton extends JButton {

    private static final long serialVersionUID = 1L;

    protected static final RenderingHints qualityHints;

    static {
	qualityHints = new RenderingHints(null);
	qualityHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
		RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	qualityHints.put(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
	qualityHints.put(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	qualityHints.put(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	qualityHints.put(RenderingHints.KEY_COLOR_RENDERING,
		RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	qualityHints.put(RenderingHints.KEY_DITHERING,
		RenderingHints.VALUE_DITHER_ENABLE);
    }

    private double rotation = 0;

    private BufferedImage image;

    public RotatingButton() {
	super();
	init();
    }

    public RotatingButton(Action action) {
	super(action);
	init();
    }

    public RotatingButton(Icon icon) {
	super(icon);
	init();
    }

    public RotatingButton(String text,double angle) {
	super(text);
	init(); setRotation(angle);
    }

    public RotatingButton(String text, Icon icon) {
	super(text, icon);
	init();
    }

    @Override
    public Dimension getPreferredSize() {
	Dimension size = super.getPreferredSize();
	Area area = new Area(new Rectangle(size.width, size.height));
	area.transform(AffineTransform.getRotateInstance(rotation, size
		.getWidth() / 2.0, size.getHeight() / 2.0));
	Rectangle bounds = area.getBounds();
	size.setSize(bounds.width, bounds.height);
	return size;
    }

    public double getRotation() {
	return rotation;
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g2.setRenderingHints(qualityHints);
	g2.setColor(this.getBackground());
	g2.fillRect(0, 0, getWidth(), getHeight());
	g2.rotate(rotation, getWidth() / 2.0, getHeight() / 2.0);
	g2.drawImage(image, null, (getWidth() - image.getWidth()) / 2,
		(getHeight() - image.getHeight()) / 2);
    }

    public void setRotation(double rotation) {
	this.rotation = rotation;
	this.updateImage();
    }

    private void init() {
	updateImage();
	this.addPropertyChangeListener(new PropertyChangeListener() {

	    @Override
	    public void propertyChange(PropertyChangeEvent event) {
		updateImage();
	    }
	});
	this.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent event) {
		updateImage();
	    }
	});
    }

    private void updateImage() {
	this.setSize(super.getPreferredSize());
	image = new BufferedImage(getWidth(), getHeight(),
		BufferedImage.TYPE_INT_ARGB);
	Graphics2D ig = image.createGraphics();
	super.paint(ig);
	ig.dispose();
	this.setSize(this.getPreferredSize());
	if (this.getParent() instanceof JComponent) {
	    ((JComponent) this.getParent()).revalidate();
	    ((JComponent) this.getParent()).repaint();
	}
    }

}
class RoundButton extends javax.swing.JButton {
  private Color color;
  public RoundButton(String label,Color color) {
    super(label);

    setBackground(java.awt.Color.white); 
     setFocusable(false); this.color=color;
     setForeground(color); //setBorderPainted(true);
     Font newButtonFont=new Font(getFont().getName(),Font.BOLD,getFont().getSize());
     setFont(newButtonFont);
    /*
     These statements enlarge the button so that it 
     becomes a circle rather than an oval.
    */
    java.awt.Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
  public RoundButton(javax.swing.Icon icon){
      super(icon);
      
      java.awt.Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
  protected void paintComponent(java.awt.Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(new Color(214,217,223));
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
 
    super.paintComponent(g);
  }
 
  protected void paintBorder(java.awt.Graphics g) {
    g.setColor(color);  // steel blue
    g.drawOval(0, 0, getSize().width -1, getSize().height -1);
  }
 
  // Hit detection.
  java.awt.Shape shape;
 
  public boolean contains(int x, int y) {
    // If the button has changed size,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
}

class CustomSliderUI extends javax.swing.plaf.basic.BasicSliderUI {

        private static final int TRACK_HEIGHT = 8;
        private static final int TRACK_WIDTH = 8;
        private static final int TRACK_ARC = 5;
        private static final Dimension THUMB_SIZE = new Dimension(20, 20);
        private final java.awt.geom.RoundRectangle2D.Float trackShape = 
                new java.awt.geom.RoundRectangle2D.Float();

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            java.awt.Shape clip = g2.getClip();

            //boolean horizontal = isHorizontal();
            //boolean inverted = slider.getInverted();

            // Paint shadow.
            g2.setColor(new Color(170, 170 ,170));
            g2.fill(trackShape);

            // Paint track background.
            //g2.setColor(new Color(200, 200 ,200));
            g2.setClip(trackShape);
            trackShape.y += 1;
            g2.fill(trackShape);
            trackShape.y = trackRect.y;

            g2.setClip(clip);

            // Paint selected track.
            /*if (horizontal) {
                boolean ltr = slider.getComponentOrientation().isLeftToRight();
                if (ltr) inverted = !inverted;
                int thumbPos = thumbRect.x + thumbRect.width / 2;
                if (inverted) {
                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
                } else {
                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                }

            } else {
                int thumbPos = thumbRect.y + thumbRect.height / 2;
                if (inverted) {
                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                } else {
                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                }
            }*/
            g2.setColor(new Color(255,192,0));
            g2.fill(trackShape);
            g2.setClip(clip);
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(new Color(96,130,182)); //new Color(246, 146, 36));
            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {}
}

class Pair {
    public String text;
    public Font font1,font2;
}

class MyHtml2Text extends HTMLEditorKit.ParserCallback {
    StringBuffer s;
    public MyHtml2Text() {}
    public void parse(java.io.Reader in) throws IOException {
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        delegator.parse(in, this, Boolean.TRUE);
    }
    public void handleText(char[] text, int pos) {
        s.append(text);
        s.append("\n");
    }
    public String getText() {
        return s.toString();
    }
}
  
public class Calculator1 extends javax.swing.JFrame {

    /**
     * Creates new form Calculator1
     */
    //private javax.swing.JButton rButton1;
    private final java.util.ArrayList<javax.swing.JButton> buttons;
    private final java.util.ArrayList<javax.swing.JLabel> shifts;
    private java.util.ArrayList<String> angle;
    private final Pair[] p;
    private javax.swing.ImageIcon background,help;
    private boolean real=true,forward=true;
    private String exp="",temp="";
    //private JTextWrapPane jt;
    private int X,Y;
    
    public Calculator1() {
        /*jt=new JTextWrapPane();
        jt.setEditable(false);
        jt.setVisible(true);
        jt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 130, 182)));
        jt.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        jt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jt.setBounds(15, 60, 415, 52);
        this.add(jt); //jt.setLineWrap(false);*/
        
        buttons=new java.util.ArrayList<>();
        shifts=new java.util.ArrayList<>();
        angle=new java.util.ArrayList<>();
        angle.add("deg"); angle.add("rad"); angle.add("grad");
        p=new Pair[3]; for(int i=0;i<3;i++) p[i]=new Pair();
        
        p[0].text="\u03c0";
        p[0].font1=new Font("Serif",1,20);
        p[0].font2=new Font("Serif",0,12);
        
        p[1].text="e";
        p[1].font1=new Font("Times New Roman",3,20);
        p[1].font2=new Font("Times New Roman",2,14);
        
        p[2].text="\u03c9";
        p[2].font1=new Font("DejaVu Serif",1,18);
        p[2].font2=new Font("DejaVu Serif",0,13);
        
        background=new javax.swing.ImageIcon("/home/susmit/Desktop/button/world.png");
        java.awt.Image img=background.getImage();
        java.awt.Image temp=img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
        background=new javax.swing.ImageIcon(temp);
        help=new javax.swing.ImageIcon("/home/susmit/Desktop/button/help4.png");
        img=help.getImage();
        temp=img.getScaledInstance(18,18,java.awt.Image.SCALE_SMOOTH);
        help=new javax.swing.ImageIcon(temp);
        /*JLabel back=new JLabel(background);
        back.setLayout(null);
        back.setBounds(0,0,500,600);*/
        initComponents();
        /*System.out.println(jTextPane1.getX()+" "+jTextPane1.getY()+" "+jTextPane1.getWidth()+" "+jTextPane1.getHeight());
        System.out.println(jTextPane3.getX()+" "+jTextPane3.getY()+" "+jTextPane3.getWidth()+" "+jTextPane3.getHeight());
        jTextPane3.setBounds(jTextPane1.getX(), jTextPane1.getY(), 410, 42);*/
        //jTextPane2.setBounds(jTextPane1.getBounds());
        //jTextPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96,130,182),1,false));
        //jTextPane2.setVisible(false);
        //jScrollPane1.setViewportView(jt);
        this.setBounds(490, 100, 446, 607);
        X=490; Y=100;
        jTextPane1.setContentType("text/html");
        jTextPane1.putClientProperty(javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        //jTextPane1.setText("<html><sup>&#160;</sup>12345+22779%25+8*&#8730;3+9-14*<sup> &nbsp</sup>1</html>");
        jTextPane1.setText("<html><sup>&nbsp</sup></html>");
        jTextPane1.getActionMap().get("caret-up").setEnabled(false);
        jTextPane1.getActionMap().get("caret-backward").setEnabled(false);
        jTextPane1.getActionMap().get("caret-forward").setEnabled(false);
        //jScrollPane1.getActionMap().get("unitScrollUp").setEnabled(false);
        jScrollPane1.getActionMap().put("unitScrollUp", new javax.swing.AbstractAction(){
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
        }});
        //jScrollPane1.getActionMap().get("negativeUnitIncrement").setEnabled(false);
        
        //jTextPane1.setText("<html><head><style>p span{background-color: rgb(96,130,182);color: white;}</style></head><body><p><span><sup>&nbsp</sup></span></p></body></html>");
        //jTextPane1.setText("<html><head><style>sup { font-size: 70%;vertical-align: text-middle;line-height: 0; }</style></head></html>");
        //jTextPane1.setText("<html><head><style>sup{line-height: 0;font-size: 0.96em;vertical-align: super;}</style></head></html>");
        /*javax.swing.text.SimpleAttributeSet attribs = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setAlignment(attribs, javax.swing.text.StyleConstants.ALIGN_RIGHT);
        jTextPane1.setParagraphAttributes(attribs, true);
        jTextPane1.setAutoscrolls(false);*/
   
        //jTextPane1.setBounds(15, 60, 415, 52);
        choice1.add("Basic Mode");
        choice1.add("Scientific Mode");
        choice1.add("Conversion Mode");
        choice1.add("Logical Mode");
        choice1.add("Big Integer op.");
        String text ="<html>x<sup>2</sup><br>";
        jButton4.setText(text);
        text="<html>x<sup>3</sup><br>";
        jButton8.setText(text);
        text="<html><sup>n</sup>Cr<br>";
        jButton11.setText(text);
        text="<html>x !<sup> </sup><br>";
        jButton15.setText(text);
        //jButton9.setBackground(new Color(255,192,0));
        //jButton16.setForeground(Color.red);  // golden yellow
        //jButton16.setBackground(getBackground());
        jLabel2.setForeground(new Color(255,192,0));
        //jLabel2.setBackground(new Color(254,254,254));
        jLabel4.setForeground(new Color(255,192,0));
        jLabel5.setForeground(new Color(255,192,0));
        for(int i=0;i<shifts.size();i++) shifts.get(i).setBackground(getBackground());
        jToggleButton1.setBackground(Color.white);
        jToggleButton1.setForeground(new Color(255,192,0));
        //jRadioButton3.setIcon(help);
        /*System.out.println(jButton8.getHeight());
        System.out.println(jButton15.getHeight());*/
        /*System.out.println(this.getBackground().getRed()+" "+getBackground().getGreen()+
                " "+getBackground().getBlue());*/
        /*System.out.println(this.getBounds().height+" "+this.getBounds().width
                    +" "+this.getBounds().x+" "+this.getBounds().y);*/
        //rButton1=new javax.swing.JButton("RButton1");
        //this.add(rButton1); rButton1.setVisible(true);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formcomponentMoved(evt);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog(this,true);
        jLabel13 = new javax.swing.JLabel(background);
        jButton16 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton9 = new RoundButton("C",Color.RED);
        jButton5 = new javax.swing.JButton();
        jButton6 = new RotatingButton("    )    ",-0.78);
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new RotatingButton("    (    ",0.78);
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton1 = new RoundButton("RButton1",new Color(96,130,182));
        jButton2 = new javax.swing.JButton();
        choice1 = new java.awt.Choice();
        jLabel1 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton34 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jButton35 = new javax.swing.JButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton36 = new RoundButton("\u03c0",new Color(255,192,0));
        jButton3 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jButton38 = new RoundButton(help);
        jSlider1 = new javax.swing.JSlider();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();/*{

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                < getParent().getSize().width;
            }

        }*///;
        jToggleButton3 = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();/*{

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                < getParent().getSize().width;
            }

        }*///;

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setResizable(false);
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialog1WindowClosing(evt);
            }
        });

        jLabel13.setText("Back to real world !");
        jLabel13.setLayout(null);
        jLabel13.setBounds(0,0,200,200);
        jLabel13.setForeground(Color.black);

        jButton16.setText("Okay!");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton37.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton37.setText("No way, I'm lovin' it here");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel13)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(27, 27, 27)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jLabel15.setText("    x =");

        jScrollPane2.setViewportView(jTextPane2);

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator-Scientific");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 130, 182), 2));

        jButton4.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton4.setText("3");
        buttons.add(jButton4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jButton9.setText("C");
        buttons.add(jButton9);
        //jButton9.setBackground(new Color(237,234,222));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Ubuntu Condensed", 0, 9)); // NOI18N
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton5.setIconTextGap(3);
        jButton5.setLabel("log");
        buttons.add(jButton5);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        jButton6.setText("    )    ");
        buttons.add(jButton6);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton7.setText("ln");
        buttons.add(jButton7);
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton8.setText("3");
        buttons.add(jButton8);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });

        jButton10.setText("    (    ");
        buttons.add(jButton10);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton12.setText("cos");
        buttons.add(jButton12);
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton13.setText("tan");
        buttons.add(jButton13);
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton14.setText("sin");
        buttons.add(jButton14);
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton11.setText("3");
        buttons.add(jButton11);
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton15.setText("x !");
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                /*if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {

                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-27)+"</sup>"+"!<sup> </sup>");
                    //jButton2.setBackground(Color.yellow);//JOptionPane.showMessageDialog(null, "Double clicked!");
                }
                else{
                    if(!jToggleButton1.isSelected())
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"!<sup> </sup>");
                    else
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<sup>-1</sup>");
                }*/
                /*else{
                    if(!jToggleButton1.isSelected())
                    jButton2.setBackground(Color.yellow);
                }*/
            }
        });
        buttons.add(jButton15);
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jButton17.setText("deg");
        buttons.add(jButton17);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Ubuntu Condensed", 1, 11)); // NOI18N
        jToggleButton1.setText("SHIFT");
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.setMaximumSize(new java.awt.Dimension(43, 27));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(43, 27));
        jToggleButton1.setPreferredSize(new java.awt.Dimension(43, 27));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setFont(new java.awt.Font("Ubuntu Condensed", 0, 12)); // NOI18N
        jToggleButton2.setText("hyp");
        jToggleButton2.setMaximumSize(new java.awt.Dimension(39, 30));
        jToggleButton2.setMinimumSize(new java.awt.Dimension(39, 30));
        jToggleButton2.setPreferredSize(new java.awt.Dimension(39, 30));

        jLabel2.setFont(new java.awt.Font("Ubuntu Condensed", 1, 12)); // NOI18N
        jLabel2.setText("<html>&emsp;&nbsp;x<sup>-1</sup><br></html>");
        jLabel2.setMaximumSize(new java.awt.Dimension(61, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(61, 14));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(51, 14));
        shifts.add(jLabel2);

        jLabel4.setBackground(new java.awt.Color(254, 254, 254));
        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setText("<html>&ensp;&ensp;\u221B<br></html>");
        jLabel4.setMaximumSize(new java.awt.Dimension(61, 24));
        jLabel4.setMinimumSize(new java.awt.Dimension(61, 24));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(21, 14));
        shifts.add(jLabel4);

        jLabel5.setBackground(new java.awt.Color(254, 254, 254));
        jLabel5.setFont(new java.awt.Font("Ubuntu Condensed", 1, 12)); // NOI18N
        jLabel5.setText("<html>&emsp;&nbsp;\u221A<br></html>");
        jLabel5.setMaximumSize(new java.awt.Dimension(61, 24));
        jLabel5.setMinimumSize(new java.awt.Dimension(61, 24));
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(51, 14));
        shifts.add(jLabel5);

        jLabel6.setBackground(new java.awt.Color(254, 254, 254));
        jLabel6.setFont(new java.awt.Font("Ubuntu Condensed", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 192, 0));
        jLabel6.setText("<html>&ensp;&nbsp;&nbsp;<sup>n</sup>Pr<br></html>");
        jLabel6.setMaximumSize(new java.awt.Dimension(61, 24));
        jLabel6.setMinimumSize(new java.awt.Dimension(61, 24));
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(51, 14));
        shifts.add(jLabel6);

        jLabel7.setBackground(new java.awt.Color(254, 254, 254));
        jLabel7.setFont(new java.awt.Font("Ubuntu Condensed", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 192, 0));
        jLabel7.setText("<html>&emsp;&nbsp;<sup>x</sup>\u221a<br></html>");
        jLabel7.setOpaque(true);
        shifts.add(jLabel7);

        jLabel8.setBackground(new java.awt.Color(254, 254, 254));
        jLabel8.setFont(new java.awt.Font("Ubuntu Condensed", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 192, 0));
        jLabel8.setText("<html>&emsp;&nbsp;10<sup>x</sup><br></html>");
        jLabel8.setOpaque(true);
        shifts.add(jLabel8);

        jLabel9.setBackground(new java.awt.Color(254, 254, 254));
        jLabel9.setFont(new java.awt.Font("Ubuntu Condensed", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 192, 0));
        jLabel9.setText("<html>&emsp;cos<sup>-1</sup><br></html>");
        jLabel9.setOpaque(true);
        shifts.add(jLabel9);

        jLabel10.setBackground(new java.awt.Color(254, 254, 254));
        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 192, 0));
        jLabel10.setText("<html>&emsp;&ensp;e<sup>x</sup><br></html>");
        jLabel10.setOpaque(true);
        shifts.add(jLabel10);

        jLabel11.setBackground(new java.awt.Color(254, 254, 254));
        jLabel11.setFont(new java.awt.Font("Ubuntu Condensed", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 192, 0));
        jLabel11.setText("<html>&emsp;&nbsp;tan<sup>-1</sup><br></html>");
        jLabel11.setMaximumSize(new java.awt.Dimension(44, 14));
        jLabel11.setOpaque(true);
        jLabel11.setPreferredSize(new java.awt.Dimension(48, 15));
        shifts.add(jLabel11);

        jLabel12.setBackground(new java.awt.Color(254, 254, 254));
        jLabel12.setFont(new java.awt.Font("Ubuntu Condensed", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 192, 0));
        jLabel12.setText("<html>&emsp;sin<sup>-1</sup><br></html>");
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(44, 15));
        shifts.add(jLabel12);

        jButton18.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton18.setText("7");
        buttons.add(jButton18);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton19.setText("8");
        buttons.add(jButton19);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton20.setText("9");
        buttons.add(jButton20);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Ubuntu Condensed", 1, 14)); // NOI18N
        jButton21.setText("%");
        buttons.add(jButton21);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setFont(new java.awt.Font("Ubuntu Condensed", 1, 14)); // NOI18N
        jButton22.setText("Ans");
        buttons.add(jButton22);

        jButton23.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton23.setText("4");
        buttons.add(jButton23);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton24.setText("5");
        buttons.add(jButton24);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton25.setText("6");
        buttons.add(jButton25);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jButton26.setText("-");
        buttons.add(jButton26);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton27.setText("+");
        buttons.add(jButton27);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(249, 84, 46));
        jLabel3.setText("E X  P R E S S");

        jButton28.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton28.setText("1");
        buttons.add(jButton28);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton29.setText("2");
        buttons.add(jButton29);
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton1.setText("\u25c0 Erase \u25b6");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
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
        buttons.add(jButton1);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel1.setForeground(java.awt.Color.red);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jButton30.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton30.setText("3");
        buttons.add(jButton30);
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jButton31.setText("/");
        buttons.add(jButton31);
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton32.setText("*");
        buttons.add(jButton32);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setFont(new java.awt.Font("Ubuntu Condensed", 1, 15)); // NOI18N
        jButton33.setText("0");
        buttons.add(jButton33);
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jRadioButton3.setSelected(true);
        jRadioButton3.setToolTipText("Unlock mode switching");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jButton34.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton34.setText(".");
        buttons.add(jButton34);
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jRadioButton1.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("ON");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jButton35.setFont(new java.awt.Font("Ubuntu Condensed", 1, 19)); // NOI18N
        jButton35.setForeground(new java.awt.Color(96, 130, 182));
        jButton35.setText("=");
        jButton35.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 130, 182), 1, true));
        jButton35.setBackground(Color.white);
        buttons.add(jButton35);
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jRadioButton2.setText("OFF");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jButton36.setFont(new java.awt.Font("Serif", 1, 20)); // NOI18N
        jButton36.setText("\u03c0");
        buttons.add(jButton36);

        jButton3.setFont(new java.awt.Font("Ubuntu Condensed", 0, 15)); // NOI18N
        jButton3.setText("^");
        buttons.add(jButton3);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel14.setText("   e    ");
        jLabel14.setOpaque(true);
        jLabel14.setForeground(Color.red);

        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        jButton38.setToolTipText("Explore");

        jSlider1.setMaximum(1);
        jSlider1.setToolTipText("slide to change const.");
        jSlider1.setValue(0);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jSlider1.setUI(new CustomSliderUI(jSlider1));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 130, 182), 0));
        jTextPane1.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTextPane1);

        jToggleButton3.setBackground(new java.awt.Color(204, 204, 255));
        jToggleButton3.setFont(new java.awt.Font("Ubuntu Condensed", 1, 10)); // NOI18N
        jToggleButton3.setForeground(new java.awt.Color(102, 102, 255));
        jToggleButton3.setText("mod");
        jToggleButton3.setMaximumSize(new java.awt.Dimension(39, 30));
        jToggleButton3.setMinimumSize(new java.awt.Dimension(39, 30));
        jToggleButton3.setPreferredSize(new java.awt.Dimension(39, 30));
        //jToggleButton3.setBackground(Color.white);
        jToggleButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2&&jToggleButton3.isSelected()) {
                    jButton2.setBackground(Color.yellow);//JOptionPane.showMessageDialog(null, "Double clicked!");
                }
            }
        });
        jToggleButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton3MouseClicked(evt);
            }
        });
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setOpaque(false);

        jTextPane3.setEditable(false);
        jTextPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 130, 182)));
        jTextPane3.setFont(new java.awt.Font("Ubuntu", 1, 23)); // NOI18N
        jTextPane3.setForeground(new java.awt.Color(255, 255, 255));
        jTextPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(jTextPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jRadioButton2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))))
                        .addGap(6, 6, 6)
                        .addComponent(jButton1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(7, 7, 7))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(2, 2, 2)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8)
                                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addGap(4, 4, 4)))))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addComponent(jButton17))
                        .addGap(66, 66, 66))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addGap(1, 1, 1)
                        .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(67, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel9))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel10)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(453, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void formcomponentMoved(java.awt.event.ComponentEvent e){
        //System.out.println(evt.getComponent().getX());
        int x=(int)this.getX(),y=(int)this.getY();
        //jDialog1.setBounds(this.getX()+124, this.getY()+(int)getMousePosition().getY(), 300, 210);
        jDialog1.setLocation(jDialog1.getX()+(x-X), jDialog1.getY()+(y-Y));
        X=x; Y=y;
    }
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if(jToggleButton1.isSelected()){    // TODO add your handling code here:
            for(int i=0;i<shifts.size();i++)
            shifts.get(i).setBackground(new Color(254,254,254));
        }
        else{
            for(int i=0;i<shifts.size();i++)
                shifts.get(i).setBackground(getBackground());
        }
        /*if(jButton36.getText().equals("\u03c0")){
            jButton36.setText("e");
            jButton36.setFont(new java.awt.Font("Times New Roman",3,20));
            jLabel14.setText("   \u03c0    ");
            jLabel14.setFont(new java.awt.Font("Serif",0,12));
        }
        else{
            jButton36.setText("\u03c0");
            jButton36.setFont(new java.awt.Font("Serif",1,20));
            jLabel14.setText("   e    ");
            jLabel14.setFont(new java.awt.Font("Times New Roman",2,14));
        }*/
        if(!real) jLabel6.setBackground(getBackground());
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /*if(jToggleButton1.isSelected()) System.out.println("yes");
        else System.out.println("no");*/
        double x=getMousePosition().getX(); //y=getMousePosition().getY();
        /*System.out.println(getMousePosition().getX() + " " + // TODO add your handling code here:
            getMousePosition().getY());*/
        if(x<=207){ jTextPane1.setCaretPosition(jTextPane1.getCaretPosition()-4);
        jButton2.setBackground(Color.red);
        }
        if(x>=255){ 
            if(jTextPane1.getCaretPosition()<jTextPane1.getDocument().getLength())
            jTextPane1.setCaretPosition(jTextPane1.getCaretPosition()+1);
            jButton2.setBackground(Color.blue);
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

                //jPopupMenu1.show(source, 10, 16);
                //return;
                //}
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        enable(); // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        disable(); // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jDialog1WindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowClosing
        this.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_jDialog1WindowClosing

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        //jDialog1=new javax.swing.JDialog(this, true);
        jDialog1.setBounds(this.getX()+124, this.getY()+(int)getMousePosition().getY(), 300, 210);
        if(real){
            background=new javax.swing.ImageIcon("/home/susmit/Desktop/button/adventurer1.jpg");
            java.awt.Image img=background.getImage();
            java.awt.Image temp=img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
            background=new javax.swing.ImageIcon(temp);
            jLabel13.setIcon(background);
            jLabel13.setText("Dive into imaginary world !");
            jLabel13.setForeground(Color.red);
            jLabel13.setFont(new Font("Ubuntu",0,12));
            jButton16.setText("Yeah!");
            jButton16.setBackground(Color.white);
            jButton16.setForeground(Color.red);
            jButton37.setText("Nah,I'm good here");
            forward=true;
        }
        else {
            background=new javax.swing.ImageIcon("/home/susmit/Desktop/button/world.png");
            java.awt.Image img=background.getImage();
            java.awt.Image temp=img.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
            background=new javax.swing.ImageIcon(temp);
            jLabel13.setIcon(background);
            jLabel13.setText("Back to real world !");
            jLabel13.setBounds(0,0,200,200);
            jLabel13.setForeground(Color.black);
            jLabel13.setFont(new Font("DejaVu Sans",0,12));
            jButton16.setText("Okay!");
            jButton16.setForeground(Color.black);
            jButton16.setBackground(jButton37.getBackground());
            jButton37.setText("No way, I'm lovin' it here");
        }
        jDialog1.setVisible(true);
        /*this.setEnabled(false);*/
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        //System.out.println(jSlider1.getValue());// TODO add your handling code here:
        /*switch(jSlider1.getValue()) {
            case 0 :
                jButton36.setText("\u03c0");
                jButton36.setFont(new java.awt.Font("Serif",1,20));
                jLabel14.setText("   e    ");
                jLabel14.setFont(new java.awt.Font("Times New Roman",2,14));
                break;
            
            case 1 :
                jButton36.setText("e");
                jButton36.setFont(new java.awt.Font("Times New Roman",3,20));
                jLabel14.setText("   \u03c0    ");
                jLabel14.setFont(new java.awt.Font("Serif",0,12));
                break;
        }*/
        int select=jSlider1.getValue(),next;
        if(select==jSlider1.getMaximum()) forward=false;
        if(select==0) forward=true;
        next=(forward ? select+1 : select-1);
        jButton36.setText(p[select].text);
        jButton36.setFont(p[select].font1);
        jLabel14.setText("   "+p[next].text+"    ");
        jLabel14.setFont(p[next].font2);
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        jDialog1.dispose(); // TODO add your handling code here:
        this.setEnabled(true); 
        if(real){
            jButton21.setText("z");
            jButton21.setFont(new Font(jButton21.getFont().getName(),1,16));
            jButton15.setText("<html>|z|<sup> </sup></html>");
            jButton5.setFont(new Font(jButton5.getFont().getName(),0,10));
            jButton5.setText("ln");
            jButton7.setText("<html>log<sub>z</sub></html>");
            jLabel8.setFont(new Font("Ubuntu",1,10));
            jLabel8.setText("<html>&emsp;&nbsp;e<sup>z</sup></html>");
            jLabel10.setText("<html>&emsp;&ensp;z<sup>x</sup></html>");
            jButton11.setEnabled(false);
            jLabel6.setEnabled(false);
            if(jToggleButton1.isSelected()) jLabel6.setBackground(getBackground());
            jButton36.setFont(new Font("DejaVu Serif",1,18));
            jButton36.setText("\u03c9");
            jSlider1.setMaximum(2);
        }
        else {
            jButton21.setText("%");
            jButton21.setFont(new Font(jButton21.getFont().getName(),1,12));
            jButton15.setText("<html>x!<sup> </sup></html>");
            jButton5.setFont(new Font(jButton5.getFont().getName(),0,9));
            jButton5.setText("log");
            jButton7.setText("ln");
            jLabel8.setFont(new Font("Ubuntu Condensed",1,10));
            jLabel8.setText("<html>&emsp;&nbsp;10<sup>x</sup></html>");
            jLabel10.setText("<html>&emsp;&ensp;e<sup>x</sup></html>");
            jButton11.setEnabled(true);
            jLabel6.setEnabled(true);
            if(jToggleButton1.isSelected()) jLabel6.setBackground(new Color(254,254,254));
            jSlider1.setMaximum(1);
        }
        real=!real;
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        jDialog1.dispose();    // TODO add your handling code here:
        this.setEnabled(true);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        String text="."; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+7>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='.'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='.'+" ";
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        String text="0"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        //System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='0'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='0'+" ";
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        String ang=angle.get(0);
        angle.remove(0); // TODO add your handling code here:
        angle.add(ang);
        jButton17.setText(angle.get(0));
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        /*if(exp.isEmpty()){ 
            jTextPane1.setText("<html>&nbsp;1111111111111111111111111111<sup> &nbsp</sup>log</html>");
            exp+='1';
        }
        else
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"1111111111111111111111111111");
        System.out.println(jTextPane1.getCaretPosition());
        return;*/
     
        String text="1"; double pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){//397){//text.length()>(26-temp.length())){ 
            //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<p><span><sup>&nbsp</sup>"+text);
            System.out.println(jTextPane1.getText());
            //if(pos>389) 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp;</sup>"+text);
            //else jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<sup> &nbsp;</sup>"+text);
            System.out.println(jTextPane1.getText());
            temp=text; exp+='$';
        }
        else{
            temp+='1'; //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-35)+text);
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='1'+" ";
       // jTextPane1.setText("<html>"+exp+"<sup> </sup></html>");
       // System.out.println(exp.length());
        /*jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "1<sup> </sup>");*/
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        String text="2"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='2'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='2'+" ";
        /*jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "2<sup> </sup>");*/
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        String text="3"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='3'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='3'+" ";
        /* jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "3<sup> </sup>");*/
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        String text="4"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='4'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='4'+" "; 
        /*jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "4<sup> </sup>");*/
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        String text="5"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='5'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='5'+" ";
        /* jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "5<sup> </sup>");*/
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        String text="6"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='6'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='6'+" ";
        /* jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "6<sup> </sup>");*/
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        String text="7"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='7'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='7'+" ";
        /* jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "7<sup> </sup>");*/
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        String text="8"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='8'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='8'+" ";
        /*jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "8<sup> </sup>");*/
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        String text="9"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+15>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='9'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='9'+" ";
        /*jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "9<sup> </sup>");*/
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        /*if(jTextField1.getText().isEmpty()) return; // TODO add your handling code here:
        jTextField1.setText(jTextField1.getText()+'+');*/
        /*MyHtml2Text parser = new MyHtml2Text();
        try{
         String text=jTextPane1.getText();
         parser.parse(new java.io.StringReader(text));
         text=parser.getText();
         if(parser.getText().equals("")) return;
        }
        catch(Exception e) {}
        jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
                "+<sup> </sup>");*/
        String text="+"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+16>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='+'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='+'+" ";
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        //jTextField1.setText(jTextField1.getText()+'-'); // TODO add your handling code here:
        String text="-"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+10>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='-'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='-'+" ";
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        //jTextField1.setText(jTextField1.getText()+'*'); // TODO add your handling code here:
       String text="*"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+14>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='*'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='*'+" ";
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        //jTextField1.setText(jTextField1.getText()+'/'); // TODO add your handling code here:
        String text="/"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+12>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='/'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='/'+" ";
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        //jTextField1.setText(jTextField1.getText()+'%'); // TODO add your handling code here:
        String text="%"; int pos=jTextPane1.getCaret().getMagicCaretPosition().x;
        System.out.println(pos);
        if(pos+25>402){ 
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+='%'; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+='%'+" ";
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTextPane1.setText("<html><sup>&nbsp</sup></html>");// TODO add your handling code here:
        //jTextPane1.setText("<html><head><style>p span{background-color: rgb(96,130,182);color: white;}</style></head><body><p><span><sup>&nbsp</sup></span></p></body></html>");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //jTextPane1.setBounds(jTextPane1.getX(), jTextPane1.getY(), 415, 52);
        /*System.out.println(jTextField1.getBounds().getHeight()+" "+jTextField1.getBounds().getWidth()
                            +" "+jTextField1.getBounds().getX()+" "+jTextField1.getBounds().getY());
        */
        //return;
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     //MyHtml2Text parser = new MyHtml2Text(); 
        /*try{
         String text=jTextPane1.getText();
         parser.parse(new java.io.StringReader(text));
         text=parser.getText();
        //System.out.println(parser.getText());
        //for(int i=0;i<text.length();i++) System.out.print((int)text.charAt(i)+" ");
        //if(parser.getText().equals("")) System.out.print("yes");
        }
        catch(Exception e) {}*/
     //String text="<html>x<sup>2</sup></html>"; //"<html>C</html>";
        
        //System.out.println(jTextPane1.getCaretPosition());
        //System.out.println(jTextPane1.getText());
        //if(jTextPane1.getText().equals("\n")) System.out.println("y");
     //System.out.println(jTextPane1.getText().length()); // 58 at null (58-20)
        //if(!parser.getText().equals(""))
     /*text=jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+
            (jToggleButton1.isSelected() ? "\u221a<sup> </sup>" : "<sup>2</sup>"); //"C</body></html>";   
      */   
        //text+="2x</sup>";
        //System.out.println(text);
     //jTextPane1.setText(text);
        //System.out.println(jTextPane1.getText());
        //}
        //else jTextPane1.setText("<html>x<sup>2</sup></html>");
        //}
        //catch(Exception e){}
        //jTextPane1.setText(jTextPane1.getText()+text);
        /*try{
         text=jTextPane1.getText();
         parser.parse(new java.io.StringReader(text));
         /*text.replaceAll("\\<.*?>", ""); 
         text.replaceAll("\\&.*?\\;", "");*/

        //text=jTextPane1.getDocument().getText(0,jTextPane1.getDocument().getLength());
        /*text=parser.getText();
        System.out.println(parser.getText());
        //for(int i=0;i<text.length();i++) System.out.print((int)text.charAt(i)+" ");
        if(text.equals("\u221a\n 2\n")) System.out.print("yes");
        }
        catch(Exception e) {;}*/
        //jTextPane1.setContentType("text/plain");
     //return;
        
        /*jTextField1.setText(jTextField1.getText()+
                (jToggleButton1.isSelected() ? "\u221a" : "<html><sup>2</sup></html>")); */// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        /*if(jToggleButton3.isSelected()){ 
            jToggleButton3.setBackground(this.getBackground()); jToggleButton3.setForeground(Color.black);
        }// TODO add your handling code here:
        else{ jToggleButton3.setBackground(new Color(204,204,255)); jToggleButton3.setForeground(new Color(102,102,255));
            //jToggleButton3.setBackground(Color.white); jToggleButton3.setForeground(new Color(255,192,0)); 
        }*/
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton3MouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==java.awt.event.MouseEvent.BUTTON1){ 
            jToggleButton3.setSelected(true);
            jToggleButton3.setBackground(this.getBackground()); jToggleButton3.setForeground(Color.black);
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"|");
        }
        if(evt.getButton()==java.awt.event.MouseEvent.BUTTON3){
            jToggleButton3.setSelected(false);
            jToggleButton3.setBackground(new Color(204,204,255)); jToggleButton3.setForeground(new Color(102,102,255));
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"|");
        }
    }//GEN-LAST:event_jToggleButton3MouseClicked

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        double x=this.getMousePosition().getX(); // TODO add your handling code here:
        if(x>=258||x<=209) jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        else{
                  java.awt.Cursor cur=java.awt.Toolkit
                  .getDefaultToolkit()
                  .createCustomCursor(
                         new javax.swing.ImageIcon("/home/susmit/NetBeansProjects/Calculator_app/src/main/java/com/mycompany/calculator_app/scientific/hand1.png").getImage()
                          .getScaledInstance(16,16,java.awt.Image.SCALE_SMOOTH),
                         new java.awt.Point(8,8),
                         "My cursor"
                  );
                  jButton1.setCursor(cur);
            //jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        //if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
        if(evt.getButton()== java.awt.event.MouseEvent.BUTTON3&&jToggleButton1.isSelected()){
            /*int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+"<sup>3</sup>");*/
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<sup>3</sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="<sup>3</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                System.out.println(caretpos);
                if(caretpos+14>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="ln "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="ln"+" ";
            }
            else{
                String text="<font face=\"Callibri\" size=\"4\">&#8731;</font>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                System.out.println(caretpos);
                if(caretpos+18>403){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="\u221B "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                    System.out.println(jTextPane1.getText());
                }
                exp+="\u221B"+" ";
            }
        }
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+"<sup>2</sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="<sup>2</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                System.out.println(caretpos);
                if(caretpos+14>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="ln "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="ln"+" ";
            }
            else{
                String text="\u221A"; //"sqrt"; //; // 1+2-3*sqrt3+11111
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                System.out.println(caretpos);
                if(caretpos+16>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="\u221a "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="\u221a"+" ";
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+"C<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="C";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+18>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="C "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="C"+" ";
            }
            else{
                String text="P";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+17>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="P "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="P"+" ";
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+"log<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="log";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                //if(text.length()>29-temp.length()){
                if(caretpos+40>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup>&nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="log"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="log"+" ";
                //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"log<sup> </sup>");
            }
            else{
                String text="10<sup>^</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                //if(text.length()>29-temp.length()){
                if(caretpos+43>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup>&nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="10^"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="10^"+" ";
                //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"10<sup>^</sup>");
            }
        }
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+"ln<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="ln";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                //if(text.length()>29-temp.length()){
                if(caretpos+24>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="ln "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="ln"+" ";
                //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"ln<sup> </sup>");
            }
            else{
                String text="e<sup>^</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+29>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="e^"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="e^"+" ";
            }
        }
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked
        String txt=(jToggleButton2.isSelected() ? "sinh" : "sin");
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+txt+"<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                //String text="ln";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                if((txt.equals("sin")&&caretpos+37>402)||(txt.equals("sinh")&&caretpos+53>402)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+txt);
                    temp=txt; exp+='$';
                }
                else{
                    temp+=txt+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+txt);
                }
                exp+=txt+" ";
            }
            else{
                String text=txt+"<sup>-1</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                if((txt.equals("sin")&&caretpos+60>402)||(txt.equals("sinh")&&caretpos+76>402)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=txt; exp+='$';
                }
                else{
                    temp+=text+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+=text+" ";
            }
        }
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        String txt=(jToggleButton2.isSelected() ? "cosh" : "cos");
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+txt+"<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if((txt.equals("cos")&&caretpos+43>402)||(txt.equals("cosh")&&caretpos+59>402)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+txt);
                    temp=txt; exp+='$';
                }
                else{
                    temp+=txt+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+txt);
                }
                exp+=txt+" ";
            }
            else{
                String text=txt+"<sup>-1</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if((txt.equals("cos")&&caretpos+66>403)||(txt.equals("cosh")&&caretpos+82>403)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+=text+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+=text+" ";
            }
        }
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        String txt=(jToggleButton2.isSelected() ? "tanh" : "tan");
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {
            int i; String text=jTextPane1.getText();
            for(i=text.length()-31;!(text.charAt(i-1)=='>'||text.charAt(i-1)==' ');i--); 
            jTextPane1.setText(jTextPane1.getText().substring(0, i)+txt+"<sup> </sup>");
        }
        else{
            if(!jToggleButton1.isSelected()){
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if((txt.equals("tan")&&caretpos+43>402)||(txt.equals("tanh")&&caretpos+59>402)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+txt);
                    temp=txt; exp+='$';
                }
                else{
                    temp+=txt+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+txt);
                }
                exp+=txt+" ";
            }
            else{
                String text=txt+"<sup>-1</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if((txt.equals("tan")&&caretpos+66>403)||(txt.equals("tanh")&&caretpos+82>403)){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+=text+" "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+=text+" ";
            }
        }
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
        jTextPane1.getCaret().setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        jTextPane1.getCaret().setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //this.setEnabled(false); // TODO add your handling code here:
        //for(int i=0;i<buttons.size();i++)
        //buttons.get(i).setEnabled(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {    // TODO add your handling code here:
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="!";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+8>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="! "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="!"+" ";
                //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"ln<sup> </sup>");
            }
            else{
                String text="<sup> -1</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+29>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="e^"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="e^"+" ";
            }
        }
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (evt.getClickCount() == 2&&jToggleButton1.isSelected()) {    // TODO add your handling code here:
        }
        else{
            if(!jToggleButton1.isSelected()){
                String text="<sup>^</sup>";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+13>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="^ "; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="^"+" ";
                //jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"ln<sup> </sup>");
            }
            else{
                String text="<sup>x</sup>\u221a";
                int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
                //System.out.println(caretpos);
                if(caretpos+30>402){
                    jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
                    temp=text; exp+='$';
                }
                else{
                    temp+="n\u221a"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
                }
                exp+="n\u221a"+" ";
            }
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        int i=jTextPane1.getText().length()-20;
        for(int j=0;j<19;j++)
        System.out.print((int)(jTextPane1.getText().charAt(i--))+" ");
        System.out.println(jTextPane1.getText());
        MyHtml2Text parser = new MyHtml2Text(); 
        try{
         String text=jTextPane1.getText();
         parser.parse(new java.io.StringReader(text));
         text=parser.getText();
          System.out.println(parser.getText());
        //for(int i=0;i<text.length();i++) System.out.print((int)text.charAt(i)+" ");
        //if(parser.getText().equals("")) System.out.print("yes");
        }
        catch(Exception e) {}// TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String text="(";
        int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
        // System.out.println(caretpos);
        if(caretpos+10>402){
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+="("; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+="("+" ";// TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String text=")";
        int caretpos=jTextPane1.getCaret().getMagicCaretPosition().x;
        // System.out.println(caretpos);
        if(caretpos+10>402){
            jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+"<div></div><sup> &nbsp</sup>"+text);
            temp=text; exp+='$';
        }
        else{
            temp+=")"; jTextPane1.setText(jTextPane1.getText().substring(0, jTextPane1.getText().length()-19)+text);
        }
        exp+=")"+" ";// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
        /*if(evt.getNewState()==java.awt.event.WindowEvent.WINDOW_CLOSED) jDialog1.dispose();
        if(evt.getNewState()==1) jDialog1.setVisible(false);
        if(evt.getNewState()==0) jDialog1.setVisible(true);*/
    }//GEN-LAST:event_formWindowStateChanged

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
            java.util.logging.Logger.getLogger(Calculator1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    // End of variables declaration//GEN-END:variables
}
