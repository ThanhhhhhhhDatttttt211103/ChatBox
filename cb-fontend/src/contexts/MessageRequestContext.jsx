import React, { createContext, useState, useContext } from 'react';

const MessageRequestContext = createContext(null);

export const MessageRequestProvider = ({ children }) => {
  const [messageRequest, setMessageRequest] = useState(null);

  return (
    <MessageRequestContext.Provider value={{ messageRequest, setMessageRequest }}>
      {children}
    </MessageRequestContext.Provider>
  );
};
  
export const useMessageRequest = () => {
  return useContext(MessageRequestContext);
};
