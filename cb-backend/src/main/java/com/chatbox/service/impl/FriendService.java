package com.chatbox.service.impl;

import com.chatbox.dto.FriendDto;
import com.chatbox.dto.component.FriendRequest;
import com.chatbox.entity.Friend;
import com.chatbox.mapper.FriendMapper;
import com.chatbox.repository.FriendRepository;
import com.chatbox.service.IFriendService;
import com.chatbox.util.FriendUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService implements IFriendService {

    private static final Logger log = LoggerFactory.getLogger(FriendService.class);
    private FriendRepository repository;

    @Override
    public ResponseEntity<List<FriendDto>> findFriendByIdUser(String id) {
        List<Friend> friends = repository.findFriendByIdUser(id);
        if (friends.isEmpty()) {
            log.info("Can't find any friend");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<FriendDto> friendDtos = friends.stream().map(FriendMapper::mapToFriendDto).toList();
        return ResponseEntity.ok(friendDtos);
    }

    @Override
    public List<FriendDto> save(FriendRequest friendRequest) {
        try {
            log.info("Begin save with friendRequest = {}", friendRequest);

            int isFriendOne = getFriendStatus(friendRequest.getType(), true);
            int isFriendTwo = getFriendStatus(friendRequest.getType(), false);

            Friend friendOne = new Friend(friendRequest.getIdUser(), friendRequest.getIdFriend(), friendRequest.getCode(), isFriendOne);
            Friend friendTwo = new Friend(friendRequest.getIdFriend(), friendRequest.getIdUser(), friendRequest.getCode(), isFriendTwo);

            List<FriendDto> listFriendDto = Arrays.asList(
                    saveAndMap(friendOne),
                    saveAndMap(friendTwo)
            );

            log.info("End save SUCCESS with count save = {}", listFriendDto.size());
            return listFriendDto;
        } catch (Exception e) {
            log.error("Exception in save", e);
            return Collections.emptyList();
        }
    }

    private int getFriendStatus(int requestType, boolean isFirstFriend) {
        if (requestType == 1) {
            return FriendUtil.AGREE_REQUEST;
        } else if (requestType == 2) {
            return isFirstFriend ? FriendUtil.SEND_REQUEST : FriendUtil.RECEIVE_REQUEST;
        } else {
            return FriendUtil.DISAGREE_REQUEST;
        }
    }

    private FriendDto saveAndMap(Friend friend) {
        Friend savedFriend = repository.save(friend);
        return FriendMapper.mapToFriendDto(savedFriend);
    }

//    public List<FriendDto> save(FriendRequest friendRequest) {
//        try {
//            log.info("Begin save with friendRequest = {}", friendRequest);
//            Friend friendOne;
//            Friend friendTwo;
//            int isFriendOne;
//            int isFriendTwo;
//            if (friendRequest.getType() == 1) {
//                isFriendOne = FriendUtil.AGREE_REQUEST;
//                isFriendTwo = FriendUtil.AGREE_REQUEST;
//            } else if (friendRequest.getType() == 2) {
//                isFriendOne =  FriendUtil.SEND_REQUEST;
//                isFriendTwo = FriendUtil.RECEIVE_REQUEST;
//            } else {
//                isFriendOne =  FriendUtil.DISAGREE_REQUEST;
//                isFriendTwo = FriendUtil.DISAGREE_REQUEST;
//            }
//            friendOne = new Friend(friendRequest.getIdUser(), friendRequest.getIdFriend(), friendRequest.getCode(), isFriendOne);
//            friendTwo = new Friend(friendRequest.getIdFriend(), friendRequest.getIdUser(), friendRequest.getCode(), isFriendTwo);
//            Friend friendSaved1 = repository.save(friendOne);
//            Friend friendSaved2 = repository.save(friendTwo);
//            List<FriendDto> listFriendDto = new ArrayList<>();
//            listFriendDto.add(FriendMapper.mapToFriendDto(friendSaved1));
//            listFriendDto.add(FriendMapper.mapToFriendDto(friendSaved2));
//            log.info("End save SUCCESS with count save = {}", listFriendDto.size());
//            return listFriendDto;
//        } catch (Exception e) {
//            log.info("Exception in save");
//            log.error("Exception in save", e);
//            return new ArrayList<>();
//        }
//    }

}
