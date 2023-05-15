package com.jorge.passessmentqkm2.model;

/**
 * The InHouse class represents a part that is produced in-house.
 * It extends the Part class and adds a field, machineId.
 *
 * @author Jorge Basilio
 * @version  05/14/23
 *
 * */
public class InHouse extends Part {
    /**
     * The ID of the machine that produces this part.
     */
    private int machineId;

    /**
     * Constructs a new InHouse part with the given parameters.
     *
     * @param id the part ID
     * @param name the part name
     * @param price the price of the part
     * @param stock the stock count of the part
     * @param min the minimum allowed stock count
     * @param max the maximum allowed stock count
     * @param machineId the ID of the machine that produces this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the ID of the machine that produces this part.
     *
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Returns the ID of the machine that produces this part.
     *
     * @return the machine ID
     */
    public int getMachineId(){
        return machineId;
    }

    @Override
    public String toString() {
        return "InHouse Part ID: " + this.getId() +
                ", Name: " + this.getName() +
                ", Price: " + this.getPrice() +
                ", Stock: " + this.getStock() +
                ", Min: " + this.getMin() +
                ", Max: " + this.getMax() +
                ", Machine ID: " + this.getMachineId();
    }
}
