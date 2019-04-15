package lessons.tests.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.model.Contacts;
import lessons.tests.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;




public class ContactAddressTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsCsv() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();
        while (line != null) {
            String split[] = line.split(";");
            list.add(new Object[]{new ContactData()
                    .withFirstname(split[0]).withMiddlename(split[1])
                    .withLastname(split[2])//.withGroup(split[3])
                    .withNickname(split[4]).withTitle_contact(split[5])
                    .withCompany(split[6]).withMobile_phone(split[7]).withHome_phone(split[8])
                    .withFax_phone(split[9]).withWorkPhone(split[10]).withEmail(split[11])
                    .withEmail1(split[12]).withEmail2(split[13])
                    .withAddress(split[14]).withPhoto(split[15])});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
        }.getType());
        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void ensurePreconditions() {
        createOneGroupIfGroupsEmpty();
    }


    @Test(dataProvider = "validContactsFromJson")
    public void testAddNewContact(ContactData contact) {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData newContact = contact.inGroup(groups.iterator().next());
        app.contact().createContact(newContact);
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
}