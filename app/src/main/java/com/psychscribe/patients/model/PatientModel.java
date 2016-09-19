package com.psychscribe.patients.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 21/7/16.
 */



public class PatientModel {

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
    private List<PatientData> data = new ArrayList<PatientData>();

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
    public List<PatientData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<PatientData> data) {
        this.data = data;
    }

    public class PatientData implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("middle_initial")
        @Expose
        private String middleInitial;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zip_code")
        @Expose
        private String zipCode;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone_1")
        @Expose
        private String phone1;
        @SerializedName("phone_2")
        @Expose
        private String phone2;
        @SerializedName("is_active")
        @Expose
        private String isActive;

        public boolean isStickyVisible;

        public boolean isStickyVisible() {
            return isStickyVisible;
        }

        public void setStickyVisible(boolean stickyVisible) {
            isStickyVisible = stickyVisible;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The userId
         */
        public String getUserId() {
            return userId;
        }

        /**
         *
         * @param userId
         * The user_id
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         *
         * @return
         * The firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         *
         * @param firstName
         * The first_name
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         *
         * @return
         * The lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         *
         * @param lastName
         * The last_name
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         *
         * @return
         * The middleInitial
         */
        public String getMiddleInitial() {
            return middleInitial;
        }

        /**
         *
         * @param middleInitial
         * The middle_initial
         */
        public void setMiddleInitial(String middleInitial) {
            this.middleInitial = middleInitial;
        }

        /**
         *
         * @return
         * The dob
         */
        public String getDob() {
            return dob;
        }

        /**
         *
         * @param dob
         * The dob
         */
        public void setDob(String dob) {
            this.dob = dob;
        }

        /**
         *
         * @return
         * The address
         */
        public String getAddress() {
            return address;
        }

        /**
         *
         * @param address
         * The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         *
         * @return
         * The city
         */
        public String getCity() {
            return city;
        }

        /**
         *
         * @param city
         * The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         *
         * @return
         * The state
         */
        public String getState() {
            return state;
        }

        /**
         *
         * @param state
         * The state
         */
        public void setState(String state) {
            this.state = state;
        }

        /**
         *
         * @return
         * The zipCode
         */
        public String getZipCode() {
            return zipCode;
        }

        /**
         *
         * @param zipCode
         * The zip_code
         */
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        /**
         *
         * @return
         * The email
         */
        public String getEmail() {
            return email;
        }

        /**
         *
         * @param email
         * The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         *
         * @return
         * The phone1
         */
        public String getPhone1() {
            return phone1;
        }

        /**
         *
         * @param phone1
         * The phone_1
         */
        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        /**
         *
         * @return
         * The phone2
         */
        public String getPhone2() {
            return phone2;
        }

        /**
         *
         * @param phone2
         * The phone_2
         */
        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        /**
         *
         * @return
         * The isActive
         */
        public String getIsActive() {
            return isActive;
        }

        /**
         *
         * @param isActive
         * The is_active
         */
        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

    }

    public class Errors {


    }


}
