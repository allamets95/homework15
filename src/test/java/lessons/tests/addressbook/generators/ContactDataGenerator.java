package lessons.tests.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.tests.TestBase;
import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.tests.TestBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator extends TestBase {
    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;
    private File photo;


    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();


    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContact(count);
        if (format.equals("csv")) saveAsCsv(contacts, new File(file));
        else if (format.equals("xml")) saveAsXml(contacts, new File(file));
        else if (format.equals("json")) saveAsJson(contacts, new File(file));
        else System.out.println("Unrecognized format " + format);
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }

    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }

    }


    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file);) {
            for (ContactData contact : contacts) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(), contact.getMiddlename(),
                        contact.getMobile_phone(), contact.getWork_phone(), contact.getHome_phone(), contact.getFax_phone(), //contact.getGroup(),
                        contact.getTitle_contact(),
                        contact.getNickname(), contact.getAddress(), contact.getCompany(), contact.getEmail1_contact(), contact.getEmail2_contact(), contact.getEmail3_contact(), contact.getPhoto()));
            }
        }

    }

    private List<ContactData> generateContact(int count) {
        List<ContactData> contacts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstname(String.format("UserName%s", i)).withMiddlename(String.format("UserMidldleName%s", i))
                    .withLastname(String.format("UserLastName%s", i))//.withGroup("test")
                    .withNickname(String.format("Nickname%s", i)).withTitle_contact(String.format("Title%s", i))
                    .withCompany(String.format("Company%s", i)).withMobile_phone(String.format("+38097223331%s", i)).withHome_phone(String.format("+7 774 777 77%s", i))
                    .withFax_phone(String.format("+380972233311%s", i)).withWorkPhone(String.format("+38097225531%s", i)).withEmail(String.format("testemail%s@mail.ru", i))
                    .withEmail1(String.format("testemail2%s@mail.ru", i)).withEmail2(String.format("testemail3%s@mail.ru", i))
                    .withAddress(String.format("Ukraine, ul. TestStr %s, kv. %s", i, i)).withPhoto(photo = new File("src/test/resources/photo.jpg")));

        }
        return contacts;
    }
}
