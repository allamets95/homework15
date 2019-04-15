package lessons.tests.addressbook.tests;

import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.model.Contacts;
import org.hamcrest.core.IsEqual;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().isEmpty()) {
            if (app.db().groups().isEmpty()) {
                createOneGroupIfGroupsEmpty();
            }
            app.contact().createSomeOne();
        }
    }

    @Test
    public void testsContactDeletion() {

        Contacts before = app.db().contacts();
        ContactData deleteContact = before.iterator().next();
        app.contact().deleteContact(deleteContact);
        Contacts after = app.db().contacts();
        assertThat(after.size(), IsEqual.equalTo(before.size() - 1));
        assertThat(after, IsEqual.equalTo(before.without(deleteContact)));

    }
}
