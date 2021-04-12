package SchemaCreate;

import Schema.StarSchema;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadWriteXmlFile {
    public boolean createSchemaIfNotExist(String schemaName) {
        String currentDirectory = System.getProperty("user.dir");
        String fName = currentDirectory + "/storage/" +schemaName + ".xml";
        File f = new File(fName);
        if(f.exists()) {
            System.out.println("Schema exists. Please use different name.");
            return true;
        }
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating schema. Please try again.");
                return false;
            }
        }
        System.out.println("XML created successfully");
        return true;
    }

    public boolean writeStarSchema(StarSchema s) throws JAXBException, FileNotFoundException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + s.getName() + ".xml";
        File f = new File(fName);
        System.out.println(f.exists());
        if(f.exists())
        {
            JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(s, new FileOutputStream(fName));
        }
        else
            System.out.println("file not found");
        return true;
    }
}
