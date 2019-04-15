/*
 * Данные для заполнения формы нового контакта.
 */
package lessons.tests.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lessons.tests.addressbook.model.GroupData;
import lessons.tests.addressbook.model.Groups;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {
    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;
    @Expose
    @Column(name = "firstname")
    private String firstname;
    @Expose
    @Column(name = "middlename")
    private String middlename;
    @Expose
    @Column(name = "lastname")
    private String lastname;

    @Expose
    @Column(name = "nickname")
    private String nickname;

    @Expose
    @Column(name = "title")
    private String title_contact;
    @Expose
    @Column(name = "company")
    private String company;
    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;
    @Transient
    private String address2;
    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String home_phone;
    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobile_phone;
    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String work_phone;
    @Expose
    @Column(name = "fax")
    @Type(type = "text")
    private String fax_phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups=new HashSet<GroupData>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Expose
    @Column(name = "email")
    @Type(type = "text")

    private String email1_contact;
    @Expose
    @Column(name = "email2")
    @Type(type = "text")
    private String email2_contact;

    @Expose
    @Column(name = "email3")
    @Type(type = "text")
    private String email3_contact;

    @Transient
    private String allEmail;

    @Transient
    private String allPhones;

    @Transient
    private String allAddresses;
    @Expose
    @Column(name = "photo")
    @Type(type = "text")
    private String photo;


    public ContactData setId(int id) {
        this.id = id;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public ContactData withAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getAllAddresses() {
        return allAddresses;
    }

    public ContactData withAllAddresses(String allAddresses) {
        this.allAddresses = allAddresses;
        return this;
    }


    public ContactData withWorkPhone(String work_phone) {
        this.work_phone = work_phone;
        return this;
    }


    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }


    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactData withMiddlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public ContactData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /*public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }*/


    public ContactData withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public ContactData withTitle_contact(String title_contact) {
        this.title_contact = title_contact;
        return this;
    }

    public ContactData withCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withHome_phone(String home_phone) {
        this.home_phone = home_phone;
        return this;
    }

    public ContactData withMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
        return this;
    }


    public ContactData withFax_phone(String fax_phone) {
        this.fax_phone = fax_phone;
        return this;
    }

    public ContactData withEmail(String email_contact) {
        this.email1_contact = email_contact;
        return this;
    }

    public ContactData withEmail1(String email1_contact) {
        this.email2_contact = email1_contact;
        return this;
    }

    public ContactData withEmail2(String email2_contact) {
        this.email3_contact = email2_contact;
        return this;
    }


    public ContactData withAllEmail(String allEmail) {
        this.allEmail = allEmail;
        return this;
    }

    public ContactData withPhoto(String s) {
        this.photo = s;
        return this;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    public File getPhoto() {
        return (new File(photo));

    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle_contact() {
        return title_contact;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getFax_phone() {
        return fax_phone;
    }

    public String getEmail1_contact() {
        return email1_contact;
    }

    public String getEmail2_contact() {
        return email2_contact;
    }

    public String getEmail3_contact() {
        return email3_contact;
    }


    public String getAllEmail() {
        return allEmail;
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    public int getId() {
        return id;
    }


    public String getFio() {

        return getFirstname() + " " + getMiddlename() + " " + getLastname();
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title_contact='" + title_contact + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", home_phone='" + home_phone + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", work_phone='" + work_phone + '\'' +
                ", fax_phone='" + fax_phone + '\'' +
                ", email1_contact='" + email1_contact + '\'' +
                ", email2_contact='" + email2_contact + '\'' +
                ", email3_contact='" + email3_contact + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
    public ContactData inGroup(GroupData group){
        groups.add(group);
        return this;
    }

}

