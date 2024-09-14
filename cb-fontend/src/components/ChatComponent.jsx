import './init';
import React, { useEffect, useRef, useState } from 'react';
import LeftMenuComponent from './LeftMenuComponent';
import RenderChatArea from './RenderArea';
import { clickAddContact, codeBetweenTwoUser, createChatRoomUI, displayDateTime, displayMsgFile, displayMsgText, getFirstName, handleLastMessage, isNewDay, outGroupUI, activeNewChatRoom, unfriendUI } from '../logics/eventHandlers';
import { useStompClient } from '../contexts/StomppClientContext';
import { useAccount } from '../contexts/AccountContext';
import { useChatRoom } from '../contexts/ChatRoomContext';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';
import { useUser } from '../contexts/UserContext';
import { uploadFile } from '../services/FileService';
import axios from 'axios';
import AddToGroupComponent from './AddToGroupComponent';
import OutGroupComponent from './OutGroupComponent';
import ParticipantsComponent from './ParticipantsComponent';
import addNotification from 'react-push-notification';
import logo from '/src/assets/img/facebook.png';
import { countNotifiUnread } from '../services/NotificationService';
import { addFriendTest } from '../services/FriendService';
import { useMessageRequest } from '../contexts/MessageRequestContext';
import Picker from '@emoji-mart/react';
import data from '@emoji-mart/data';
import ProfileComponent from './ProfileComponent';
import { useListChatRoom } from '../contexts/ListChatRoomContext';

