import { forwardRef, useEffect, useState } from "react";
import { useStompClient } from "../contexts/StomppClientContext";
import { useUser } from "../contexts/UserContext";
import { addFriendTest } from "../services/FriendService";
import { codeBetweenTwoUser, updateUISearch } from "../logics/eventHandlers";
import { useChatRoom } from "../contexts/ChatRoomContext";
import { useMessageRequest } from "../contexts/MessageRequestContext";

const ProfileComponent = forwardRef(({setOpenProfile, setOpenMemberGroup, profile}, ref) => {
    const { stompClient } = useStompClient();
    const { user } = useUser();
    const { setChatRoom } = useChatRoom();
    const { setMessageRequest } = useMessageRequest();
    var isFriend = 0;

    const sendNotifiAdd = async (content, idFriend) => {
        const notification = {
          sender: user.id,
          content: content,
          received: idFriend,
          sendTime: new Date(),
          idChatRoom: user.id,
          type: 2,
          isRead : 0,
          isEnable: 1
        };
        stompClient.send("/app/handle", {}, JSON.stringify(notification));
    }

    function clickAddContact(idFriend) {
        const code = codeBetweenTwoUser(user.id, idFriend);
        isFriend = 2;
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: code, type: 2}
        addFriendTest(friendRequest);
        sendNotifiAdd(user.fullname + ' sent you a friend request', idFriend);
        const btnAddContact = document.getElementById('btn-add-contact');
        btnAddContact.classList.add('hidden');
        const spanAdd =  document.getElementById('added-to-contacts');
        spanAdd.classList.remove('hidden');
    }

    const sendNotifiAgreed = async (content, idFriend) => {
        const notification = {
          sender: user.id,
          content: content,
          received: idFriend,
          sendTime: new Date(),
          idChatRoom: user.id,
          type: 2,
          isRead : 0,
          isEnable: 1,
        };
        stompClient.send("/app/handle", {}, JSON.stringify(notification));
      };

    async function clickAccept(idFriend) {
        code = codeBetweenTwoUser(user.id, idFriend);
        isFriend = 1;
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 1}
        addFriendTest(friendRequest);
        sendNotifiAgreed("Agreed to the friend request", idFriend);
        const btnResponse = document.getElementById("btn-response");
        const btnStatus = document.getElementById("btn-status");
        btnResponse.classList.add('hidden');
        btnStatus.classList.remove('hidden');
    }

    async function clickDecline(idFriend) {
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 0}
        isFriend = 0;
        addFriendTest(friendRequest);
        const btnResponse = document.getElementById("btn-response");
        const btnAddContact = document.getElementById("btn-add-contact");
        btnResponse.classList.add('hidden');
        btnAddContact.classList.remove('hidden');
    }

    async function sendMessage() {
        try { 
            profile.code = codeBetweenTwoUser(user.id, profile.id);
            profile.isFriend = profile.isFriend ? profile.isFriend : isFriend;
            updateUISearch(profile.id, profile.fullName, profile.isFriend);
            const code = profile.code;
            const idUser = user.id;
            const index = 0;
            const id = profile.id;
            const nameChatRoom = profile.fullName;
            setMessageRequest({ code, idUser, index });
            setOpenProfile(false)
            setOpenMemberGroup(false)
            setChatRoom({ code, id, nameChatRoom, isFriend });
        } catch (error) {
            
        }
    }

  return (
    <div className="contact-container" ref={ref}>
        <div className="contact-card">
            <div className="header">
                <div className="contact-avatar">ND</div>
                <div className="titleCloseBtn">
                    <button onClick={() => {setOpenProfile(false) }}> &times; </button>
                </div>
            </div>
            <div className="contact-name">{profile.fullName}</div>
            <button id="btn-add-contact" className={`contact-button ${profile.isFriend === 0 ? '': 'hidden'}`}  onClick={() => clickAddContact(profile.id)}>Add contact</button>
            <span id="btn-status" className={`contact-button ${profile.isFriend === 1 ? '': 'hidden'}   ${profile.status.includes('ONLINE') ? 'user-online': 'user-offline'}`}  >{profile.status}</span>
            <div className="span-added">
                <span className ={`${profile.isFriend === 2 ? '': 'hidden'} `} id="added-to-contacts">✓ Added to contacts</span>
            </div>
            <div id="btn-response" className={`response-add-contact ${profile.isFriend === 3 ? '': 'hidden'}`}>
                <button className='contact-button btn-accept' onClick={() => clickAccept(profile.id)} >Accept</button>
                <button className='contact-button btn-decline' onClick={() => clickDecline(profile.id)}>Decline</button>
            </div>
            <div className='list-item-contact'> 
                <div className= 'item-contact' onClick={() => {sendMessage()}}>
                    <img src='./src/assets/img/messenger.png' alt="user icon" />
                    <div className="item-name">
                        <span>Send message</span>
                    </div>
                </div>
                <div className= 'item-contact'>
                    <img src='./src/assets/img/video.png' alt="user icon" />
                    <div className="item-name">
                        <span>Start video call</span>
                    </div>
                </div>
                <div className= 'item-contact'>
                    <img src='./src/assets/img/users.png' alt="user icon" />
                    <div className="item-name">
                        <span>Create new group with { }</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
});

export default ProfileComponent;