package Utils;

import models.DataModel;
import models.Equipment;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportInXml {

    private List <DataModel> wells;
    private String file;


    public void setFile (String fileName) {
        this.file= fileName;
        ForOutputXml forOutputXml = new ForOutputXml();
        wells = forOutputXml.getWellInXml();
    }

    public void saveFile() throws Exception {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(file));

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");

        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);
        StartElement dataStartElement = eventFactory.createStartElement("", "", "test_db");
        eventWriter.add(dataStartElement);
        eventWriter.add(end);

        for (DataModel dataModel : wells) {
            Map<String, String> well_attributes = new HashMap<>();
            well_attributes.put("name", dataModel.getName());
            well_attributes.put("id", Integer.toString(dataModel.getId()));

            List<TextOfXml> textsOfXmls = new ArrayList<>();
            for ( Equipment equipment : dataModel.getEquipments() ) {

                TextOfXml textOfXml = new TextOfXml();
                textOfXml.setName("equipment");
                textOfXml.setSourceTable("well");

                Map<String, String> eq_attrs = new HashMap<>();
                eq_attrs.put("name", equipment.getName());
                eq_attrs.put("id", Integer.toString(equipment.getId()));
                textOfXml.setAttributes(eq_attrs);

                textsOfXmls.add(textOfXml);
            }
            createNode(eventWriter, "well", well_attributes, "", textsOfXmls);
        }

        eventWriter.add(eventFactory.createEndElement("", "", "dbinfo"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void createNode(
            XMLEventWriter eventWriter,
            String name,
            Map<String, String> attributes,
            String value,
            List<TextOfXml> textsOfXmls

    ) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        for(Map.Entry<String, String> entry : attributes.entrySet()){
            Attribute attribute = eventFactory.createAttribute(entry.getKey(), entry.getValue());
            eventWriter.add(attribute);
        }
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        if(!textsOfXmls.isEmpty()) {
            eventWriter.add(end);
            for( TextOfXml textOfXml : textsOfXmls ) {
                createNode(eventWriter, textOfXml.getName(), textOfXml.getAttributes(), "");
            }
            eventWriter.add(tab);
        }

        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

    private void createNode(
            XMLEventWriter eventWriter,
            String name,
            Map<String, String> attributes,
            String value
    ) throws XMLStreamException {
        List<TextOfXml> textsOfXmls = new ArrayList<>();
        createNode(eventWriter, name, attributes, value, textsOfXmls);
    }


    private class TextOfXml {
        private String name;
        private String sourceTable;
        private String value;
        private Map<String, String> attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceTable() {
            return sourceTable;
        }

        public void setSourceTable(String sourceTable) {
            this.sourceTable = sourceTable;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

}