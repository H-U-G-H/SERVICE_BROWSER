import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer
{
    // ������� ����� ��������� � ��������� HashMap.
    // ������ ������ ������� �� ��������� � ��������� ����� ���:
    // ���� (��� ������� ������) � �������� (����� ������).
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
        // ��� ������ ������������ �������������� ������������� �������.
        // � �������� �� � HashMap
    }

    public Object[] getServiceList()
    {
        System.out.println("in remote");
        return serviceList.keySet().toArray();
        // ������ �������� ���� ����� ����� �������� ������ �������� � ���������� �� � ������������
        // (����� ������������ ��� ������� ���� �� ���).
        // �� ���������� ������ ���� Object (���� ������ �� �������� ������),
        // ������� ������� ������ �� ������, ���������� ������ HashMap.
    }

    public Service getService(Object serviceKey) throws RemoteException
    {
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
        // ������ �������� ���� ����� ����� ����, ��� ������������ ������� ������ � �������������� ������.
        // ��� ���������� ���� (��� ���������� ��� ��������� �������),
        // ����� �������� �� HashMap ��������������� ������.
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
