import React, { createContext, useState, useContext } from 'react';

const ChatRoomContext = createContext(null);

export const ChatRoomProvider = ({ children }) => {
  const [chatRoom, setChatRoom] = useState(null);

  return (
    <ChatRoomContext.Provider value={{ chatRoom, setChatRoom }}>
      {children}
    </ChatRoomContext.Provider>
  );
};
export const useChatRoom = () => {
  return useContext(ChatRoomContext);
};
