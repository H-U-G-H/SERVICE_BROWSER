import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class DayOfTheWeekService implements Service
{
    JLabel outputLabel;
    JComboBox month;
    JTextField day;
    JTextField year;

    @Override
    public JPanel getGuiPanel()
    {
        JPanel panel = new JPanel();
        JButton button = new JButton("Do it!");
        button.addActionListener(new DoItListener());
        outputLabel = new JLabel("date appears here");
        DateFormatSymbols dateStuff = new DateFormatSymbols();
        month = new JComboBox(dateStuff.getMonths());
        day = new JTextField(8);
        year = new JTextField(8);
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Month"));
        inputPanel.add(month);
        inputPanel.add(new JLabel("Day"));
        inputPanel.add(day);
        inputPanel.add(new JLabel("Year"));
        inputPanel.add(year);
        panel.add(inputPanel);
        panel.add(button);
        panel.add(outputLabel);

        return panel;
    }

    public class DoItListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int monthNum = month.getSelectedIndex();
            int dayNum = Integer.parseInt(day.getText());
            int yearNum = Integer.parseInt(year.getText());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, monthNum);
            calendar.set(Calendar.DAY_OF_MONTH, dayNum);
            calendar.set(Calendar.YEAR, yearNum);
            Date date = calendar.getTime();
            String dayOfWeek = new SimpleDateFormat("EEEE").format(date);
            outputLabel.setText(dayOfWeek);
        }
    }
}
