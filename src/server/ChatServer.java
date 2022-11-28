package server;

import TCP.TCPConnection;
import TCP.TCPConnectionListener;
import org.xml.sax.SAXException;
import server.density.Calculations;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;

public class ChatServer implements TCPConnectionListener {

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());

                } catch (IOException e) {

                    System.out.println("TCPConnection exeption...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Client connect: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println("ttt");
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnect: " + tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnections exeption...");
    }

    private void sendToAllConnections(String message) {

        System.out.println(message);
        String[] subStr;
        subStr = message.split("-"); // Разделения строки str с помощью метода split()
        for (int i = 0; i < subStr.length; i++) {
            System.out.println(subStr[i]);
        }
        String engineType = subStr[0];
        int cost = 1 < subStr.length ? Integer.parseInt(subStr[1]) : 0;
        float age = 2 < subStr.length ? Float.parseFloat(subStr[2]):0;
        int volume = 3 < subStr.length ? Integer.parseInt(subStr[3]):0;
        Calculations calc = null;
        try {
            calc = new Calculations(cost, age, volume);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        if (Objects.equals(engineType, "null")) {
            message = String.valueOf(calc.PhysicalCalc());
        } else {
            message = String.valueOf(calc.LegalPetrol(engineType));
        }

        final int lh = connections.size();
        for (int i = 0; i < lh; i++) {
            connections.get(i).sendMessage(message);
        }
    }
}

