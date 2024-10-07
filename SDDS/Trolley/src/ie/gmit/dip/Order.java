package ie.gmit.dip;

import java.util.*;

public interface Order {
	public String getOrderNumber();
	public Date getOrderDate();
	public boolean addItem(LineItem item);
	public boolean removeItem(LineItem item);
	public LineItem[] items();
	public float getTotal();
}
