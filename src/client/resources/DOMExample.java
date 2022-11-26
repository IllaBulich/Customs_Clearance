package client.resources;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DOMExample {
    // Список для сотрудников из XML файла
    private static ArrayList<Rates> employees = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(new File("src/client/duties.xml"));

        // Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("rates");

        // Перебор всех элементов employee
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);
            Node employeeParent = employee.getParentNode();
            // Получение атрибутов каждого элемента
            NamedNodeMap attributes = employee.getAttributes();
            NamedNodeMap attributesParent = employeeParent.getAttributes();
            System.out.println( attributesParent.getNamedItem("yearsMin").getNodeValue());
            // Добавление сотрудника. Атрибут - тоже Node, потому нам нужно получить значение атрибута с помощью метода getNodeValue()
            employees.add(new Rates(
                    Float.parseFloat(attributes.getNamedItem("volumeMin").getNodeValue()),
                    Float.parseFloat(attributes.getNamedItem("volumeMax").getNodeValue()),
                    Float.parseFloat(attributes.getNamedItem("priceMin").getNodeValue()),
                    Float.parseFloat(attributes.getNamedItem("priceMax").getNodeValue()),
                    Float.parseFloat(attributes.getNamedItem("fromCost").getNodeValue()),
                    Float.parseFloat(attributes.getNamedItem("bid").getNodeValue())
            ));
        }

        // Вывод информации о каждом сотруднике
        for (Rates employee : employees)
            System.out.println(
                    employee.getVolumeMin() + ' ' +
                    employee.getVolumeMax() + ' ' +
                    employee.getPriceMin() + ' ' +
                    employee.getPriceMax() + ' ' +
                    employee.getFromCost() + ' ' +
                    employee.getBid()
                    );
    }
}