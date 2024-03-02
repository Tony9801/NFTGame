package com.tony.nftLogin.model;

import jakarta.persistence.*;

@Entity
public class SceneItemRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sceneItemRelationId;

    @ManyToOne
    @JoinColumn(name = "sceneId", nullable = false)
    private Scene scene;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer positionX;

    @Column(nullable = false)
    private Integer positionY;

    @OneToOne
    @JoinColumn(name = "piledItemId")
    private SceneItemRelation piledItem;

    // Getters and Setters
    public Long getSceneItemId() {
        return sceneItemRelationId;
    }

    public void setSceneItemId(Long sceneItemId) {
        this.sceneItemRelationId = sceneItemId;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public SceneItemRelation getPiledItem(){
        return this.piledItem;
    }
}
