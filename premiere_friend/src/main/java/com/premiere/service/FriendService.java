package com.premiere.service;

import com.premiere.client.UserClient;
import com.premiere.dao.FriendDao;
import com.premiere.dao.NoFriendDao;
import com.premiere.mapper.FriendMapper;
import com.premiere.pojo.Friend;
import com.premiere.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    private final FriendMapper friendMapper;
    private final FriendDao friendDao;
    private final NoFriendDao noFriendDao;
    private final UserClient userClient;

    @Autowired
    public FriendService(FriendMapper friendMapper, FriendDao friendDao, NoFriendDao noFriendDao, UserClient userClient) {
        this.friendMapper = friendMapper;
        this.friendDao = friendDao;
        this.noFriendDao = noFriendDao;
        this.userClient = userClient;
    }


    public Integer addFriend(String userId, String friendid) {
        if (friendDao.selectCount(userId, friendid) > 0) {
            return 0;
        }
        Friend friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        if (friendDao.selectCount(friendid, userId) > 0) {
            friendDao.updateLike(userId, friendid, "1");
            friendDao.updateLike(friendid, userId, "1");
        }
        // 增加粉丝
        userClient.incFollowCount(userId, 1);
        userClient.incFansCount(friendid, 1);
        return 1;
    }

    public void deleteFriend(String userId, String friendid) {
        if (friendDao.selectCount(friendid, userId) > 0) {
            friendDao.updateLike(friendid, userId, "0");
        }
        if (friendDao.selectCount(userId, friendid) > 0) {
            // 减少粉丝
            userClient.incFollowCount(userId, -1);
            userClient.incFansCount(friendid, -1);
            friendDao.deleteFriend(userId, friendid);
        }
    }

    public void addNoFriend(String userId, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        deleteFriend(userId,friendid);
    }
}
