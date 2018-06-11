package pl.kostkamatloch.nfcreader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "test7tag")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    private String idtag;


    private short tnf;
     
    private String payload;
   
    private String text;
    
    private String uri;
   
    private String technologies;
    
    private double latitude;
    
   
    private double longitude;
    
  //  @NotBlank
    private String description;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

 //   @Column(nullable = false)
 //   @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdtag() {
        return idtag;
    }

    public void setIdTag(String idtag) {
        this.idtag = idtag;
    }

    public short getTnf() {
        return tnf;
    }

    public void setTnf(short tnf) {
        this.tnf = tnf;
    }
    
     public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
     public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
     public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
     public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
        public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

}