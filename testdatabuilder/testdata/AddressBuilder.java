package testdata;
import domain.Address;
import domain.Postcode;


public class AddressBuilder {

	private String street = "10a Somewhere Street";
	private String city = "London";
	private Postcode postcode = new PostcodeBuilder().build();

	public AddressBuilder withNoPostcode() {
		this.postcode = null;
		return null;
	}

	public Address build() {
		return new Address(street, city, postcode);
	}

	public AddressBuilder withStreet(String street) {
		this.street = street;
		return this;
	}

	public AddressBuilder withCity(String city) {
		this.city = city;
		return this;
	}

	public AddressBuilder withPostCode(String district, String sector) {
		this.postcode = new PostcodeBuilder().withDistrict(district).withSector(sector).build();
		return this;
	}

}
