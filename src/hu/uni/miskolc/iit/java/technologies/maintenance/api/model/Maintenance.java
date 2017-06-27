package hu.uni.miskolc.iit.java.technologies.maintenance.api.model;

import java.util.Date;

public class Maintenance {

	private ProdMachine prodmachine;
	private Price price;
	private Date startDate;
	private Date expirationDate;
	private boolean stopped;

	public Maintenance() {
		super();
	}

	public Maintenance(ProdMachine prodmachine, Price price, Date startDate, Date expirationDate, boolean isStopped) {
		super();
		this.prodmachine = prodmachine;
		this.price = price;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.stopped = isStopped;
	}

	public ProdMachine getProdMachine() {
		return prodmachine;
	}

	public void setProdMachine(ProdMachine prodmachine) {
		this.prodmachine = prodmachine;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean isStopped) {
		this.stopped = isStopped;
	}

	public boolean openMaintenance() {
		return !stopped && expirationDate.after(new Date());
	}

	@Override
	public String toString() {
		return "Maintenance [prodmachine=" + prodmachine + ", price=" + price + ", startDate=" + startDate + ", expirationDate="
				+ expirationDate + ", stopped=" + stopped + "]";
	}

}
