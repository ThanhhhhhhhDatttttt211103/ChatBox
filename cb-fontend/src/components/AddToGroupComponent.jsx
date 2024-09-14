import React, { forwardRef, useEffect, useRef, useState } from 'react';
import { getFirstName } from '../logics/eventHandlers';
import { searchUserAdd } from '../services/UserService';
import { saveGroupDetail } from '../services/GroupService';
import { useStompClient } from '../contexts/StomppClientContext';
import { useUser } from '../contexts/UserContext';

var listSeclected = [];
const AddToGroupComponent = forwardRef(({ setOpenAddToGroup, idUser, idGroup, nameChatRoom }, ref) => {
  const { stompClient } = useStompClient();
  const [listFriend, setListFriend] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [checkedItems, setCheckedItems] = useState({});
  const listUserSuggestRef = useRef(null);
  const { user } = useUser();

  const sendNotifi = async (content) => {
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

  const handleClick = (friend) => {
    const listSuggest =  listUserSuggestRef.current;
    if (listSeclected.some(user => user.id === friend.id)) {
      listSeclected = listSeclected.filter(user => user.id !== friend.id);
    } else {
      listSeclected = [...listSeclected, friend];
    }
    setCheckedItems((prev) => ({
      ...prev,
      [friend.id]: !prev[friend.id]  
    }));

    listSuggest.classList.add('isUserSelected');
    if (listSeclected.length === 0) {
      listSuggest.classList.remove('isUserSelected');
    }
  };
  

  useEffect(() => {
    const fetchUserFriend = async () => {
        try {
          const formData = new FormData();
          formData.append('idUser', idUser);
          formData.append('idGroup', idGroup);
          formData.append('name', '');
          const response = await searchUserAdd(formData);
          setListFriend(response.data);
        } catch (error) {
            console.error('Error fetching messages:', error);
            setListMessage([]);
        }
    };
    fetchUserFriend();
}, []);

const handleSearchChange = async (event) => {
  try {
      const value = event.target.value;
      setSearchTerm(value);
      const formData = new FormData();
      formData.append('idUser', idUser);
      formData.append('idGroup', idGroup);
      formData.append('name', value);
      const response = await searchUserAdd(formData);
      setListFriend(response.data);
      event.target.value = null;
  } catch (error) {

  }
};

async function addMemberToGroup() {
  for (const userSelected of listSeclected) {
    const sendTime = new Date();
    const groupDetail = { "idGroup": idGroup, "idUser": userSelected.id, "isEnable": 1 };
    
    try {
      await saveGroupDetail(groupDetail);
      const chatMessage = {
        sender: user.id,
        received: idGroup,
        content: `${user.fullname} added ${userSelected.fullname} to this conversation`,
        code: idGroup,
        sendTime: sendTime,
        isEnable: 1,
        type: 4,
        messageEdt: null,
        senderName: user.fullname
      };
      stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
    } catch (error) {
      console.error(`Failed to save group detail for user ${userSelected.id}`, error);
    }
  }
  listSeclected = []
  setOpenAddToGroup(false);
}


function deleteUser(id) {
  if (listSeclected.some(user => user.id === id)) {
    listSeclected = listSeclected.filter(user => user.id !== id);
  }
  setCheckedItems((prev) => ({
    ...prev,
    [id]: !prev[id]  
  }));
}

  return (
    <div className='addToGroup-bg' ref={ref}> 
        <div className='addToGroup-container'>
            <div className="titleCloseBtn">
                <span>Add to Group</span>
                <button onClick={() => { setOpenAddToGroup(false);  listSeclected = []}}> &times; </button>
            </div>
            <div className='search'>
              <input type="text"  autoComplete="off" placeholder="Search..." value={searchTerm} onChange={handleSearchChange}/>
            </div>
            <div className='body'>
                <div id='listUserSelected' className=''>
                    <div className='user-selected-container'> 
                      {
                        listSeclected.map((user, index) => 
                            <div className='user-selected' key={index}>
                                <span className='delete-file' onClick={() => deleteUser(user.id)}>&times;</span>
                                <img src='./src/assets/img/user_icon.png' alt="user icon" />
                                <span className='name'>{ getFirstName(user.fullname)}</span>
                            </div>
                        )   
                      }
                      </div>
                </div>
                <div className='text-suggest'>
                  <p>Suggested</p>
                </div>
                <div id='listUserSuggest'  className='list-user-suggest' ref={listUserSuggestRef}>
                      {listFriend.map((friend, index) => 
                          <div className="user-item-suggest" key={index} onClick={() => handleClick(friend)}>
                            <img src='./src/assets/img/user_icon.png' alt="user icon" />
                            <div className="friend-name-container">
                              <div className="friend-name">{friend.fullname}</div>
                            </div>
                            <div className="input-container">
                              <input type="checkbox" className="check-add-group"  checked={!!checkedItems[friend.id]} readOnly/>
                            </div>
                          </div>
                      )}
                </div>
            </div>
            <div className='footer'>
                <button className='button-add-group' onClick={addMemberToGroup}>Done</button>
            </div>
        </div>
    </div>
  );
});

export default AddToGroupComponent;