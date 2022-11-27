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
import java.util.Objects;

public class Сalculations {

    private int cost;
    private float age;
    private int volume;

    private NodeList employeeElements;

    public Сalculations(int cost, float age, int volume) throws ParserConfigurationException, IOException, SAXException {
        this.cost = cost;
        this.age = age;
        this.volume = volume;

        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(new File("src/client/resources/duties.xml"));

        // Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        this.employeeElements = document.getDocumentElement().getElementsByTagName("table");
    }

    public int PhysicalCalc() {
        // Перебор всех элементов table
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);

            // Получение атрибутов каждого элемента
            NamedNodeMap attributesYaer = employee.getAttributes();

            NodeList attributesParent = employee.getChildNodes();


            if (Logics(attributesYaer, "yearsMin", "yearsMax", age) && Objects.equals(attributesYaer.getNamedItem("engine").getNodeValue(), "null")) {

                for (int j = 0; j < attributesParent.getLength(); j++) {
                    if (!attributesParent.item(j).getNodeName().equals("#text")) {
                        NamedNodeMap attributes = attributesParent.item(j).getAttributes();

                        if (!Objects.equals(attributes.getNamedItem("volumeMin").getNodeValue(), "null")) {
                            if (Logics(attributes, "volumeMin", "volumeMax", volume)) {
                                return (int) (volume * Float.parseFloat(attributes.getNamedItem("bid").getNodeValue()));
                            }
                        } else {
                            if (Logics(attributes, "priceMin", "priceMax", cost)) {
                                return (int) Math.max(cost * Float.parseFloat(attributes.getNamedItem("fromCost").getNodeValue()),
                                        volume * Float.parseFloat(attributes.getNamedItem("bid").getNodeValue()));
                            }
                        }

                    }
                }
            }
        }
        return 0;
    }

    public int LegalPetrol( String engineType) {
// Перебор всех элементов table
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);

            // Получение атрибутов каждого элемента
            NamedNodeMap attributesYaer = employee.getAttributes();

            NodeList attributesParent = employee.getChildNodes();

            if (Logics(attributesYaer, "yearsMin", "yearsMax", age)&& Objects.equals(attributesYaer.getNamedItem("engine").getNodeValue(), engineType)){

                for (int j = 0; j < attributesParent.getLength(); j++) {
                    if (!attributesParent.item(j).getNodeName().equals("#text")) {
                        NamedNodeMap attributes = attributesParent.item(j).getAttributes();

                        if (Logics(attributes, "volumeMin", "volumeMax", cost)) {
                            return (int) Math.max(!Objects.equals(attributes.getNamedItem("fromCost").getNodeValue(),"null") ? cost * Float.parseFloat(attributes.getNamedItem("fromCost").getNodeValue()):0,
                                    volume * Float.parseFloat(attributes.getNamedItem("bid").getNodeValue()));
                        }
                    }
                }
            }
        }
        return 0;
    }

    private boolean Logics(NamedNodeMap attr, String attributesMin, String attributesMax, float x) {
        if (Objects.equals(attr.getNamedItem(attributesMax).getNodeValue(), "null")) {
            if (Integer.parseInt(attr.getNamedItem(attributesMin).getNodeValue()) < x)
                return true;
        }else {
            if (Integer.parseInt(attr.getNamedItem(attributesMin).getNodeValue()) < x &&
                    x <= Integer.parseInt(attr.getNamedItem(attributesMax).getNodeValue()))
                return true;
        }
        return false;
    }


}
