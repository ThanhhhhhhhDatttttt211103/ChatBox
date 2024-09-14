import React, { useEffect, useRef, useState } from 'react';
import { useAccount } from '../contexts/AccountContext';
import { useNavigate } from 'react-router-dom';
import { useChatRoom } from '../contexts/ChatRoomContext';
import { apiListFriend, listChatRooms } from '../services/ChatRoomService';
import { useUser } from '../contexts/UserContext';
import { useStompClient } from '../contexts/StomppClientContext';
import { activeNewChatRoom, formatDateNotification, handleLastMessage, handleSubstringNotification, offSearch, onSearch, updateUI, updateUISearch } from '../logics/eventHandlers';
import { getNotifications, updateNotifiUnread } from '../services/NotificationService';
import { getProfile } from '../services/UserService';
import { useListChatRoom } from '../contexts/ListChatRoomContext';

var selectedId = null;
var indexChatRoom = null;
const LeftMenuComponent = ({ setMessageRequest, countNotifi, setCountNotifi, isClickContact, setProfile, setOpenProfile }) => {
    const { stompClient } = useStompClient();
    const { account } = useAccount();
    const [listFriend, setListFriend] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [listNotification, setListNotification] = useState([]);
    const navigate = useNavigate();
    const { chatRoom ,setChatRoom } = useChatRoom();
    const { user } = useUser();
    const { listChatRoom, setListChatRoom } = useListChatRoom();

    //show notifications
    const [openNotification, setOpenNotification] = useState(false);
    const notificationRef = useRef();
    const bellRef = useRef();

    useEffect(() => {
        var chatRoom =  listChatRoom.at(indexChatRoom);
        if (chatRoom) {
            chatRoom.isFriend = isClickContact;
        }
    }, [isClickContact]);

    useEffect(() => {
        if (chatRoom) {
            selectedId = chatRoom.id;
        }
    }, [chatRoom]);

    // useEffect(() => {
    //     if(listChatRoom.length > 0){
    //         activeNewChatRoom(selectedId, listChatRoom.at(0).idFriendOrGroup)
    //     }
    // }, [listChatRoom.length > 0]);
  
    useEffect(() => {
        const fetchLastMessages = async () => {
            try {
                const response = await listChatRooms(account.idUser);
                setListChatRoom(response.data);
            } catch (error) {
                console.error('Error fetching last messages:', error);
            }
        };
        fetchLastMessages();
    }, [account.idUser, countNotifi]);

    useEffect(() => {
        const notifiElement = document.getElementById('total');
        if (notifiElement) {
            if (countNotifi === 0) {
                notifiElement.classList.add('hidden');
            } else {
                notifiElement.classList.remove('hidden');
            }
        }
    }, [countNotifi]);

    const clickFriend = (e, indexFriend) => {
        const isFriend = e.currentTarget.getAttribute('isfriend');
        const code = e.currentTarget.getAttribute('code');
        const idUser = account.idUser;
        const index = 0;
        const id = e.currentTarget.getAttribute('id');
        const nameChatRoom = e.currentTarget.getAttribute('name');
        updateUI(e, isFriend);
        if (selectedId === id) {
            return
        }
        setMessageRequest({ code, idUser, index });
        setChatRoom({ code, id, nameChatRoom, isFriend });
        selectedId = id;
        indexChatRoom = indexFriend;
    };

    function clickLogout() {
        stompClient.send("/app/user.disconnectUser", {}, JSON.stringify(user));
        stompClient.disconnect();
        selectedId = null;
        setChatRoom(null);
        setMessageRequest(null)
        navigate('/');
    }

    const handleSearchClick = (event) => {
        onSearch();
        event.target.value = null;
    };

    const handleSearchChange = async (event) => {
        try {
            const value = event.target.value;
            setSearchTerm(value);
            const formData = new FormData();
            formData.append('idUser', account.idUser);
            formData.append('name', value);
            const response = await apiListFriend(formData);
            setListFriend(response.data);
            event.target.value = null;
        } catch (error) {

        }
    };

    const clickFriendSearch = (e) => {
        offSearch();
        const code = e.currentTarget.getAttribute('code');
        const idUser = account.idUser;
        const index = 0;
        const id = e.currentTarget.getAttribute('id');
        const nameChatRoom = e.currentTarget.getAttribute('name');
        updateUISearch(id, nameChatRoom);
        setMessageRequest({ code, idUser, index });
        setChatRoom({ code, id, nameChatRoom});
        setListFriend([]);
        setSearchTerm([]);
        selectedId = id;
    };

    const clickBackSearch = (e) => {
        offSearch();
    };

    window.addEventListener('click', (e) => {
        const notificationContainer = notificationRef.current;
        if (notificationContainer && !notificationContainer.contains(e.target) && e.target !== bellRef.current) {
            setOpenNotification(false);
        }
    });

    async function hanldeOnClickBell() {
        setOpenNotification(!openNotification); 
        const formData = new FormData();
        formData.append('idUser', account.idUser);
        if (!openNotification) {
            getNotifications(formData).then((response) => {setListNotification(response.data);});
        }

        if (openNotification && countNotifi > 0) {
            updateNotifiUnread(formData)
            setCountNotifi(0);
        }
    }

    async function clickNotifiAddContact(idFriend) {
        const formData = new FormData();
        formData.append('idUser', user.id);
        formData.append('idFriend', idFriend);
        const response = await getProfile(formData);
        setProfile(response.data);
        setOpenProfile(true);
        setOpenNotification(false);
    }

    return (
        <>
        <div className="users-list">
            <div className="users-container">
                <div className='users-header icon-container d-flex'>
                    <h2 className='ml-2'>Online Users</h2>
                    <a className="position-relative mb-1 my-auto notifi-container">
                        <img src='./src/assets/img/bell.png' alt="icon" onClick={hanldeOnClickBell} ref={bellRef}/>
                        <span className= 'notifi-count' id="total">{countNotifi}</span>
                    </a>
                    {openNotification &&  (
                            <div className="notification-container" ref={notificationRef}>
                                <div className='notification-header'>Notifications</div>
                                <div className='notification-body'>
                                    <ul>
                                        {listNotification.map((item, index) => (
                                            <li className= {`notification-item ${item.isRead === 0 ? 'notification-unread': ''}`} key={index} onClick={item.type === 2 ? () => clickNotifiAddContact(item.idChatRoom) : null}>
                                               <div className='notification-content'>
                                                    <img src='./src/assets/img/user_icon.png' alt="user icon" />
                                                    <div className='notification-info'>
                                                        <span className='notification-title'>{item.nameChatRoom}</span>
                                                        <span className='notification-message'>{handleSubstringNotification(item.content)}</span>
                                                    </div>
                                                </div>
                                                <div className= 'notification-time'>
                                                    <span className='notification-message'>{formatDateNotification(item.sendTime)}</span>
                                                </div>
                                            </li>
                                            ))
                                        }
                                    </ul>
                                </div>
                            </div>
                    )}
                </div>
                <div className='text-center d-flex justify-content-center align-items-center'>
                    <img src='./src/assets/img/arrow.png' alt="icon" className='iconBack hidden' id='iconBackSearch' onClick={clickBackSearch}/>
                    <input type="text" placeholder="Tìm kiếm..." className='mb-2 rounded-4 w-100 h-100 form-control' value={searchTerm} onChange={handleSearchChange} onClick={handleSearchClick}/>
                </div>
                <ul id="connectedUsers">
                    {listChatRoom.map((chatRoom, index) =>
                        <li className='user-item' onClick={(e) => {clickFriend(e, index)}} key={index} id={chatRoom.idFriendOrGroup} name={chatRoom.nameChatRoom} code={chatRoom.code} isfriend = {chatRoom.isFriend} index = {index}>
                            <img src='./src/assets/img/user_icon.png' alt="user icon" />
                            <div className='user-info'>
                                <span className='friend-name'>{chatRoom.nameChatRoom}</span>
                                <span className='friend-message'>{handleLastMessage(chatRoom.lastMessage)}</span>
                            </div>
                            <span className='nbr-msg hidden'></span>
                        </li>   
                    )}
                </ul>
                    
                <ul id="listUsers" className='hidden'>
                    {listFriend.map(friend => 
                        <li className='user-item' onClick={clickFriendSearch} key={friend.idFriendOrGroup} id={friend.idFriendOrGroup} name={friend.nameChatRoom} code={friend.code}>
                            <img src='./src/assets/img/user_icon.png' alt="user icon" />
                            <div className='user-info'>
                                <span className='friend-name'>{friend.nameChatRoom}</span>
                            </div>
                            <span className='nbr-msg hidden'></span>
                        </li>
                    )}
                </ul>

            </div>
            <div>
                <p id="connected-user-fullname">{user.fullname}</p>
                <a className="logout" href="#" onClick={clickLogout} id="logout">Logout</a>
            </div>
        </div>
        </>
    );
};

export default LeftMenuComponent;