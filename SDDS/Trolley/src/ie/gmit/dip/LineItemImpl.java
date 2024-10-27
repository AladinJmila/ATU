package ie.gmit.dip;

public class LineItemImpl implements LineItem {
	private String number;
	private String name;
	private int qty;
	private float price;

	public LineItemImpl() {
		super();
	}

	public LineItemImpl(String number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	public LineItemImpl(String number, String name, int qty, float price) {
		super();
		this.number = number;
		this.name = name;
		this.qty = qty;
		this.price = price;
	}

	public void setItemNumber(String number) {
		this.number = number;
	}

	public String getItemNumber() {
		return this.number;
	}

	public void setItemName(String name) {
		this.name = name;
	}

	public String getItemName() {
		return this.name;
	}

	public void setItemQuantity(int qty) {
		this.qty = qty;
	}

	public int getItemQuantity() {
		return this.qty;
	}

	public void setItemPrice(float price) {
		this.price = price;
	}

	public float getItemPrice() {
		return this.price;
	}

}
