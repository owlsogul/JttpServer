package me.blog.owlsogul.jttp.server.observer;

import java.util.LinkedList;

public class ObserverList<T extends Observer> {

	private LinkedList<Observer> observers;
	public ObserverList(){
		this.observers = new LinkedList<>();
	}
	
	public void add(Observer observer) {
		observers.add(observer);
	}
	
	public void remove(Observer observer) {
		observers.remove(observer);
	}
	
	public void observe(Object data) {
		for (Observer observer : observers) {
			observer.observe(data);
		}
	}
	
	public void removeAll() {
		observers.clear();
	}
}
