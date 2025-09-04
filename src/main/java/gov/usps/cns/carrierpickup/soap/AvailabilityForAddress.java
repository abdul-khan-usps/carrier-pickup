package gov.usps.cns.carrierpickup.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "clientId",
    "address"
})
@XmlRootElement(name = "availabilityForAddress", namespace = "http://v5.soap.ws.pp.ccc.usps.com/")
public class AvailabilityForAddress {

    @XmlElement(required = true)
    protected String clientId;
    
    @XmlElement(required = true)
    protected Address address;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String value) {
        this.clientId = value;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address value) {
        this.address = value;
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
}
