package com.tony.nftLogin.model;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private Double price;
    private Double width;
    private Double height;
    private String placementType;
    private String occupancyGrid;
    private String placementGrid;
    private String blockchainLink;

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPlacementType() {
        return placementType;
    }

    public void setPlacementType(String placementType) {
        this.placementType = placementType;
    }

    public String getOccupancyGrid() {
        return occupancyGrid;
    }

    public void setOccupancyGrid(String occupancyGrid) {
        this.occupancyGrid = occupancyGrid;
    }

    public String getPlacementGrid() {
        return placementGrid;
    }

    public void setPlacementGrid(String placementGrid) {
        this.placementGrid = placementGrid;
    }

    public String getBlockchainLink() {
        return blockchainLink;
    }

    public void setBlockchainLink(String blockchainLink) {
        this.blockchainLink = blockchainLink;
    }
}
