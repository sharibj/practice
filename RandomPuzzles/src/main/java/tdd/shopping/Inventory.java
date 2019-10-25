package tdd.shopping;

public enum Inventory {
	DoveSoap(10.0), AxeDeo(15.0), Colgate(10);
	private double price;

	Inventory(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}
}
