package org.ipccenter.visitadvisor.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "group_table")
@NamedQueries({
    @NamedQuery(name = "Group.getAll", query = "SELECT g from Group g"),
    @NamedQuery(name = "Group.getByName", query = "SELECT g from Group g where g.name = :name")
})
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + '}';
    }

}
