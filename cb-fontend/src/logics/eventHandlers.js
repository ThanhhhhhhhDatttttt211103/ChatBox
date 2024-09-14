import axios from "axios";
import { createChatRoom } from "../services/ChatRoomService";
import { addFriendTest } from "../services/FriendService";


export function updateUI(event, isFriend) {
  const btnHeaderAdd = document.getElementById('btn-header-add');
  btnHeaderAdd.classList.add('hidden');
  const headerItemAdd = document.getElementById('header-item-add');
  headerItemAdd.classList.add('hidden');
  const clickedUser = event.currentTarget;
  document.querySelectorAll('.user-item').forEach(item => {
      item.classList.remove('active');
  });
  clickedUser.classList.add('active');

  const dotWait = clickedUser.querySelector('.nbr-msg');
  dotWait.classList.add('hidden');

  const hearderChat = document.getElementById('hearder-chat');
  hearderChat.classList.remove('hidden')


  const nameElement = document.getElementById('nameBoxChat');
  nameElement.textContent = event.currentTarget.getAttribute('name')

  const typeMessage = document.getElementById('messageForm');
  typeMessage.classList.remove('hidden');

  if (isFriend && parseInt(isFriend) === 0 ) {
    const btnItemAdd = document.getElementById('btn-header-add');
    btnItemAdd.classList.remove('hidden');
    typeMessage.classList.add('hidden');  
  }
  else if (parseInt(isFriend) === 3) {
    const headerItemAdd = document.getElementById('header-item-add');
    headerItemAdd.classList.remove('hidden');
    typeMessage.classList.add('hidden');
  }
  else {
    typeMessage.classList.remove('hidden');
  }
}

export function activeNewChatRoom(selectId, idFriend) {
  console.log(idFriend);
  if (selectId === idFriend) {
    document.querySelectorAll('.user-item').forEach(item => {
      item.classList.remove('active');
    });
    const clickedUser = document.getElementById(idFriend);
    clickedUser.classList.add('active');
  } else {
    const chatRoomWait = document.getElementById(idFriend);
    const dotWait = chatRoomWait.querySelector('.nbr-msg');
    if (dotWait) {
      dotWait.classList.remove('hidden');
    }
  }
}

export function updateUISearch(id, name, isFriend) {
  console.log(id + " " + name + " " +  isFriend)
  const btnHeaderAdd = document.getElementById('btn-header-add');
  btnHeaderAdd.classList.add('hidden');
  const headerItemAdd = document.getElementById('header-item-add');
  headerItemAdd.classList.add('hidden');
  const typeMessage = document.getElementById('messageForm');
  typeMessage.classList.remove('hidden');

  const clickedUser = document.querySelector('#'+id)
  document.querySelectorAll('.user-item').forEach(item => {
      item.classList.remove('active');
  });
  if (clickedUser) {
    clickedUser.classList.add('active');
    const dotWait = clickedUser.querySelector('.nbr-msg');
    if (dotWait) {
      dotWait.classList.add('hidden');
    }
  }

  const hearderChat = document.getElementById('hearder-chat');
  hearderChat.classList.remove('hidden')

  if (isFriend > 2 || isFriend === 0) {
    typeMessage.classList.add('hidden');
  }

  const nameElement = document.getElementById('nameBoxChat');
  nameElement.textContent = name;

  if (isFriend === 0 ) {
    const btnHeaderAdd = document.getElementById('btn-header-add');
    btnHeaderAdd.classList.remove('hidden');
  }
  
  if (isFriend === 3) {
    const headerItemAdd = document.getElementById('header-item-add');
    headerItemAdd.classList.remove('hidden');
  }
}

export function getFirstName(fullName) {
  const nameParts = fullName.trim().split(' ');
  return nameParts[nameParts.length - 1];
}

export function onSearch() {
  const iconBackSearch = document.querySelector('#iconBackSearch');
  iconBackSearch.classList.remove('hidden');
  const connectedUsers = document.querySelector('#connectedUsers');
  connectedUsers.classList.add('hidden');
  const listUsers = document.querySelector('#listUsers');
  listUsers.classList.remove('hidden');
}

export function offSearch() {
  const iconBackSearch = document.querySelector('#iconBackSearch');
  iconBackSearch.classList.add('hidden');
  const connectedUsers = document.querySelector('#connectedUsers');
  connectedUsers.classList.remove('hidden');
  const listUsers = document.querySelector('#listUsers');
  listUsers.classList.add('hidden');
}


export function isNewDay(time, timeNext) {
  const date1 = new Date(time);
  const date2 = new Date(timeNext);
  return date1.getFullYear() !== date2.getFullYear() || date1.getMonth() !== date2.getMonth() || date1.getDate() !== date2.getDate();
}

export function formatDate(timestamp) {
  const date = new Date(timestamp);
  const today = new Date();
  const yesterday = new Date(today.getTime() - 86400000);
  const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const dayOfWeek = daysOfWeek[date.getDay()];
  const day = date.getDate();
  const month = months[date.getMonth()];
  const year = date.getFullYear();

  if (date.toDateString() === today.toDateString()) {
      return "Today";
  } else if (date.toDateString() === yesterday.toDateString()) {
      return "Yesterday";
  } else if ((today.getTime() - date.getTime()) <= (86400000 * 5)){
    return  dayOfWeek ;
  } else {
    return `${dayOfWeek}, ${month} ${day}, ${year}`
  }
}

