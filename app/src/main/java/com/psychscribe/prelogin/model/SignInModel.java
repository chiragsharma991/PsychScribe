
package com.psychscribe.prelogin.model;

import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;


public class SignInModel {

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
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The errors
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     * @param errors The errors
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     * @return The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("passcode")
        @Expose
        private String passcode;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("last_accessed")
        @Expose
        private String lastAccessed;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("plan_start_date")
        @Expose
        private String planStartDate;
        @SerializedName("plan_end_date")
        @Expose
        private String planEndDate;
        @SerializedName("is_paid")
        @Expose
        private Integer isPaid;
        @SerializedName("token")
        @Expose
        private String token;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The passcode
         */
        public String getPasscode() {
            return passcode;
        }

        /**
         * @param passcode The passcode
         */
        public void setPasscode(String passcode) {
            this.passcode = passcode;
        }

        /**
         * @return The deviceType
         */
        public String getDeviceType() {
            return deviceType;
        }

        /**
         * @param deviceType The device_type
         */
        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        /**
         * @return The deviceToken
         */
        public String getDeviceToken() {
            return deviceToken;
        }

        /**
         * @param deviceToken The device_token
         */
        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        /**
         * @return The lastAccessed
         */
        public String getLastAccessed() {
            return lastAccessed;
        }

        /**
         * @param lastAccessed The last_accessed
         */
        public void setLastAccessed(String lastAccessed) {
            this.lastAccessed = lastAccessed;
        }

        /**
         * @return The isActive
         */
        public String getIsActive() {
            return isActive;
        }

        /**
         * @param isActive The is_active
         */
        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        /**
         * @return The createdAt
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * @param createdAt The created_at
         */
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        /**
         * @return The updatedAt
         */
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         * @param updatedAt The updated_at
         */
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        /**
         * @return The planStartDate
         */
        public String getPlanStartDate() {
            return planStartDate;
        }

        /**
         * @param planStartDate The plan_start_date
         */
        public void setPlanStartDate(String planStartDate) {
            this.planStartDate = planStartDate;
        }

        /**
         * @return The planEndDate
         */
        public String getPlanEndDate() {
            return planEndDate;
        }

        /**
         * @param planEndDate The plan_end_date
         */
        public void setPlanEndDate(String planEndDate) {
            this.planEndDate = planEndDate;
        }

        /**
         * @return The isPaid
         */
        public Integer getIsPaid() {
            return isPaid;
        }

        /**
         * @param isPaid The is_paid
         */
        public void setIsPaid(Integer isPaid) {
            this.isPaid = isPaid;
        }

        /**
         * @return The token
         */
        public String getToken() {
            return token;
        }

        /**
         * @param token The token
         */
        public void setToken(String token) {
            this.token = token;
        }

    }

    class Errors {

    }

}