package banks.Models;

import banks.Tools.*;

/**
 * The Address class represents a physical address with various components including country, region, city, street, house number, and flat number
 */
public class Address {
    private static final int ADDRESS_COMPONENT_COUNT = 6;
    private final String country;
    private final String region;
    private final String city;
    private final String street;
    private final int house;
    private final int flat;

    /**
     * Creates a new Address object from the specified full address string
     * @param fullAddress the full address string in the format "country, region, city, street, house, flat"
     * @throws AddressException if the fullAddress is null or empty, or if it does not contain all the required components
     */
    public Address(String fullAddress) throws AddressException {
        if (fullAddress.isEmpty()) {
            throw AddressException.addressIsNullException();
        }

        var address = fullAddress.split(", ");
        if (address.length < ADDRESS_COMPONENT_COUNT) {
            throw AddressException.addressIsNotFullException();
        }

        country = address[0];
        region = address[1];
        city = address[2];
        street = address[3];
        house = Integer.parseInt(address[4]);
        flat = Integer.parseInt(address[5]);
    }

    /**
     * Returns the country component of the address
     * @return the country component of the address
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the region component of the address
     * @return the region component of the address
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the city component of the address
     * @return the city component of the address
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the street component of the address
     * @return the street component of the address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns the house number component of the address
     * @return the house number component of the address
     */
    public int getHouse() {
        return house;
    }

    /**
     * Returns the flat number component of the address
     * @return the flat number component of the address
     */
    public int getFlat() {
        return flat;
    }

    /**
     * Returns a string representation of the address in the format "country, region, city, street, house, flat".
     * @return a string representation of the address
     */
    public String stringAddress() {
        return country + ", " + region + ", " + city + ", " + street + ", " + house + ", " + flat;
    }
}
