package testdata;

public class BuilderFactory {
	public static OrderBuilder anOrder() {
		return new OrderBuilder();
	}
	public static OrderLineBuilder anOrderLine() {
		return new OrderLineBuilder();
	}
	public static CustomerBuilder aCustomer() {
		return new CustomerBuilder();
	}
	public static AddressBuilder anAddress() {
		return new AddressBuilder();
	}
	public static PostcodeBuilder aPostcode() {
		return new PostcodeBuilder();
	}
}
