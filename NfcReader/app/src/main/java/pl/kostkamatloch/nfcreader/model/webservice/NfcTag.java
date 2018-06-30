package pl.kostkamatloch.nfcreader.model.webservice;

import com.google.gson.annotations.SerializedName;




/**
 * Created by Rafal on 07.06.2018.
 */

//NFC tag model to communicate with server
public class NfcTag {

    @SerializedName("id")
    private Long id;
    @SerializedName("idtag")
    private String idTag;
    @SerializedName("tnf")
    private short tnf;
    @SerializedName("payload")
    private String payload;
    @SerializedName("text")
    private String text;
    @SerializedName("uri")
    private String uri;
    @SerializedName("technologies")
    private String technologies;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("description")
    private String description;
    @SerializedName("createdAt")
    private String createdAt;

    public NfcTag(){}

    public NfcTag(String idTag, short tnf, String payload, String text, String uri, String technologies, double latitude, double longitude, String description) {
        this.idTag = idTag;
        this.tnf = tnf;
        this.payload = payload;
        this.text = text;
        this.uri = uri;
        this.technologies = technologies;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
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


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return createdAt;
    }

    public void setDate(String createdAt) {
        this.createdAt = createdAt;
    }
}