var stompClient = null;
var selectedId = null;
var listFile = [];
var varCountNotifi = 0;
const ChatComponent = () => {
    const { setStompClient } = useStompClient();
    const { messageRequest, setMessageRequest } = useMessageRequest();
    const messageInputRef = useRef(null);
    const { account } = useAccount();
    const { chatRoom, setChatRoom } = useChatRoom();
    const { user } = useUser();
    const [countNotifi, setCountNotifi] = useState(0);

    const fileInputRef = React.createRef();
    const [images, setImages] = useState([]);
    const [lastMessage, setLastMessage] = useState(null);
    const lastMessageRef = useRef(lastMessage);
    const chatRoomClick = useRef();
    const [open, setOpen] = useState(false);
    const menuGroup = ["Add to Group", "Participants", "Leave"];
    const menuSolo = ["Create new Group", "Unfriend"];
    const menuRef = useRef();
    const imgRef = useRef();

    //search-message
    const [ openSearchMessage, setOpenSearchMessage ] = useState(false);

    const [ isClickContact, setIsClickContact ] = useState(null);
    const { listChatRoom, setListChatRoom } = useListChatRoom();

    //overlay
    const [openAddToGroup, setOpenAddToGroup] = useState(false);
    const addToGroupRef = useRef();

    const [openOutGroup, setOpenOutGroup] = useState(false);
    const outGroupRef = useRef();

    const [openMemberGroup, setOpenMemberGroup] = useState(false);
    const outMemberGroupRef = useRef();

    const [ profile, setProfile ] = useState(null);
    const [openProfile, setOpenProfile] = useState(false);
    const profileRef = useRef();

    //Emoji
    const [ isPickerVisible, setPickerVisible ] = useState(false);

    useEffect(() => {
        varCountNotifi = countNotifi;
    }, [countNotifi]);

    useEffect(() => {
        lastMessageRef.current = lastMessage;
    }, [lastMessage]);

 
    const sendMessageText = (messageInput) => {
        const chatMessage = {
            sender: account.idUser,
            received: chatRoom.id,
            content: messageInput.value.trim(),
            code: chatRoom.code,
            sendTime: new Date(),
            isEnable: 1,
            type: 1,
            messageEdt: null,
            senderName: user.fullname
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        moveToTop(chatRoom.id, messageInput.value.trim());
        messageInput.value = '';
    };
    const sendMessageFile = async (listFile) => {
        for (const file of listFile) {
            try {
                var type = null;
                type = file.type.includes('image')? 2 : 3;
                const formData = new FormData();
                formData.append('file', file);
                formData.append('code', chatRoom.code);
                await uploadFile(formData);

                const chatMessage = {
                    sender: account.idUser,
                    received: chatRoom.id,
                    content: file.name,
                    code: chatRoom.code,
                    sendTime: new Date(),
                    isEnable: 1,
                    type: type,
                    messageEdt: null,
                    senderName: user.fullname
                };
                stompClient.send("/app/chatFile", {}, JSON.stringify(chatMessage));
                moveToTop(chatRoom.id, "Đã gửi hình ảnh");
            } catch (error) {
                console.log(error);
            }
        }
        listFile.length = 0;
        setImages([]);
    };
    
    useEffect(() => {
        if (chatRoom != null) {
            selectedId = chatRoom.id;
            chatRoomClick.current = chatRoom;
        }
        const messageInput = messageInputRef.current;
        const handleKeyDown = (event) => {
            if (event.key === 'Enter') {
                if(messageInput && stompClient && !(messageInput.value.trim().length ===0)) {
                    sendMessageText(messageInput);
                }
                if(listFile.length > 0) {
                    sendMessageFile(listFile);
                }
            }
        };
        if (messageInput) {
            messageInput.addEventListener('keydown', handleKeyDown);
        }

        return () => {  
            if (messageInput) {
                messageInput.removeEventListener('keydown', handleKeyDown);
            }
        };
    }, [chatRoom]);

    useEffect(() => {
        const formData = new FormData();
        formData.append('idUser', account.idUser);
        countNotifiUnread(formData).then((response) =>  {setCountNotifi(response.data); varCountNotifi = response.data});
        let socket = new SockJS("http://localhost:8080/ws");
        stompClient = over(socket);
        stompClient.connect({}, () => onConnected(stompClient, account, user), onError);
        setStompClient(stompClient);
    }, []);

    const onConnected = (stompClient, accountData, userData) => {
        if (accountData && accountData.idUser) {
            stompClient.subscribe(`/user/${accountData.idUser}/queue/messages`, onMessageReceived);
            stompClient.subscribe(`/user/${accountData.idUser}/queue/notification`, onNotificationReceived);
            stompClient.send("/app/user.connectUser", {}, JSON.stringify(userData));
        } else {
            console.error("Account data is not defined");
        }
      }

    window.addEventListener('beforeunload', (event) => {
        stompClient.disconnect();
        stompClient.send("/app/user.disconnectUser", {}, JSON.stringify(user));
    });
    
    const onError = (error) => {
        console.error("Connection error:", error);
      }

    function createMessageContent(message) {
        const prevMessage = lastMessageRef.current;
        const chatArea = document.querySelector('#chat-messages');
        const messageContainer = document.createElement('div');
        messageContainer.classList.add('message-container');
        const messageContent = document.createElement('div');
        messageContent.classList.add('message');

        if (message.sender === account.idUser) {
            messageContainer.classList.add('sender');
            messageContent.classList.add('message-sender');
        } else {
            messageContainer.classList.add('receiver');
            messageContent.classList.add('message-receiver');
            if (!prevMessage || message.sender !== prevMessage.sender || isNewDay(message.sendTime, prevMessage.sendTime) || prevMessage.type === 4) {
                const senderName = document.createElement('span');
                senderName.classList.add('sender-name');
                senderName.textContent = getFirstName(message.senderName);
                messageContainer.appendChild(senderName);
            }
        }

        messageContent.addEventListener('click', messageClick, true)
        messageContainer.appendChild(messageContent);
        chatArea.appendChild(messageContainer);

        if (!prevMessage || isNewDay(message.sendTime, prevMessage.sendTime)) {
            const dateContainer = displayDateTime(message.sendTime);
            chatArea.insertBefore(dateContainer, messageContainer);
        }
        return messageContent;
    }

    function createMessageNotifi(message) {
        const prevMessage = lastMessageRef.current;
        const chatArea = document.querySelector('#chat-messages');
        const messageContainer = document.createElement('div');
        messageContainer.classList.add('message-container');
        const messageContent = document.createElement('div');
        messageContent.classList.add('message');

        messageContainer.classList.add('notifi');
        messageContent.classList.add('message-notifi');
        messageContent.addEventListener('click', messageClick, true)
        messageContainer.appendChild(messageContent);
        chatArea.appendChild(messageContainer);

        if (isNewDay(message.sendTime, prevMessage.sendTime)) {
            const dateContainer = displayDateTime(message.sendTime);
            chatArea.insertBefore(dateContainer, chatArea.firstChild);
        }
        return messageContent
    }

    function displayMessage(message) {
        if (message.type === 2) {
            const messageContent = createMessageContent(message);
            const formData = new FormData();
            formData.append('fileName', message.content);
            axios.post("http://localhost:8080/file/getFile", formData, {
                responseType: 'blob'
            }).then((response) => {
                const imageBlob = response.data;
                const url = URL.createObjectURL(imageBlob);
    
                const image = document.createElement('img');
                image.src = url;
                messageContent.appendChild(image);
            })
        } else if (message.type === 3) {
            const messageContent = createMessageContent(message);

            const messageFile = displayMsgFile(message.content);
            messageContent.appendChild(messageFile);
        } else if (message.type === 4) {
            const messageContent = createMessageNotifi(message);
            const messageText = displayMsgText(message.content);
            messageContent.appendChild(messageText);
        }else {
            const messageContent = createMessageContent(message);
            const messageText = displayMsgText(message.content);
            messageContent.appendChild(messageText);
        }
        setLastMessage(message);
    }

    const messageClick = (e) => {
        const messageContent = e.currentTarget.textContent;
        console.log('Message clicked:', messageContent);
    };

    async function onMessageReceived(payload) {
        const message = JSON.parse(payload.body);
        var code = message.code.includes('U') ? message.code.replace(user.id, '') : message.code;
        if (!checkChatRoom(code)) {
            await createChatRoomUI(setListChatRoom, account.idUser, message).then( activeNewChatRoom(selectedId, message.received));
        }
        if (message.received.includes('U') && message.sender != selectedId && message.sender != account.idUser) {
            handleNotification(message);
        } else if (message.received.includes('G') && message.received != selectedId && message.sender != account.idUser) {
            handleNotification(message);
        } else {
            displayMessage(message);
            moveToTop(message.received.includes('U') ? message.senderId : message.received, message.content)
        }
    }

    function checkChatRoom(code) {
        return document.getElementById(code);
    }

    function handleNotification(message) {
        var notifiedUser = message.received.includes("G") ? document.querySelector(`#${message.received}`) : document.querySelector(`#${message.sender}`);
        if (notifiedUser) {
            const nbrMsg = notifiedUser.querySelector('.nbr-msg');
            nbrMsg.classList.remove('hidden');
            const parentElement = notifiedUser.parentNode;
            parentElement.insertBefore(notifiedUser, parentElement.firstChild);
            const messageElement = notifiedUser.querySelector('.friend-message');
            if (messageElement) {
                messageElement.textContent = handleLastMessage(message.content);
            }
        }
    }

    async function onNotificationReceived(payload) {
        const notification = JSON.parse(payload.body);
        varCountNotifi =  varCountNotifi + 1
        setCountNotifi(varCountNotifi);
        NotificationWindows(notification.nameChatRoom, notification.content);
        if (notification.type === 1) {
            outGroupUI(notification.idChatRoom, selectedId, setMessageRequest, setChatRoom);
        }
    }

    function moveToTop(userId, newMessageContent) {
        const userElement = document.querySelector(`#${userId}`);
        if (userElement) {
            const parentElement = userElement.parentNode;
            parentElement.insertBefore(userElement, parentElement.firstChild);
            const messageElement = userElement.querySelector('.friend-message');
            if (messageElement) {
                messageElement.textContent = handleLastMessage(newMessageContent);
            }
        }
    }

    const handleIconClick = () => {
        fileInputRef.current.click();
    };
    
    const handleFileChange = (event) => {
        const files = event.target.files;
        if (files.length === 0) return;

        const newImages = [];
        for (let i = 0; i < files.length; i++) {
            var type = "images";
            if (files[i].type.split('/')[0] !== 'image') type = "files";
            newImages.push({
                name: files[i].name,
                url: URL.createObjectURL(files[i]),
                savedName: chatRoom.code + "/" + type + "/" + files[i].name,
            });
            listFile.push(files[i]);
        }
        setImages((prevImages) => [...prevImages, ...newImages]);
        event.target.value = null;
    };

    function deleteImage(index) {
        setImages((prevImage) => 
            prevImage.filter((_, i) => i !== index)
        );  
    }

    const handleKeyDown = (e) => {
        if (e.key === 'Escape') {
            setOpenAddToGroup(false);
            setOpen(false);
            const focusedElement = document.activeElement;
            if (focusedElement.tagName === 'INPUT') {
                if (focusedElement.id === 'searchMessage') {
                    setOpenSearchMessage(false);
                }
            }

        }
    };
    window.addEventListener('keydown', handleKeyDown);

    window.addEventListener('click', (e) => {
        if (e.target !== menuRef.current && e.target !== imgRef.current) {
            setOpen(false);
        }

        if (e.target === addToGroupRef.current) {
            setOpenAddToGroup(false);
        }

        if (e.target === outGroupRef.current) {
            setOpenOutGroup(false);
        }

        if (e.target === outMemberGroupRef.current) {
            setOpenMemberGroup(false);
        }
    })

    const handleItemClickGroup = (item) => {
        if (item === "Add to Group") {
            setOpenAddToGroup(true);
        } else if (item === "Participants") {
           setOpenMemberGroup(true);
        } else if (item === "Leave") {
            setOpenOutGroup(true);
        }
    };

    const handleItemClickSolo = (item) => {
        if (item === "Create new Group") {
            console.log(item);
        } else if (item === "Unfriend") {
            unfriend(chatRoom.id)
        }
    };

    async function unfriend(idFriend) {
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 0}
        addFriendTest(friendRequest);
        unfriendUI();
    }

    const NotificationWindows = (title, content) => {
        addNotification({
            title: title,
            message: content,
            duration: 5000,
            icon: logo,
            native: true,
            onClick: () => console.log(user.id)
        });
    }

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

    async function clickAccept(idFriend, setIsClickContact) {
        setIsClickContact(1);
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 1};
        addFriendTest(friendRequest);
        sendNotifiAdd("Agreed to the friend request", idFriend);
        const headerItemAdd = document.getElementById('header-item-add');
        headerItemAdd.classList.add('hidden');
        const typeMessage = document.getElementById('messageForm');
        typeMessage.classList.remove('hidden');
    }

    async function clickDecline(idFriend, setIsClickContact) {
        setIsClickContact(0);
        const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 0}
        addFriendTest(friendRequest);
        const headerItemAdd = document.getElementById('header-item-add');
        headerItemAdd.classList.add('hidden');
    }

    return (
        <>
        {openAddToGroup &&  <AddToGroupComponent setOpenAddToGroup = {setOpenAddToGroup} idUser = {account.idUser} idGroup={selectedId} ref={addToGroupRef}/>} 
        {openOutGroup && <OutGroupComponent setOpenOutGroup = {setOpenOutGroup} setMessageRequest = {setMessageRequest} idGroup={selectedId} ref={outGroupRef}/>}
        {openMemberGroup && <ParticipantsComponent setOpenMemberGroup = {setOpenMemberGroup} idUser = {account.idUser} idGroup={selectedId} nameChatRoom = {chatRoom.nameChatRoom} ref={outMemberGroupRef}/>}
        {openProfile && <ProfileComponent  setOpenProfile = {setOpenProfile} profile = {profile} ref={profileRef}/>}  
        <div className="chat-container" id  ="chat-page">
                <LeftMenuComponent setMessageRequest={setMessageRequest} countNotifi = {countNotifi} setCountNotifi = {setCountNotifi} isClickContact = {isClickContact}  setProfile = {setProfile} setOpenProfile={setOpenProfile}/>
            <div className="chat-area">
                <div className='w-100 hearder-item hidden' id='hearder-chat'>
                    <div className='m-2 w-100'>
                        <div>
                            <img src='./src/assets/img/user_icon.png' alt="user icon" />
                            <span id='nameBoxChat'></span>
                        </div>
                        <div className="w-50">
                            <div className='d-flex justify-content-end icon-container'>
                                <img src="./src/assets/img/search.png" className='icon' alt="video camera icon" onClick={() => {setOpenSearchMessage(!openSearchMessage)}}/>
                                <img src="./src/assets/img/video-camera.png" className='icon' alt="video camera icon" />
                                <img src="./src/assets/img/more.png" className='icon' alt="more icon" onClick={() => setOpen(!open)} ref={imgRef}/>
                                {open &&  (
                                    <div id="popup-menu" className="popup-menu" onClick={() => setOpen(true)} ref={menuRef}>
                                        <ul>
                                            { selectedId.includes('G') ? menuGroup.map((item) => (
                                                    <li key={item} onClick={() => handleItemClickGroup(item)}> {item} </li>
                                                )) :  menuSolo.map((item) => (
                                                    <li key={item} onClick={() => handleItemClickSolo(item)}> {item} </li>
                                                ))
                                            }
                                        </ul>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                    {
                        openSearchMessage && <div className='search-message'>
                            <div className='input-group'>
                                <input type="text" placeholder="Find messages in current conversation" className='form-control' id='searchMessage'/>
                                <div className='search-footer'>
                                    <span className='message-count'>0 / 0</span>
                                    <button className='cancel-button' onClick={() => {setOpenSearchMessage(!openSearchMessage)}}>Cancel</button>
                                </div>
                            </div>
                        </div>
                    }
                    
                </div>
                
                <div className='w-100'>
                    <div className='header-add-contact hidden' id='btn-header-add' onClick={() => {clickAddContact(selectedId, user, sendNotifiAdd, setIsClickContact)}}>
                        <button className="header-btn-contact btn-add">Add contact</button>
                    </div>
                    <div className='hearder-item-contact hidden' id='header-item-add'>
                        <div className='item-contact-container m-2 w-50' onClick={() => {clickAccept(selectedId, setIsClickContact)}}>
                                <button className="header-btn-contact btn-accept">Accept</button>
                        </div>
                        <div className="item-contact-container m-2 w-50"  onClick={() => {clickDecline(selectedId, setIsClickContact)}}>
                            <button className="header-btn-contact refuse btn-decline">Decline</button>
                        </div>
                    </div>
                </div>
                
                {messageRequest && <RenderChatArea messageRequest={messageRequest}  setLastMessage = {setLastMessage}/>}

                <div id="messageForm" name="messageForm" className='hidden' >
                    <div className="message-input">
                        <div className='d-flex justify-content-center align-items-center'>
                            <img src="./src/assets/img/image.png" className='icon' alt="image icon"  onClick={handleIconClick}/>
                            <input type="file" name='file' ref={fileInputRef} style={{ display: 'none' }} onChange={handleFileChange} multiple />
                        </div>
                        <div className='input-wrapper'>
                            <div className='container-file'> 
                                {
                                    images.map((images, index) => (
                                        images.savedName.includes("images") ? (
                                            <div className='image' key={index}>
                                                <span className='delete-file' onClick={() => deleteImage(index)}>&times;</span>
                                                <img src={images.url} alt={images.name} />
                                            </div>
                                        ) : (
                                            <div className='file' key={index}>
                                               <div className="message-file">
                                                    <div className="circle-file">
                                                        <img src='./src/assets/img/document.png' alt="user icon" className='icon'/>
                                                    </div>
                                                    <span>{images.name}</span>
                                                    <span className='delete-file' onClick={() => deleteImage(index)}>&times;</span>
                                                </div>
                                            </div>
                                        )
                                    ))
                                }
                            </div>
                            <input type="text" id="message" autoComplete="off" placeholder="Type your message..." className='inputMessage'  ref={messageInputRef}/>
                            <div className='btn-emoji' onClick={() => {setPickerVisible(!isPickerVisible);}}>
                                <img src="./src/assets/img/smile.png" className='icon' alt="image icon"/>
                               
                            </div>
                            <div className={`table-emoji ${isPickerVisible? 'd-block': 'd-none'}`}>
                                <Picker 
                                data = {data} 
                                previewPosition="none"
                                onEmojiSelect={(e) => {
                                    if(messageInputRef.current) {
                                        messageInputRef.current.value += e.native;
                                    }
                                    setPickerVisible(!isPickerVisible);
                                }} 
                               />
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </>
    );
};

export default ChatComponent;