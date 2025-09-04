package gov.usps.cns.carrierpickup.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addressResult"
})
@XmlRootElement(name = "availabilityForAddressResponse", namespace = "http://v5.soap.ws.pp.ccc.usps.com/")
public class AvailabilityForAddressResponse {

    @XmlElement(required = true)
    protected AddressResult addressResult;

    public AddressResult getAddressResult() {
        return addressResult;
    }

    public void setAddressResult(AddressResult value) {
        this.addressResult = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "address",
        "dogStatus",
        "pickupTimes",
        "result"
    })
    public static class AddressResult {

        @XmlElement(required = true)
        protected Address address;
        protected String dogStatus;
        @XmlElement(required = true)
        protected PickupTimes pickupTimes;
        @XmlElement(required = true)
        protected Result result;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address value) {
            this.address = value;
        }

        public String getDogStatus() {
            return dogStatus;
        }

        public void setDogStatus(String value) {
            this.dogStatus = value;
        }

        public PickupTimes getPickupTimes() {
            return pickupTimes;
        }

        public void setPickupTimes(PickupTimes value) {
            this.pickupTimes = value;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result value) {
            this.result = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "addressLineOne",
            "addressLineTwo",
            "carrierRoute",
            "city",
            "deliveryPoint",
            "state",
            "urbanizationCode",
            "zip4",
            "zip5"
        })
        public static class Address {

            @XmlElement(required = true)
            protected String addressLineOne;
            protected String addressLineTwo;
            protected String carrierRoute;
            @XmlElement(required = true)
            protected String city;
            protected String deliveryPoint;
            @XmlElement(required = true)
            protected String state;
            protected String urbanizationCode;
            protected String zip4;
            @XmlElement(required = true)
            protected String zip5;

            public String getAddressLineOne() {
                return addressLineOne;
            }

            public void setAddressLineOne(String value) {
                this.addressLineOne = value;
            }

            public String getAddressLineTwo() {
                return addressLineTwo;
            }

            public void setAddressLineTwo(String value) {
                this.addressLineTwo = value;
            }

            public String getCarrierRoute() {
                return carrierRoute;
            }

            public void setCarrierRoute(String value) {
                this.carrierRoute = value;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String value) {
                this.city = value;
            }

            public String getDeliveryPoint() {
                return deliveryPoint;
            }

            public void setDeliveryPoint(String value) {
                this.deliveryPoint = value;
            }

            public String getState() {
                return state;
            }

            public void setState(String value) {
                this.state = value;
            }

            public String getUrbanizationCode() {
                return urbanizationCode;
            }

            public void setUrbanizationCode(String value) {
                this.urbanizationCode = value;
            }

            public String getZip4() {
                return zip4;
            }

            public void setZip4(String value) {
                this.zip4 = value;
            }

            public String getZip5() {
                return zip5;
            }

            public void setZip5(String value) {
                this.zip5 = value;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "endPickupTime",
            "estimatedPickupWindow",
            "startPickupTime"
        })
        public static class PickupTimes {

            protected String endPickupTime;
            protected String estimatedPickupWindow;
            protected String startPickupTime;

            public String getEndPickupTime() {
                return endPickupTime;
            }

            public void setEndPickupTime(String value) {
                this.endPickupTime = value;
            }

            public String getEstimatedPickupWindow() {
                return estimatedPickupWindow;
            }

            public void setEstimatedPickupWindow(String value) {
                this.estimatedPickupWindow = value;
            }

            public String getStartPickupTime() {
                return startPickupTime;
            }

            public void setStartPickupTime(String value) {
                this.startPickupTime = value;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "message",
            "result",
            "resultCode"
        })
        public static class Result {

            @XmlElement(required = true)
            protected String message;
            protected boolean result;
            @XmlElement(required = true)
            protected String resultCode;

            public String getMessage() {
                return message;
            }

            public void setMessage(String value) {
                this.message = value;
            }

            public boolean isResult() {
                return result;
            }

            public void setResult(boolean value) {
                this.result = value;
            }

            public String getResultCode() {
                return resultCode;
            }

            public void setResultCode(String value) {
                this.resultCode = value;
            }
        }
    }
}
