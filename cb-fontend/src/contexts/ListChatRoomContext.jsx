import React, { createContext, useState, useContext } from 'react';

const ListChatRoomContext = createContext(null);

export const ListChatRoomProvider = ({ children }) => {
  const [listChatRoom, setListChatRoom] = useState([]);

  return (
    <ListChatRoomContext.Provider value={{ listChatRoom, setListChatRoom }}>
      {children}
    </ListChatRoomContext.Provider>
  );
};
export const useListChatRoom = () => {
  return useContext(ListChatRoomContext);
};
