import React, { forwardRef, useEffect, useRef, useState } from 'react';
import { useStompClient } from '../contexts/StomppClientContext';
import { useUser } from '../contexts/UserContext';
import { getProfile, searchUserGroup } from '../services/UserService';
import { saveGroupDetail } from '../services/GroupService';
import ProfileComponent from './ProfileComponent';

const ParticipantsComponent = forwardRef(({setOpenMemberGroup, idUser, idGroup, nameChatRoom }, ref) => {
  const { stompClient } = useStompClient();
  const [listFriend, setListFriend] = useState([]);
  const [countMember, setCountMember] = useState([]);
  const [ profile, setProfile ] = useState(null);


  const { user } = useUser();

  const [openProfile, setOpenProfile] = useState(false);
  const profileRef = useRef();

  useEffect(() => {
    const fetchUserFriend = async () => {
        try {
          const formData = new FormData();
          formData.append('idGroup', idGroup);
          const response = await searchUserGroup(formData);
          setListFriend(response.data);
          setCountMember(response.data.length);
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    };
    fetchUserFriend();
  }, []); 

  const sendNotifiAll = async (content) => {
    const chatMessage = {
      sender: user.id,
      received: idGroup,
      content: content,
      code: idGroup,
      sendTime: new Date(),
      isEnable: 1,
      type: 4,
      messageEdt: null,
      senderName: user.fullname
    };
    stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
  };

  const sendNotifiOut = async (content, idFriend, idGroup) => {
    const notification = {
      sender: user.id,
      content: content,
      received: idFriend,
      sendTime: new Date(),
      idChatRoom: idGroup,
      type: 1,
      isRead : 0,
      isEnable: 1
    };
    stompClient.send("/app/handle", {}, JSON.stringify(notification));
  };

  const removeMember = async (friend) => {
    try {
      const groupDetail = {"idGroup" : idGroup, "idUser": friend.id, "isEnable":0};
      await saveGroupDetail(groupDetail);
      sendNotifiAll(user.fullname + " has removed " + friend.fullname + " from this conversation");
      sendNotifiOut(user.fullname + " has removed you from this conversation", friend.id, idGroup)
      updateUIRemove(friend.id);
      setCountMember(countMember - 1);
    } catch (error) {
        console.error('Error fetching messages:', error);
    }
  };

  function updateUIRemove(idFriend) {
    const listMember = document.getElementById("listUserSuggest");
    const member = listMember.querySelector(`#${idFriend}`);
    listMember.removeChild(member) 
  }

  async function displayProfile(id) {
    const formData = new FormData();
    formData.append('idUser', user.id);
    formData.append('idFriend', id);
    const response = await getProfile(formData);
    setProfile(response.data);
    setOpenProfile(true);
  }

  return (
    <>
      <div className='addToGroup-bg' ref={ref}> 
          <div className='addToGroup-container'>
              <div className="titleCloseBtn">
                  <span>{nameChatRoom}</span>
                  <button onClick={() => {setOpenMemberGroup(false) }}> &times; </button>
              </div>
              <div className='body'>
                  <div className='text-group-member'>
                    <p>{countMember} Participants</p>
                  </div>
                  <div id='listUserSuggest'  className='list-user-member-group'>
                        {listFriend.map((friend, index) => 
                            <div className= {`user-item-member  ${friend.id === idUser ? 'append-to-bottom' : ''}`} key={index}  id={friend.id} onClick={(e) => {displayProfile(friend.id)}}>
                              <img src='./src/assets/img/user_icon.png' alt="user icon" />
                              <div className="friend-name-container">
                                <div className="friend-name">{friend.fullname}</div>
                              </div>
                              <div className={`input-container  ${friend.id === idUser ? 'hidden' : ''}`} onClick={(e) => { e.stopPropagation(); removeMember(friend)}} >
                                <button className='btn-remove'>Remove</button>
                              </div>
                            </div>
                        )}
                  </div>
              </div>
          </div>
      </div>
      {openProfile && <ProfileComponent setOpenProfile = {setOpenProfile} setOpenMemberGroup = {setOpenMemberGroup} profile = {profile} ref={profileRef}/>}
    </>
  );
});

export default ParticipantsComponent;