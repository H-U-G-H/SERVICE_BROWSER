import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class DiceService
{
    JLabel label;
    JComboBox numOfDice;

    public JPanel getGuiPanel()
    {
        // Ёто самый важный метод.
        // ќн описан в интерфейсе Service и вызываетс€, когда сервис выбирают из списка и загружают.
        // ¬ методе getGuiPanel может происходить что угодно, но возвращать он должен виджет JPanel,
        // который и представл€ет собой пользовательский интерфейс дл€ игры в кости.

        JPanel panel = new JPanel();
        JButton button = new JButton("Roll 'em!");
        String[] choices = {"1", "2", "3", "4", "5"};
        numOfDice = new JComboBox(choices);
        label = new JLabel("dice values here");
        button.addActionListener(new RollEmListener());
        panel.add(numOfDice);
        panel.add(button);
        panel.add(label);
        return panel;
    }

    public class RollEmListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            // Ѕросаем кости
            String diceOutput = "";
            String selection = (String) numOfDice.getSelectedItem();
            int numOfDiceToRoll = Integer.parseInt(selection);

            for (int i = 0; i < numOfDiceToRoll; i++)
            {
                int r = (int) ((Math.random() * 6) + 1);
                diceOutput += (" " + r);
            }
            label.setText(diceOutput);
        }
    }
}
