package de.bord.festival.models;


import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = new Date();
    }

}
