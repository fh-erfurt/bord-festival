package de.bord.festival.models;



import javax.persistence.*;
import java.util.Date;

@Entity
public class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    public Long getId(){return this.id;}

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt=new Date();
    }
    @PreUpdate
    void onUpdate(){
        this.updatedAt=new Date();
    }

}
