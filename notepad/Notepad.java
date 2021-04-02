package com.notepad;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.concurrent.ThreadPoolExecutor;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area = new JTextArea();
    JScrollPane pane = new JScrollPane(area);
    String text;
    Notepad() {
        setBounds(0, 0, 1080, 720);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newWindow = new JMenuItem("File");
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newWindow.addActionListener(this);
        JMenuItem openFile = new JMenuItem("Open");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openFile.addActionListener(this);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveFile.addActionListener(this);
        JMenuItem printFile = new JMenuItem("Print");
        printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        printFile.addActionListener(this);
        JMenuItem exitWindow = new JMenuItem("Exit");
        exitWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        exitWindow.addActionListener(this);
        file.add(newWindow);
        file.add(openFile);
        file.add(saveFile);
        file.add(printFile);
        file.add(exitWindow);
        JMenu edit = new JMenu("Edit");
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        add(menuBar);
        setJMenuBar(menuBar);
        add(pane, BorderLayout.CENTER);
        pane.setBorder(BorderFactory.createEmptyBorder());
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("File")) { area.setText(""); }
        else if (e.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt","txt");
            chooser.addChoosableFileFilter(restrict);
            int action = chooser.showOpenDialog(this);
            if (action!= JFileChooser.APPROVE_OPTION){return;}
            File file = chooser.getSelectedFile();
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader,null);
            }catch(Exception e1){}
        }
        else if (e.getActionCommand().equals("Save")) { JFileChooser save = new JFileChooser();save.setApproveButtonText("Save");int action = save.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File filename = new File(save.getSelectedFile() + ".txt");
            BufferedWriter outfile = null;
            try{ outfile = new BufferedWriter(new FileWriter(filename)); area.write(outfile); }
            catch (Exception e1){ }
        }
        else if (e.getActionCommand().equals("Print")) { try{area.print();}catch(Exception e1){}}
        else if (e.getActionCommand().equals("Exit")) { System.exit(getDefaultCloseOperation());}
//        yaha se neeche ke else if kaaam nhi kr ra hai...
        else if (e.getActionCommand().equals("Copy")){text = area.getSelectedText();}
        else if (e.getActionCommand().equals("Paste")){area.insert(text,area.getCaretPosition());}
        else if (e.getActionCommand().equals("Cut")){text  = area.getSelectedText();area.replaceRange("",area.getSelectionStart(),area.getSelectionEnd());}
        else if (e.getActionCommand().equals("Select All")){area.selectAll();}
        else if (e.getActionCommand().equals("About")){new About().setVisible(true);}
    }
    public static void main(String[] args) {
        Notepad notepad = new Notepad();
        notepad.setVisible(true);
//        About about = new About();
    }
}