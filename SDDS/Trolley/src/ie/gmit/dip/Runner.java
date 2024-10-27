package ie.gmit.dip;

public class Runner {
	public static void main(String[] args) {
		Order order = new OrderImpl("QB-1234-AA");
		
		LineItem item1 = new LineItemImpl("AA1", "Salmon Fly Rod", 3, 899.99f);
		LineItem item2 = new LineItemImpl("AA2", "Salmon Fly Reel", 3, 200.00f);
		LineItem item3 = new LineItemImpl("AA3", "Salmon Fly Line", 21, 78.99f);
		LineItem item4 = new LineItemImpl("AA4", "Salmon Fly Sink Tip", 43, 10.00f);
		
		order.addItem(item1);
		order.addItem(item2);
		order.addItem(item3);
		order.addItem(item4);
		
		LineItem[] items = order.items();
		for (int i = 0; i < items.length; i++) {
			System.out.println("[" + (i + 1) + "]\t" + items[i].getItemName());
		}
		
		System.out.println("Total: " + order.getTotal());
		
	}
}
