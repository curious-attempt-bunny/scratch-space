package testdata;
import domain.Address;
import domain.Customer;


public class CustomerBuilder {

	private Address address = new AddressBuilder().build();

	public CustomerBuilder withAddress(Address address) {
		this.address = address;
		return this;
	}

	public CustomerBuilder withAddress(AddressBuilder addressBuilder) {
		this.address = addressBuilder.build();
		return this;
	}

	public Customer build() {
		return new Customer(address);
	}

}
