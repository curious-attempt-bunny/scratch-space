package testdata;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import domain.Customer;
import domain.Order;
import domain.OrderLine;


public class OrderBuilder implements Cloneable {
    private Customer customer = new CustomerBuilder().build();
    private List<OrderLine> lines = new ArrayList<OrderLine>();
    private BigDecimal discountRate = BigDecimal.ZERO;
	private String voucher = null;

    public OrderBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder withCustomer(CustomerBuilder customerBuilder) {
        this.customer = customerBuilder.build();
        return this;
    }
    
    public OrderBuilder withOrderLines(List<OrderLine> lines) {
        this.lines = lines;
        return this;
    }
    
	public OrderBuilder withOrderLine(String name, int quantity) {
		lines.add(new OrderLineBuilder().withName(name).withQuantity(quantity).build());
		return this;
	}

    public OrderBuilder withDiscount(BigDecimal discountRate) {
        this.discountRate = discountRate;
        return this;
    }
    
    public OrderBuilder withGiftVoucher(String voucher) {
    	this.voucher = voucher;
    	return this;
    }

    public Order build() {
        Order order = new Order(customer);
        for (OrderLine line : lines) order.addLine(line);
        order.setDiscountRate(discountRate);
        order.setVoucher(voucher);
        return order;
    }
    
    public OrderBuilder but() {
    	try {
			return (OrderBuilder) clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
    }

}
