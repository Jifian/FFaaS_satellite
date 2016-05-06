package Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yapca on 25-4-2016.
 */
@Entity
@Table(name = "datasource")
public class Datasource {

    @Id
    @GeneratedValue
    @Column(name = "d_id", nullable = false, unique = true)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "datasource")
    private Set<Snapshots> snapshots = new HashSet<Snapshots>(
            0);

    private Datasource() {}
    public Datasource(String name)
    {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Snapshots> getSnapshots() {
        return snapshots;
    }

    public Datasource addSnapshot(Snapshots snapshot)
    {
        snapshots.add(snapshot);
        return this;
    }
}
