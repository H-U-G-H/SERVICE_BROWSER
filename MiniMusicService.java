import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniMusicService implements Service
{
    MyDrawPanel myPanel;

    @Override
    public JPanel getGuiPanel()
    {
        JPanel mainPanel = new JPanel();
        myPanel = new MyDrawPanel();
        JButton playButton = new JButton("Play it");
        playButton.addActionListener(new PlayItListener());
        mainPanel.add(myPanel);
        mainPanel.add(playButton);

        return mainPanel;
    }

    public class PlayItListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Sequencer sequencer = MidiSystem.getSequencer();
                sequencer.open();
                sequencer.addControllerEventListener(myPanel, new int[127]);
                Sequence sequence = new Sequence(Sequence.PPQ, 4);
                Track track = sequence.createTrack();

                for (int i = 0; i < 100; i += 4)
                {
                    int rNum = (int) ((Math.random() * 50) + 1);

                    if(rNum < 38)
                    {
                        track.add(makeEvent(144, 1, rNum, 100, i));
                        track.add(makeEvent(176, 1, 127, 0, i));
                        track.add(makeEvent(128, 1, rNum, 100, i + 2));
                    } // OUT OF IF-ELSE
                } // OUT OF LOOP
                sequencer.setSequence(sequence);
                sequencer.start();
                sequencer.setTempoInBPM(220);
            } catch (Exception ex) {ex.printStackTrace();} // OUT OF TRY-CATCH
        } // OUT OF METHOD
    } // OUT OF ACTION LISTENER

    public MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick)
    {
        MidiEvent event = null;

        try
        {
            ShortMessage message = new ShortMessage();
            message.setMessage(cmd, chan, one, two);
            event = new MidiEvent(message, tick);
        }
        catch (Exception e) {e.printStackTrace();}

        return event;
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener
    {
        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event)
        {
            msg = true;
            repaint();
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(300, 300);
        }

        @Override
        public void paintComponents(Graphics g)
        {
            if(msg)
            {
                Graphics2D g2d = (Graphics2D) g;
                int red = (int) (Math.random() * 250);
                int green = (int) (Math.random() * 250);
                int blue = (int) (Math.random() * 250);

                g.setColor(new Color(red, green, blue));

                int height = (int) ((Math.random() *120) + 10);
                int width = (int) ((Math.random() * 120) + 10);

                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x, y, height, width);
                msg = false;
            } // OUT OF IF-ELSE
        } // OUT OF PAINT METHOD
    } // OUT OF INNER CLASS
} // OUT OF MUSIC SERVICE