export function formatDateNotification(timestamp) {
  const date = new Date(timestamp);
  const today = new Date();
  const yesterday = new Date(today.getTime() - 86400000);
  const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  const dayOfWeek = daysOfWeek[date.getDay()];
  const day = date.getDate();
  const month = date.getMonth();
  const year = date.getFullYear();
  
  const hours = date.getHours();
  const minutes = date.getMinutes();
  const ampm = hours >= 12 ? 'PM' : 'AM';
  const formattedHours = hours % 12 || 12;
  const formattedMinutes = minutes.toString().padStart(2, '0');

  if (date.toDateString() === today.toDateString()) {
    return `Today at ${formattedHours}:${formattedMinutes}${ampm}`;
  } else if (date.toDateString() === yesterday.toDateString()) {
    return `Yesterday at ${formattedHours}:${formattedMinutes}${ampm}`;
  } else if ((today.getTime() - date.getTime()) <= (86400000 * 5)) {
    return `${dayOfWeek} at ${formattedHours}:${formattedMinutes}${ampm}`;
  } else {
    return `${month + 1}/${day}/${year}`;
  }
}

export async function displayMsgImage(content) {
  const formData = new FormData();
  formData.append('fileName', content);

  try {
    const response = await axios.post("http://localhost:8080/file/getFile", formData, {
      responseType: 'blob'
    });

    const imageBlob = response.data;
    const url = URL.createObjectURL(imageBlob);

    const image = document.createElement('img');
    image.src = url;
    
    return image; 
  } catch (error) {
    throw error; 
  }
}

export function displayMsgFile(content) {
  const messageFile = document.createElement('div');
  messageFile.classList.add('message-file');
  const circleFile = document.createElement('div');
  circleFile.classList.add('circle-file');
  const iconFile = document.createElement('img');
  iconFile.src = "./src/assets/img/document.png";
  iconFile.classList.add('icon');
  const nameFile = document.createElement('span')
  nameFile.textContent = content;
  circleFile.appendChild(iconFile);
  messageFile.appendChild(circleFile);
  messageFile.appendChild(nameFile);
  return messageFile
}

export function displayMsgText(content) {
  const messageText = document.createElement('span');
  messageText.textContent = content;
  return messageText
}

export function displayDateTime(sendTime) {
  const dateContainer = document.createElement('div');
  dateContainer.classList.add('date-bar');
  const dateText = document.createElement('span');
  dateText.classList.add('date-text');
  dateText.textContent = formatDate(sendTime);
  dateContainer.appendChild(dateText);
  return dateContainer;
}

function handleSubstring(message) {
  if (message.length > 23) {
    return  message.substring(0, 23) + '...';
  }
  return message;
}

export function handleLastMessage(lastMessage) {
  if (lastMessage.includes('images'))
      return "Đã gửi một ảnh";
  if (lastMessage.includes('files'))
      return "Đã gửi một tệp đính kèm"; 
  return  handleSubstring(lastMessage);
}

export async function createChatRoomUI(setListChatRoom, idUser, message) {
  const formData = new FormData();
  formData.append('idUser', idUser);
  var codeChatRoom = message.code;
  if (message.code.includes('U'))
    codeChatRoom =  message.code.replace(idUser, '')
  formData.append('idFriend', codeChatRoom);
  const response = await createChatRoom(formData);
  const chatRoomNew = response.data;
  chatRoomNew.sender = message.sender;
  chatRoomNew.lastMessage = message.content;
  chatRoomNew.lastSendTime = message.sendTime;
  setListChatRoom(prevListChatRoom => {
    if (prevListChatRoom) {
      return [chatRoomNew, ...prevListChatRoom];
    } else {
      return [chatRoomNew];
    }
  });
}

export function outGroupUI(idGroup, selectId,  setMessageRequest, setChatRoom) {
  if (selectId === idGroup) {
    const hearderChat = document.getElementById('hearder-chat');
    hearderChat.classList.add('hidden')
    const typeMessage = document.getElementById('messageForm');
    typeMessage.classList.add('hidden');
    setMessageRequest("");
    setChatRoom("");
  }

  const listChatRoom = document.getElementById("connectedUsers");
  const chatRoom = listChatRoom.querySelector(`#${idGroup}`);
  listChatRoom.removeChild(chatRoom);

}

export function unfriendUI() {
  const typeMessage = document.getElementById('messageForm');
  typeMessage.classList.add('hidden');
}

export function handleSubstringNotification(message) {
  if (message.length > 20) {
    return  message.substring(0, 23) + '...';
  }
  return message;
}

export function codeBetweenTwoUser(idUser1, idUser2){
  const numberOne = parseInt(idUser1.replace('U', ''), 10);
  const numberTwo = parseInt(idUser2.replace('U', ''), 10);
  return numberOne > numberTwo ? (idUser2 + idUser1) : (idUser1 + idUser2);
}



//add Friend
export function clickAddContact(idFriend, user ,sendNotifiAdd, setIsClickContact) {
  setIsClickContact(2);
  const friendRequest = {idUser: user.id, idFriend: idFriend, code: codeBetweenTwoUser(user.id, idFriend), type: 2}
  addFriendTest(friendRequest);
  sendNotifiAdd(user.fullname + ' sent you a friend request', idFriend);
  const btnAddContact = document.getElementById('btn-header-add');
  btnAddContact.classList.add('hidden');
  const typeMessage = document.getElementById('messageForm');
  typeMessage.classList.remove('hidden');
}