package com.main;

import com.sun.codemodel.internal.JOp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Form1 {
    private JPanel panel1;
    private JTextField txtFirst;
    private JTextField txtLast;
    private JButton enterButton;
    private JButton displayButton;
    private JButton clearButton;
    private JButton exitButton;
    private JTextField txtGPA;
    private String[] mFirst = new String[20];
    private String[] mLast = new String[20];
    private double[] mGPA = new double[20];
    private int mIndex = 0;

    public Form1() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFirst.setText("");
                txtLast.setText("");
                txtGPA.setText("");
                txtFirst.requestFocus();
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ValidateInput() == false)
                {
                    return;
                }
                String first = txtFirst.getText();
                String last = txtLast.getText();
                double gpa = Double.parseDouble(txtGPA.getText());

                mFirst[mIndex] = first;
                mLast[mIndex] = last;
                mGPA[mIndex] = gpa;
                mIndex++;

                txtFirst.setText("");
                txtLast.setText("");
                txtGPA.setText("");
                txtFirst.requestFocus();
            }
        });
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = Integer.parseInt(JOptionPane.showInputDialog("Please enter desired index."));
                txtFirst.setText(mFirst[index]);
                txtLast.setText(mLast[index]);
                txtGPA.setText(Double.toString(mGPA[index]));
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Do you want to quit the application?") == JOptionPane.YES_OPTION)
                {
                    FileOutputStream sw = null;
                    try
                    {
                        sw = new FileOutputStream("output.txt");
                        byte[] n = "\n".getBytes();
                        byte[] t = "\t".getBytes();
                        for(int ctr =0; ctr<mIndex; ctr++)
                        {
                            byte[] f = mFirst[ctr].getBytes();
                            byte[] l = mLast[ctr].getBytes();
                            byte[] g = Double.toString(mGPA[ctr]).getBytes();
                            sw.write(f);
                            sw.write(t);
                            sw.write(l);
                            sw.write(t);
                            sw.write(g);
                            sw.write(n);
                        }

                    }
                    catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if(sw != null) {
                            try {
                                sw.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    System.exit(0);
                }
            }
        });
    }

    public boolean TryParse()
    {
        try {
            Double.parseDouble(txtGPA.getText());
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


    public boolean ValidateInput()
    {
        if(txtFirst.getText() == "")
        {
            JOptionPane.showMessageDialog(null, "Please enter your first name.");
            txtFirst.requestFocus();
            return false;
        }
        if(txtLast.getText() == "")
        {
            JOptionPane.showMessageDialog(null, "Please enter your last name.");
            txtLast.requestFocus();
            return false;
        }
        if(TryParse() == false)
        {
            JOptionPane.showMessageDialog(null, "Please enter a real number in GPA.");
            txtGPA.requestFocus();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FileIO");
        frame.setContentPane(new Form1().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
