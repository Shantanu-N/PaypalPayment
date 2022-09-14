package com.capg.Payment.Model;




public class PaymentOrder {
	
	private double price;
	private String currency;
	private String method;
	
	
	
	@Override
	public String toString() {
		return "PaymentOrder [price=" + price + ", currency=" + currency + ", method=" + method + "]";
	}
	
	public PaymentOrder() {
		
	}



	public PaymentOrder(double price, String currency, String method) {
		super();
		this.price = price;
		this.currency = currency;
		this.method = method;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
	
	
	

}
