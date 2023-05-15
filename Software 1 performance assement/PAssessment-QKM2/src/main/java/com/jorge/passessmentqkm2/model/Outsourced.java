package com.jorge.passessmentqkm2.model;

/**
 * The Outsourced class represents a type of Part that is obtained from an external company.
 * It extends the Part class and includes an additional field for the company's name.
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class Outsourced extends Part {
    /**
     * The name of the company that provides this part.
     */
    private String companyName;
    /**
     * Creates a new Outsourced part with the given parameters.
     *
     * @param id the ID of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the stock level of the part
     * @param min the minimum allowable stock level of the part
     * @param max the maximum allowable stock level of the part
     * @param companyName the name of the company that provides the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Sets the name of the company that provides this part.
     *
     * @param companyName the name of the company
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    /**
     * Returns the name of the company that provides this part.
     *
     * @return the name of the company
     */
    public String getCompanyName(){
        return companyName;
    }

    @Override
    public String toString() {
        return "Outsourced Part ID: " + this.getId() +
                ", Name: " + this.getName() +
                ", Price: " + this.getPrice() +
                ", Stock: " + this.getStock() +
                ", Min: " + this.getMin() +
                ", Max: " + this.getMax() +
                ", Company Name: " + this.getCompanyName();
    }
}
