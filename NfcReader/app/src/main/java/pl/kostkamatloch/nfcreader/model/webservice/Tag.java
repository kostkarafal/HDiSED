package pl.kostkamatloch.nfcreader.model.webservice;

import com.google.gson.annotations.SerializedName;



/**
 * Created by Rafal on 06.06.2018.
 */

public class Tag {
    @SerializedName("id")
        private Long id;
        @SerializedName("name")
        private String name;
    @SerializedName("content")
        private String content;
    @SerializedName("description")
        private String description;
    @SerializedName("localization")
        private String localization;


    public Tag(String name, String content, String description, String localization) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.description = description;
        this.localization = localization;
    }

    //  public Tag(Long id, St)

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

        public String getLocalization() {
            return localization;
        }

        public void setLocalization(String localization) {
            this.localization = localization;
        }


    }
