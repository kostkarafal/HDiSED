package pl.kostkamatloch.nfcreader.model.webservice;

import com.google.gson.annotations.SerializedName;



/**
 * Created by Rafal on 06.06.2018.
 */

public class TestNfcTag {
    @SerializedName("id")
        private Long id;
    @SerializedName("name")
        private String name;
    @SerializedName("content")
        private String content;
    @SerializedName("latitude")
        private double latitude;
    @SerializedName("longitude")
        private double longitude;
    @SerializedName("description")
        private String description;

    public TestNfcTag(String name, String content, String description, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //  public TestNfcTag(Long id, St)

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String title) {
            this.name = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
