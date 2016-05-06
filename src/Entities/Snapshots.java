package Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yapca on 25-4-2016.
 */
@Entity
@Table(name = "snapshots")
public class Snapshots {
    @Id
    @GeneratedValue
    @Column(name = "s_id", nullable = false, unique = true)
    private int id;

    @Column(name = "level")
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datasource_id", nullable = false)
    private Datasource datasource;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "snapshot")
    private Set<Measurement> measurements = new HashSet<Measurement>(
            0);

    private Snapshots() {}

    public Snapshots(String level, Datasource datasource)
    {
        this.level = level;
        this.datasource = datasource;
    }

    public int getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public Set<Measurement> getMeasurements() {
        return measurements;
    }
}
