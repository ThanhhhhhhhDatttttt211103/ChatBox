import React, { useEffect, useRef, useState } from 'react';
import { getMessage } from '../services/MessageService';
import { useAccount } from '../contexts/AccountContext';
import axios from 'axios';
import { displayDateTime, displayMsgFile, displayMsgText, formatDate, isNewDay } from '../logics/eventHandlers';

var messageRequestCurrent = null;
var messageLast = null;
const RenderChatArea = React.memo(({ messageRequest, setLastMessage }) => {
    const { account } = useAccount();
    const [listMessage, setListMessage] = useState([]);
    const chatAreaRef = useRef(null);
    useEffect(() => {
        messageRequestCurrent = messageRequest;
        const fetchMessages = async () => {
            try {
                const response = await getMessage(messageRequest);
                const chatArea = chatAreaRef.current;
                chatArea.innerHTML = '';
                setListMessage(response.data);
                setLastMessage(response.data[0]);
                messageLast = response.data[response.data.length - 1];
            } catch (error) {
                console.error('Error fetching messages:', error);
                setListMessage([]);
            }
        };
        fetchMessages();
    }, [messageRequest]);

    const fetchMessageContinue = async (messageRequest) => {
        try {
            const response = await getMessage(messageRequest);
            const updatedMessages = [messageLast, ...response.data];
            setListMessage(updatedMessages);
            messageLast = response.data[response.data.length - 1];
        } catch (error) {
            throw error;
        }
    };

    const messageClick = (e) => {
        const messageContent = e.currentTarget.textContent;
        console.log('Message clicked:', messageContent);
    };

    function createMessageContent(message, index) {
        const chatArea = chatAreaRef.current;
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
            if ((message.sender !== listMessage.at(index + 1).sender || isNewDay(message.sendTime, listMessage.at(index + 1).sendTime) || listMessage.at(index + 1).type === 4)) {
                const senderName = document.createElement('span');
                senderName.classList.add('sender-name');
                senderName.textContent = message.senderName;
                messageContainer.appendChild(senderName);
            }
        }
        
        messageContent.addEventListener('click', messageClick, true)
        messageContainer.appendChild(messageContent);
        chatArea.insertBefore(messageContainer, chatArea.firstChild);

        if (isNewDay(message.sendTime, listMessage.at(index + 1).sendTime)) {
            const dateContainer = displayDateTime(message.sendTime);
            chatArea.insertBefore(dateContainer, chatArea.firstChild);
        }
        return messageContent
    }

    function createMessageNotifi(message, index) {
        const chatArea = chatAreaRef.current;
        const messageContainer = document.createElement('div');
        messageContainer.classList.add('message-container');
        const messageContent = document.createElement('div');
        messageContent.classList.add('message');

        messageContainer.classList.add('notifi');
        messageContent.classList.add('message-notifi');
        messageContent.addEventListener('click', messageClick, true)
        messageContainer.appendChild(messageContent);
        chatArea.insertBefore(messageContainer, chatArea.firstChild);

        if (isNewDay(message.sendTime, listMessage.at(index + 1).sendTime)) {
            const dateContainer = displayDateTime(message.sendTime);
            chatArea.insertBefore(dateContainer, chatArea.firstChild);
        }
        return messageContent
    }

    async function displayMessage(message, index) {
        if (listMessage.length >=11 && index === listMessage.length - 1) return;
        if (!listMessage.at(index + 1) && listMessage.at(0)) {
            displayMsgLast(message);
            return
        }

        if (message.type === 2) {
            const messageContent = createMessageContent(message, index);
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
            const messageContent = createMessageContent(message, index);
            const messageFile = displayMsgFile(message.content);
            messageContent.appendChild(messageFile);
        } else if (message.type === 4) {
            const messageContent = createMessageNotifi(message, index);
            const messageText = displayMsgText(message.content);
            messageContent.appendChild(messageText);
        } 
        else {
            const messageContent = createMessageContent(message, index);
            const messageText = displayMsgText(message.content);
            messageContent.appendChild(messageText);
        }
    }

    function displayMsgLast (message) {
        const chatArea = chatAreaRef.current;
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
            const senderName = document.createElement('span');
            senderName.classList.add('sender-name');
            senderName.textContent = message.senderName;
            messageContainer.appendChild(senderName);
        }

        if (message.type === 2) {
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
            const messageFile = displayMsgFile(message.content);
            messageContent.appendChild(messageFile);
        } else {
            const messageText = displayMsgText(message.content);
            messageContent.appendChild(messageText);
        }

        messageContent.addEventListener('click', messageClick, true)
        messageContainer.appendChild(messageContent);
        chatArea.insertBefore(messageContainer, chatArea.firstChild);
        const dateContainer = displayDateTime(message.sendTime);
        chatArea.insertBefore(dateContainer, chatArea.firstChild);
    }

    const handleScroll = (event) => {
        if (chatAreaRef.current) {
            const { scrollTop, scrollHeight, clientHeight } = chatAreaRef.current;
            if (scrollTop === 0) {
                const previousScrollHeight = scrollHeight - clientHeight;
                messageRequestCurrent.index += 11;
                if (listMessage.length >= 11) {
                    fetchMessageContinue(messageRequestCurrent).then(() => {
                        const newScrollHeight = chatAreaRef.current.scrollHeight;
                        const scrollToPosition = newScrollHeight - previousScrollHeight;
                        chatAreaRef.current.scrollTop = scrollToPosition;
                    }).catch(() => {
                        chatAreaRef.current.scrollTop = 0;
                    });
                }
            }
        }
    };

    return (
        <>
            <div id="chat-messages" ref={chatAreaRef} onScroll={handleScroll} className='message-area'>
            {
                listMessage.map((message, index) => {
                    displayMessage(message, index);
                })      
            }
            </div>
        </>
    );
});
export default RenderChatArea;