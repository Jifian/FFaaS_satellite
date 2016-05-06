package Entities;

import javax.persistence.*;

/**
 * Created by yapca on 25-4-2016.
 */
@Entity
@Table(name = "measurement_value")
public class MeasurementValue {
    @Id
    @GeneratedValue
    @Column(name = "mv_id", nullable = false, unique = true)
    private int id;

    @Column(name = "latitude", nullable = false)
    private float latitude;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "value", nullable = false)
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_id", nullable = false)
    private Measurement measurement;

    private MeasurementValue() {}

    public int getId() {
        return id;
    }

    public MeasurementValue(float value, float longitude, float latitude, Measurement measurement)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.value = value;
        this.measurement = measurement;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getValue() {
        return value;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
