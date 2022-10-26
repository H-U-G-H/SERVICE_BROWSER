import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;

public class ServiceBrowser
{
    JPanel mainPanel;
    JComboBox serviceList;
    ServiceServer server;

    public void buildGUI()
    {
        JFrame frame = new JFrame("RMI Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        mainPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        Object[] services = getServicesList();
        // ���� ����� ��������� ����� �� ������� RMI,
        // �������� "��������" � �������� ����� getServiceList()

        serviceList = new JComboBox(services);
        // ��������� ������� (������ ��������� Object)
        // � ������ JComboBox (�������������� ������).
        // JComboBox ����� ��� ������� �� ����� ��� ������ �������.

        frame.getContentPane().add(BorderLayout.NORTH, serviceList);
        serviceList.addActionListener(new MyListListener());
    }

    void loadService(Object serviceSelection)
    {
        try
        {
            Service svc = server.getService(serviceSelection);

            mainPanel.removeAll();
            mainPanel.add(svc.getGuiPanel());
            mainPanel.validate();
            mainPanel.repaint();
            // ����� �� ��������� �� ������ ��������� ������,
            // ����� ���� ��� ������������ ������� ��� � ������.
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    Object[] getServicesList()
    {
        Object obj = null;
        Object[] services = null;

        try
        {
            obj = Naming.lookup("rmi://127.0.0.1/ServiceServer");
            // ��������� ����� �� ������� RMI � �������� "��������".
        }
        catch(Exception exception){exception.printStackTrace();}

        server = (ServiceServer) obj;
        // �������� ��� "��������" � ���� ��������� ����������,
        // ����� � ���������� ������� �� �� ����� getServiceList().
        try
        {
            services = server.getServiceList();
            // ����� getServiceList() ���������� ������ � ���������� ���� Object.
            // �� ����� ������� �� � ������ JComboBox, � ������� ��������
            // ������������ ����� ������ ���� �����.
        }
        catch(Exception exception){exception.printStackTrace();}
        return services;
    }

    class MyListListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Object selection = serviceList.getSelectedItem();
            loadService(selection);
            // ���� �� ����� �� ���� ������, ������, ������������ ������ ������� �� ������ JComboBox.
            // �� ���� ���� ������� � ��������� ��������������� ������.
        }
    }

    public static void main(String[] args)
    {
        new ServiceBrowser().buildGUI();
    }
}
