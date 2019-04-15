package lessons.tests.addressbook.appmanager;

import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.model.Contacts;
import lessons.tests.addressbook.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.util.List;


public class ContactHelper extends HelperBase {


    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitNewContact() {
        click(By.name("submit"));
        click(By.id("logo"));

    }

    public void fillNewContact(ContactData contactData, boolean creation) {
        if (creation) {
            type(By.name("firstname"), contactData.getFirstname());
            type(By.name("middlename"), contactData.getMiddlename());
            type(By.name("lastname"), contactData.getLastname());
            attach(By.name("photo"), contactData.getPhoto());
            //new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            //new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());

            }
            type(By.name("nickname"), contactData.getNickname());
            type(By.name("title"), contactData.getTitle_contact());
            type(By.name("company"), contactData.getCompany());
            type(By.name("home"), contactData.getHome_phone());
            type(By.name("mobile"), contactData.getMobile_phone());
            type(By.name("work"), contactData.getWork_phone());
            type(By.name("fax"), contactData.getFax_phone());
            type(By.name("email"), contactData.getEmail1_contact());
            type(By.name("email2"), contactData.getEmail2_contact());
            type(By.name("email3"), contactData.getEmail3_contact());
            type(By.name("address"), contactData.getAddress());

        } else {
            type(By.name("firstname"), contactData.getFirstname());
            type(By.name("lastname"), contactData.getLastname());
            Assert.assertFalse(isElementPresent(By.name("new_group")));

        }


    }


    public void submit() {
        click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    }


    public void deleteContact(ContactData contact) {

        selectById(contact.getId());
        click(By.xpath("//div/div[4]/form[2]/div[2]/input"));
        contactCash = null;
        wd.switchTo().alert().accept();
        click(By.id("logo"));

    }


    public void modifyContact(int id, ContactData contact) {
        selectToEditContact(id);
        fillNewContact(contact, false);
        submit();
        contactCash = null;
        click(By.id("logo"));
    }

    public void modifyContact(ContactData contact) {
        selectToEditContact(contact.getId());
        fillNewContact(contact, false);
        submit();
        contactCash = null;
        click(By.id("logo"));
    }

    public void createContact(ContactData contact) {
        click(By.linkText("add new"));
        fillNewContact(contact, true);
        submitNewContact();
        contactCash = null;
        click(By.id("logo"));
    }


    public boolean isThereContact() {

        return isElementPresent(By.xpath("//div/div[4]/form[2]/table/tbody/tr[2]/td[1]/input"));
    }

    public void createSomeOne() {
        click(By.linkText("add new"));

        File photo = new File("src/test/resources/photo.jpg");


        ContactData contact = new ContactData().withFirstname("UserName2").withMiddlename("UserMidldleName2")
                .withLastname("UserLastName2").withNickname("User2").withTitle_contact("mr.")
                .withCompany("Home1").withMobile_phone("+380672233344").withHome_phone("+7 664 666 66")
                .withFax_phone("+380962737377").withWorkPhone("+380(066)66366333").withEmail("email88@mail.ru")
                .withEmail1("email2@mail.ru").withEmail2("email73@mail.ru")
                .withAddress("Ukraine, ul. Mira 1, kv. 5").withPhoto(photo);

        fillNewContact(contact, true);

        submitNewContact();
        contactCash = null;
        click(By.id("logo"));

    }


    public void select(int index) {
        click(By.id("logo"));
        wd.findElements(By.name("selected[]")).get(index).click();

    }

    public void selectById(int id) {
//        click(By.id("logo"));
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();


    }


    private Contacts contactCash = null;


    public Contacts all() {
        if (contactCash != null) {
            return new Contacts(contactCash);

        }
        contactCash = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmail = cells.get(4).getText();
            String allPhones = cells.get(5).getText();

            contactCash.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName).withAllEmail(allEmail).withAllPhones(allPhones).withAddress(address));

        }

        return new Contacts(contactCash);
    }


    public ContactData infoFromEditForm(ContactData contact) {
        selectToEditContact(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String middlename = wd.findElement(By.name("middlename")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String nickname = wd.findElement(By.name("nickname")).getAttribute("value");
        String company = wd.findElement(By.name("company")).getAttribute("value");
        String title = wd.findElement(By.name("title")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String fax = wd.findElement(By.name("fax")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email1 = wd.findElement(By.name("email2")).getAttribute("value");
        String email2 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");

        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withNickname(nickname).withMiddlename(middlename).withCompany(company)
                .withTitle_contact(title).withHome_phone(home).withMobile_phone(mobile)
                .withWorkPhone(work).withEmail(email)
                .withEmail1(email1).withEmail2(email2).withAddress(address).withFax_phone(fax);


    }

    public String infoFromViewForm(ContactData contact) {
        selectToViewContact(contact.getId());
        String allViewInfo = wd.findElement(By.xpath("//*[@id='content']")).getText();
        String clearViewInfo = allViewInfo.replaceAll("\\s*\\(www\\..*?\\)", "");
        click(By.id("logo"));
           return clearViewInfo;

    }

    public void selectToEditContact(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public void selectToViewContact(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(6).findElement(By.tagName("a")).click();

    }

    public void addToGroup(ContactData contactToMove, GroupData groupToAssign) {
        selectById(contactToMove.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(groupToAssign.getName());
        pressButtonAddToGroup();
        gotoPageOfGroup();

    }

    public void gotoPageOfGroup() {
        click(By.xpath("//*[@id='content']/div/i/a"));

    }

    public void removeFromGroupByContactId(int id) {
        selectById(id);
        click(By.name("remove"));
        click(By.id("logo"));

    }

    public void pressButtonAddToGroup() {
        click(By.xpath("//*[@id='content']/form[2]/div[4]/input"));

    }

    public Boolean ifThisContactAssignedToGroup(Contacts contacts, ContactData contact, GroupData group, String action) throws IllegalArgumentException {
        Boolean flag;
        if (action.equals("add_this")) {
            flag = false;
        } else if (action.equals("remove_this")) {
            flag = true;
        } else {
            throw new IllegalArgumentException("\'" + action + "\'" + " action is not supported");
        }

        for (ContactData c : contacts) {
            if (c.getId() == contact.getId()) {
                if (c.getGroups().size() > 0) {
                    if (c.getGroups().iterator().next().getId() == group.getId()) {
                        flag = !flag;
                        break;
                    }
                }
            }

        }
        return flag;
    }


}
