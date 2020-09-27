package src;

import javax.swing.*;

public class MainForm extends JFrame
{
    private JPanel body;

    public MainForm()
    {
        this.setTitle("Task");
        this.setContentPane(body);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(800, 800);
        this.setResizable(true);
        this.setFocusable(true);
        this.setVisible(true);

        canvas my_canvas = new canvas();
        my_canvas.setFocusable(true);
        my_canvas.requestFocus();
        body.add(my_canvas);
    }
}
