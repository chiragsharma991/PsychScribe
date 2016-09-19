package com.psychscribe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StateModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("data")
    @Expose
    private List<StateData> data = new ArrayList<StateData>();
    /**
     *
     * @return
     * The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The errors
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     *
     * @return
     * The data
     */
    public List<StateData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<StateData> data) {
        this.data = data;
    }

    public class Errors {

    }

    public class StateData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("name")
        @Expose
        private String name;

        /**
         *
         * @return
         * The id
         */
        public Integer getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The countryId
         */
        public String getCountryId() {
            return countryId;
        }

        /**
         *
         * @param countryId
         * The country_id
         */
        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

    }

}
