package com.tony.nftLogin.repository;
import com.tony.nftLogin.model.SceneItemRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SceneItemRelationRepository extends JpaRepository<SceneItemRelation, Long> {

    @Query("SELECT sir FROM SceneItemRelation sir WHERE sir.scene.sceneId IN " +
            "(SELECT s.sceneId FROM Scene s WHERE s.user.userId = :userId AND s.sceneType = 'main')")
    List<SceneItemRelation> findAllItemsInMainSceneByUserId(@Param("userId") Long userId);
}
