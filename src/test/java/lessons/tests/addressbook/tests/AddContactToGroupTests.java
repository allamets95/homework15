package lessons.tests.addressbook.tests;

import lessons.tests.addressbook.model.ContactData;
import lessons.tests.addressbook.model.Contacts;
import lessons.tests.addressbook.model.GroupData;
import lessons.tests.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddContactToGroupTests extends TestBase {

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
    public void testAddContactToGroup() {
        Contacts contacts = app.db().contacts();
        ContactData contact = contacts.iterator().next();
        Groups groups = app.db().groups();
        GroupData forGroup = groups.iterator().next();

        Boolean contactAssignedToGroup = false;
        if (contact.getGroups().size() > 0) {
            if (contact.getGroups().iterator().next().getId() == forGroup.getId()) {
                contactAssignedToGroup = true;
                app.contact().addToGroup(contact, forGroup);
                app.contact().removeFromGroupByContactId(contact.getId());
            }
        }

        Boolean contactNotAssignedToGroup = true;
        if (contactAssignedToGroup) {
            for (ContactData c : app.db().contacts()) {
                if (c.getId() == contact.getId()) {
                    if (c.getGroups().size() > 0) {
                        if (c.getGroups().iterator().next().getId() == forGroup.getId()) {
                            contactNotAssignedToGroup = false;
                            break;
                        }
                    }
                }

            }
        }


        assertThat(contactNotAssignedToGroup, equalTo(true));


        app.contact().addToGroup(contact, forGroup);
        assertThat(app.contact()
                .ifThisContactAssignedToGroup(app.db().contacts(), contact, forGroup, "add_this"), equalTo(true));

        app.contact().removeFromGroupByContactId(contact.getId());
        assertThat(app.contact()
                .ifThisContactAssignedToGroup(app.db().contacts(), contact, forGroup, "remove_this"), equalTo(true));

    }

}