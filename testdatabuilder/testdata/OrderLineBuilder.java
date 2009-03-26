package testdata;
import domain.OrderLine;


public class OrderLineBuilder {

	private String name = "Toothbrush";
	private int quantity = 1;

	public OrderLineBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public OrderLineBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public OrderLine build() {
		return new OrderLine(name, quantity);
	}

}
