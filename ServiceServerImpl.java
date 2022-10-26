import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer
{
    // Сервисы будут храниться в коллекции HashMap.
    // Вместо одного объекта мы добавляем в коллекцию сразу два:
    // Ключ (как правило строку) и значение (любой объект).
    HashMap serviceList;

    public ServiceServerImpl() throws RemoteException
    {
        setUpServices();
    }

    private void setUpServices()
    {
        serviceList = new HashMap();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DayOfTheWeekService());
        serviceList.put("Visual Music Service", new MiniMusicService());
        // При вызове конструктора инициализируем универсальные сервисы.
        // И помещаем их в HashMap
    }

    public Object[] getServiceList()
    {
        System.out.println("in remote");
        return serviceList.keySet().toArray();
        // Клиент вызывает этот метод чтобы получить список сервисов и отобразить их в обозревателе
        // (чтобы пользователь мог выбрать один из них).
        // Мы отправляем массив типа Object (хотя внутри он содержит строки),
        // который состоит только из ключей, хранящихся внутри HashMap.
    }

    public Service getService(Object serviceKey) throws RemoteException
    {
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
        // Клиент вызывает этот метод после того, как пользователь выберет сервис в раскрывающемся списке.
        // Код использует ключ (что изначально был отправлен клиенту),
        // чтобы получить из HashMap соответствующий сервис.
    }

    public static void main(String[] args)
    {
        try
        {
            Naming.rebind("ServiceServer", new ServiceServerImpl());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        System.out.println("Remote service is running");
    }
}
