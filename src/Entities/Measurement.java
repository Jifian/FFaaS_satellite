package Entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yapca on 25-4-2016.
 */
@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue
    @Column(name = "m_id", nullable = false, unique = true)
    private int id;

    @Column(name = "measurementname")
    private String measurement_name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "datetime", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshots snapshot;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "measurement")
    private Set<MeasurementValue> measurement_values = new HashSet<MeasurementValue>(
            0);

    private Measurement() {}
    public int getId() {
        return id;
    }

    public Measurement(String measurement_name, String unit, Date date, Snapshots snapshot)
    {
        this.measurement_name = measurement_name;
        this.unit = unit;
        this.date = date;
        this.snapshot = snapshot;
    }

    public String getMeasurement_name() {
        return measurement_name;
    }

    public String getUnit() {
        return unit;
    }

    public Date getDatetime() {
        return date;
    }

    public Snapshots getSnapshots() {
        return snapshot;
    }

    public Set<MeasurementValue> getMeasurement_values() {
        return measurement_values;
    }
}
