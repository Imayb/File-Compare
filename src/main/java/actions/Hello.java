package actions;

import classes.compare;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Hello extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        //runs the file selector to choose a file to compare it to
          file f = new file();

            f.actionPerformed(null);

            String pathName = "C:\\Users\\monic\\Documents\\test.txt";// f.temp;


           //this creates an instance of the code on opened java file class
         Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
         Project project = e.getRequiredData(CommonDataKeys.PROJECT);
            Document document = editor.getDocument();
            String sample= null;
        String something = document.getText().toString();
      //  this is a tester to make sure that the class is being copied to the string correctly
       //  WriteCommandAction.runWriteCommandAction(project, () ->
         //        document.insertString(0,something)
       //   );
       //  this copies the java class to a string

        try {
            sample = path(pathName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String finalSample = sample;
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.insertString(0, finalSample)
           );
        //this isn't necessary to the project but can be used for later modification
        //   Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        // int start = primaryCaret.getSelectionStart();
        //  int end = primaryCaret.getSelectionEnd();
        compare comp = new compare();
        if(sample!= null) {
            comp.filesCompareByWord(sample, something);
            try {
                comp.filesCompareByLine(Path.of(sample), Path.of(something));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }
     public String path(String pathName) throws IOException {
       String toCompare = null;

         Path filePath = Path.of(pathName);
        String content = Files.readString(filePath);
        try {
            FileReader fileReader
                    = new FileReader(pathName);

         //   System.out.println("Reading char by char : \n");
        //    int i;
        //    while ((i = fileReader.read()) != -1) {
               // System.out.print((char)i);
        //    }

            System.out.println("Reading using array : \n");
            char[] charArray = new char[10];
            fileReader.read(charArray);
          //  System.out.print(charArray);

            fileReader.close();
            //System.out.println("FileReader closed!");
        }
        catch (Exception e) {
            //System.out.println(e);
        }
       return content;
      }


    public class file  extends JFrame implements ActionListener {//}, files {
        Popup f;
        JFrame frame = new JFrame("File Comparer");
        JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //Label to display selected name
        JLabel one = new JLabel("No file selected");
        public String temp;

        public file() {

            //default frame and size informtion that would need to be changed later on for beautification
            frame.setSize(400, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Open button that is used to open the file chooser
            JButton open = new JButton("Open");
            open.addActionListener(this);

            JButton submit = new JButton("Submit");
            submit.addActionListener(this);

            JPanel pan = new JPanel();

            //adds the button and the lable to the panel
            pan.add(open);
            pan.add(one);
            pan.add(submit);

            frame.add(pan);
            frame.setLocationRelativeTo(null);


            frame.show();


        }

        @Override
        public void actionPerformed(@NotNull ActionEvent actionEvent) {

//        Editor editor = actionEvent.getRequiredData(CommonDataKeys.EDITOR);
//        Project project = actionEvent.getRequiredData(CommonDataKeys.PROJECT);
//        Document document = editor.getDocument();
//
//        // this copies the java class to a string
//        String something = document.getText();
//
//        //this isn't necessary to the project but can be used for later modification
//        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
//        int start = primaryCaret.getSelectionStart();
//        int end = primaryCaret.getSelectionEnd();
//
//        //this is a tester to make sure that the class is being copied to the string correctly
//        WriteCommandAction.runWriteCommandAction(project, () ->
//                document.insertString(0,something)
//        );


            //displays the frame made in the class
            frame.show();

            // restricts the type of files allowed
            file.setAcceptAllFileFilterUsed(false);

            //Title for the dialog
            file.setDialogTitle("Please select a file to compare");

            // FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .txt files", ".txt");
            //file.addChoosableFileFilter(filter);

            System.out.println("Hello World");
            //gets the first action committed
            String first = actionEvent.getActionCommand();
            String second = actionEvent.getActionCommand();

            System.out.println(first);
            //activates when open is selected
            if (first.equals("Open")) {
                int check = file.showOpenDialog(null);
                //this takes in the path of the file selected

                one.setText(file.getSelectedFile().getAbsolutePath());

                temp = one.getText().toString();
                //   File myObh = new File(one.getText());

                //  return myObh;
            }

            JTextArea textArea = new JTextArea();
            JFrame f= new JFrame();
            JTextArea area=new JTextArea("File:");

            area.setBounds(10,30, 200,200);
            f.add(area);
            f.setSize(300,300);
            f.setLayout(null);
            f.setVisible(true);
            f.add(textArea);

            //textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial", Font.PLAIN, 20));

            if (second.equals("Submit")) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);

                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    Scanner fileIn = null;

                    try {
                        fileIn = new Scanner(file);
                        if (file.isFile()) {
                            while (fileIn.hasNextLine()) {
                                String line = fileIn.nextLine() + "\n";
                                textArea.append(line);
                            }
                        }
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } finally {
                        fileIn.close();
                    }
                }


            }


        }

    }
}
