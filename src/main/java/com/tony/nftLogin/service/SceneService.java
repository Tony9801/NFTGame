package com.tony.nftLogin.service;

import com.tony.nftLogin.Message;
import com.tony.nftLogin.model.Inventory;
import com.tony.nftLogin.model.Item;
import com.tony.nftLogin.model.SceneItemRelation;
import com.tony.nftLogin.repository.SceneItemRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneService {
    @Autowired
    SceneItemRelationRepository sceneItemRelationRepository;

    public Message.GetMainSceneItemsResponse getMainSceneItems(Long userId){
        List<SceneItemRelation> relations = sceneItemRelationRepository.findAllItemsInMainSceneByUserId(userId);
        Message.GetMainSceneItemsResponse.Builder responseBuilder = Message.GetMainSceneItemsResponse.newBuilder();
        responseBuilder.setUserId(userId);

        relations.forEach(relation -> {
            Item item = relation.getItem();
            Message.SceneItem sceneItem = Message.SceneItem.newBuilder()
                    .setItemId(item.getItemId())
                    .setPositionX(relation.getPositionX())
                    .setPositionY(relation.getPositionY())
                    .setPileItemId(relation.getPiledItem().getItemId())
                    .build();
            responseBuilder.addItem(sceneItem);
        });

        return responseBuilder.build();
    }
}
