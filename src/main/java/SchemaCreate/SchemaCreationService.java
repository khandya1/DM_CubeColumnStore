package SchemaCreate;

import Schema.AggregateFunc;
import Schema.StarSchema;
import Schema.Type;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaCreationService {

    private SchemaCreation schemaCreation= new SchemaCreation();
    private ReadWriteXmlFile readWriteXmlFile= new ReadWriteXmlFile();


    public boolean doesSchemaExistService(String name) throws IOException {return schemaCreation.doesSchemaExist(name);}


    public StarSchema newSchemaService(String name){
        return schemaCreation.newSchema(name);
    }


    public void insertDimensionService(StarSchema starSchema, String name, List<String> attributeNames) {
        schemaCreation.insertDimension(starSchema, name, attributeNames);
    }

    public void insertFactService(StarSchema starSchema, String name, Type type, ArrayList<AggregateFunc> aggregateFunc) {
        schemaCreation.insertFact(starSchema, name, type, aggregateFunc);
    }

    public String writeSchemaService(StarSchema starSchema) throws IOException, JAXBException {
        return schemaCreation.writeSchemaOuter(starSchema);
    }
}
