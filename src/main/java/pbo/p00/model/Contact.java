package pbo.p00.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "phone", nullable = false, length = 255)
    private String phone;

    @ManyToMany(targetEntity = Group.class, cascade = CascadeType.ALL)
    @JoinTable(
        name = "contact_group",
        joinColumns = @JoinColumn(
            name = "contact_email",
            referencedColumnName = "email"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
        )
    )
    private Set<Group> groups;

    public Contact() {
        // empty
    }

    public Contact(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public Contact(String email, String name, String phone, Set<Group> groups) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.groups = groups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return email + "|" + name + "|" + phone;
    }
}
