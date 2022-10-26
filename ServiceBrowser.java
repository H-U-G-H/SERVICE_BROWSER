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
        // Этот метод выполняет поиск по реестру RMI,
        // получает "заглушку" и вызывает метод getServiceList()

        serviceList = new JComboBox(services);
        // Добавляем сервисы (массив элементов Object)
        // в виджет JComboBox (раскрывающийся список).
        // JComboBox знает как вывести на экран все строки массива.

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
            // Здесь мы добавляем на панель настоящий сервис,
            // после того как пользователь выберет его в списке.
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
            // Выполняем поиск по реестру RMI и получаем "заглушку".
        }
        catch(Exception exception){exception.printStackTrace();}

        server = (ServiceServer) obj;
        // Приводим тип "заглушки" к типу удалённого интерфейса,
        // чтобы в дальнейшем вызвать из неё метод getServiceList().
        try
        {
            services = server.getServiceList();
            // Метод getServiceList() возвращает массив с элементами типа Object.
            // Мы можем вывести их в списке JComboBox, с помощью которого
            // пользователь будет делать свой выбор.
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
            // Если мы дошли до этой строки, значит, пользователь выбрал элемент из списка JComboBox.
            // Мы берём этот элемент и загружаем соответствующий сервис.
        }
    }

    public static void main(String[] args)
    {
        new ServiceBrowser().buildGUI();
    }
}
