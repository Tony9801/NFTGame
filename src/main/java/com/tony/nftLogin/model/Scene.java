package com.tony.nftLogin.model;
import jakarta.persistence.*;


@Entity
public class Scene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sceneId;

    @Column(nullable = false)
    private String sceneName;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String sceneType;

    private String placementGrid;

    // Getters and Setters
    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getPlacementGrid() {
        return placementGrid;
    }

    public void setPlacementGrid(String placementGrid) {
        this.placementGrid = placementGrid;
    }
}
