package com.food.ordering.items;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequiredItems {
	
	private int id;
	private String name;
	private double price;
	
	public RequiredItems() {
		super();
	}
	public RequiredItems(int id,String name,double price)
	{
		this.id=id;
		this.name=name;
		this.price=price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	/*
	@Override
	public String toString() {
		return "RequiredItems [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
*/
}
